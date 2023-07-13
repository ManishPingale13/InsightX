package com.example.insightx.data.retrofit.repository

import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.data.retrofit.api.MachineRecordApi
import com.example.insightx.data.retrofit.model.Record
import kotlinx.coroutines.flow.Flow
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

    suspend fun getAllMachineRecords() =
        flow {
            emit(NetworkResult.Loading())
            val response = recordApi.getRecords(getHeaderMap("admin", "admin"))
            if (response.isSuccessful)
                emit(NetworkResult.Success(response.body()))
            else
                emit(NetworkResult.Error(response.message()))
        }.catch { e ->
            emit(NetworkResult.Error(e.message ?: "Something went wrong"))
        }

    suspend fun getMachineRecordById(recordId: Int) =
        flow {
            emit(NetworkResult.Loading())
            val response = recordApi.getRecord(recordId)
            if (response.isSuccessful)
                emit(NetworkResult.Success(response.body()))
            else
                emit(NetworkResult.Error(response.message()))
        }.catch { e ->
            emit(NetworkResult.Error(e.message ?: "Something went wrong"))
        }

    suspend fun deleteMachineRecordById(recordId: Int): Flow<NetworkResult<String>> = flow {
        emit(NetworkResult.Loading())
        val response = recordApi.deleteRecord(recordId)
        if (response.isSuccessful)
            emit(NetworkResult.Success(response.body()))
        else
            emit(NetworkResult.Error(response.message()))
    }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "Something went wrong"))
    }

    suspend fun addMachineRecord(record: Record): Flow<NetworkResult<Record>> = flow {
        emit(NetworkResult.Loading())
        val response = recordApi.addRecord(record)
        if (response.isSuccessful)
            emit(NetworkResult.Success(response.body()!!))
        else
            emit(NetworkResult.Error(response.message()))
    }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "Something went wrong"))
    }
}

