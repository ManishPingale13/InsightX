package com.example.insightx.data.retrofit.repository

import com.example.insightx.data.retrofit.MachineRecordApi
import com.example.insightx.data.retrofit.model.MachineRecord
import com.example.insightx.repository.MachineRecordRepository

class MachineRecordRepoImpl(
    private val recordApi: MachineRecordApi
):MachineRecordRepository {

    override suspend fun getMachineRecords(headers:Map<String,String>) = recordApi.getRecords(headers)

    override suspend fun getMachineRecord(recordId: Int)= recordApi.getRecord(recordId)

    override suspend fun deleteMachineRecord(recordId: Int) = recordApi.deleteRecord(recordId)

    override suspend fun addMachineRecord(record: MachineRecord) = recordApi.addRecord(record)
}