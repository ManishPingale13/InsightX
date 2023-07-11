package com.example.insightx.data.retrofit

import com.example.insightx.data.retrofit.model.MachineRecord
import retrofit2.Response
import retrofit2.http.*


interface MachineRecordApi {
    companion object {
        const val BASE_URL = "http://192.168.2.103:80/api/"
    }

    @GET("records")
    suspend fun getRecords(@HeaderMap headers: Map<String, String>): List<MachineRecord>

    @POST("records")
    suspend fun addRecord(@Body record: MachineRecord): MachineRecord

    @GET("record/{id}")
    suspend fun getRecord(@Path("id") id: Int): MachineRecord

    @DELETE("record/{id}")
    suspend fun deleteRecord(@Path("id") id: Int): Response<String>

}