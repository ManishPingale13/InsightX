package com.example.insightx.viewmodel

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

    private val _reqStatus = MutableLiveData<String>()
    val reqStatus: LiveData<String> = _reqStatus


    init {
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
                        _reqStatus.postValue("ERROR")

                    is NetworkResult.Loading ->
                        _reqStatus.postValue("LOADING")

                    is NetworkResult.Success ->
                        _reqStatus.postValue("SUCCESS")

                }
            }
        }
    }
}