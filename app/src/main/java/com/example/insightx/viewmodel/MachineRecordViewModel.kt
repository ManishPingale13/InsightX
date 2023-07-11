package com.example.insightx.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insightx.data.retrofit.model.MachineRecord
import com.example.insightx.repository.MachineRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MachineRecordViewModel @Inject constructor(
    private val recordRepo: MachineRecordRepository
) : ViewModel() {
    private val recordLiveData = MutableLiveData<List<MachineRecord>>()
    val records: LiveData<List<MachineRecord>> = recordLiveData

    init {
        viewModelScope.launch {
            recordLiveData.value = recordRepo.getMachineRecords(getHeaderMap("admin", "admin"))

            val record = MachineRecord(
                machine_name = "Pump",
                user = "admin",
                password = "admin",
                air_temp = 30,
                process_temp = 35,
                rotational_speed = 1500,
                torque = 50,
                tool_wear = 200,
                quality = 0
            )
            val log = recordRepo.addMachineRecord(record)
            Log.d("TAG", "DATA: $log")
        }
    }

    private fun getHeaderMap(user: String, pass: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["user"] = user
        headerMap["pass"] = pass
        return headerMap
    }
}