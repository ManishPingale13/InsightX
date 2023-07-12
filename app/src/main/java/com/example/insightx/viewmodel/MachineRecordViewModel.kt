package com.example.insightx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.data.retrofit.model.Record
import com.example.insightx.data.retrofit.repository.AuthRepoImpl
import com.example.insightx.data.retrofit.repository.MachineRecordRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MachineRecordViewModel @Inject constructor(
    private val recordRepo: MachineRecordRepoImpl,
    private val authRepo: AuthRepoImpl
) : ViewModel() {
    private val _recordLiveData = MutableLiveData<NetworkResult<List<Record>>>()
    val records: LiveData<NetworkResult<List<Record>>> = _recordLiveData

    init {
        viewModelScope.launch {
            recordRepo.getMachineRecords().collect(){
                _recordLiveData.postValue(it)
            }
//            _recordLiveData.postValue(recordRepo.getMachineRecords())
        }
    }


    private fun getHeaderMap(user: String, pass: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["user"] = user
        headerMap["pass"] = pass
        return headerMap
    }
}