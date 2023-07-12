package com.example.insightx.data.retrofit.repository

import android.util.Log
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.data.retrofit.api.MachineRecordApi
import com.example.insightx.data.retrofit.model.MachineRecord
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MachineRecordRepoImpl(
    private val recordApi: MachineRecordApi
) {

    private fun getHeaderMap(user: String, pass: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["user"] = user
        headerMap["pass"] = pass
        return headerMap
    }

    suspend fun getMachineRecords() =
//        (MachineRecordDataSource(recordApi).invoke())
        flow {
            emit(NetworkResult.Loading())
            val response = recordApi.getRecords(getHeaderMap("admin", "admin"))
            Log.d("TAG-1", "getMachineRecords: Data:  ${response.body()}")
            if (response.isSuccessful)
                emit(NetworkResult.Success(response.body()))
            else
                emit(NetworkResult.Error(response.message()))
        }.catch { e ->
            Log.d("TAG-2", "getMachineRecords: IN CATCH ${e.message}")
            emit(NetworkResult.Error(e.message ?: "Some error occurred"))
        }

    suspend fun getMachineRecord(recordId: Int) = recordApi.getRecord(recordId)

    suspend fun deleteMachineRecord(recordId: Int) = recordApi.deleteRecord(recordId)

    suspend fun addMachineRecord(record: MachineRecord) = recordApi.addRecord(record)
}