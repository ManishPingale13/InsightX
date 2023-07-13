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


    suspend fun getAllMachineRecords(creds: Map<String, String>) =
        flow {
            emit(NetworkResult.Loading())
            val response = recordApi.getRecords(creds)
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

