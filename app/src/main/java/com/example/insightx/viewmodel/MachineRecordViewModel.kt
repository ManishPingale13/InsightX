package com.example.insightx.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.data.retrofit.model.Record
import com.example.insightx.data.retrofit.repository.AuthRepoImpl
import com.example.insightx.data.retrofit.repository.MachineRecordRepoImpl
import com.example.insightx.util.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MachineRecordViewModel @Inject constructor(
    private val recordRepo: MachineRecordRepoImpl,
    private val authRepo: AuthRepoImpl,
    private val dataStoreRepo: DataStoreRepository
) : ViewModel() {

    private val _recordLiveData = MutableLiveData<NetworkResult<List<Record>>>()
    val records: LiveData<NetworkResult<List<Record>>> = _recordLiveData

    private val _deleteReqStatus = MutableLiveData<String>()
    val delReqStatus: LiveData<String> = _deleteReqStatus

    private val _createReqStatus = MutableLiveData<Record?>()
    val createReqStatus: LiveData<Record?> = _createReqStatus


    init {
        fetchMachineRecords()
    }


    fun fetchMachineRecords() {
        viewModelScope.launch {
            val user = dataStoreRepo.getUser()
            val pass = dataStoreRepo.getPass()
            if (user != null && pass != null)
                recordRepo.getAllMachineRecords(
                    getHeaderMap(user, pass)
                ).collect {
                    _recordLiveData.postValue(it)
                }
        }
    }

    private fun getHeaderMap(user: String, pass: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["user"] = user
        headerMap["pass"] = pass
        return headerMap
    }

    fun deleteRecord(removedItem: Record) {

        viewModelScope.launch {
            recordRepo.deleteMachineRecordById(removedItem.id!!).collect {
                when (it) {
                    is NetworkResult.Error ->
                        _deleteReqStatus.postValue("ERROR")

                    is NetworkResult.Loading ->
                        _deleteReqStatus.postValue("LOADING")

                    is NetworkResult.Success ->
                        _deleteReqStatus.postValue("SUCCESS")

                }
            }
        }
    }

    fun createMachineRecord(
        machineName: String,
        model: String?,
        quality: Int,
        airTemp: Double,
        processTemp: Double,
        rotationalSpeed: Double,
        torque: Double,
        toolWear: Double
    ) {

        viewModelScope.launch {
            val record =
                Record(
                    id = null,
                    machine_name = machineName,
                    user = dataStoreRepo.getUser(),
                    password = dataStoreRepo.getPass(),
                    air_temp = airTemp,
                    process_temp = processTemp,
                    rotational_speed = rotationalSpeed,
                    torque = torque,
                    tool_wear = toolWear,
                    quality = quality,
                    predictions = null,
                    status = null,
                    model=model
                )

            recordRepo.addMachineRecord(record).collect {
                when (it) {
                    is NetworkResult.Error -> {
                        _createReqStatus.postValue(null)
                    }
                    is NetworkResult.Loading -> {
                        Log.d("TAG", "createMachineRecord: Creating....")
                    }
                    is NetworkResult.Success -> {
                        _createReqStatus.postValue(it.data)
                    }
                }
            }
        }

    }

}