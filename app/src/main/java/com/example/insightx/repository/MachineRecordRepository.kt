package com.example.insightx.repository

import com.example.insightx.data.retrofit.model.MachineRecord
import retrofit2.Response

interface MachineRecordRepository {

    //Get all records
    suspend fun getMachineRecords(headers:Map<String,String>): List<MachineRecord>

    //Get single record
    suspend fun getMachineRecord(recordId: Int): MachineRecord

    //Delete single record
    suspend fun deleteMachineRecord(recordId: Int): Response<String>

    //Add single record
    suspend fun addMachineRecord(record: MachineRecord): MachineRecord

}