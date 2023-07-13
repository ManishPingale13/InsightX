package com.example.insightx.data.retrofit.api

import com.example.insightx.data.retrofit.model.Record
import retrofit2.Response
import retrofit2.http.*


interface MachineRecordApi {
    companion object {
        const val BASE_URL = "http://192.168.2.103:80/api/"
    }

    @GET("records")
    suspend fun getRecords(@HeaderMap headers: Map<String, String>): Response<List<Record>>

    @POST("records")
    suspend fun addRecord(@Body record: Record): Response<Record>

    @GET("records/{id}")
    suspend fun getRecord(@Path("id") id: Int): Response<Record>

    @DELETE("records/{id}")
    suspend fun deleteRecord(@Path("id") id: Int): Response<String>
}

//    :
//    NetworkResult<T> {
//        return try {
//            val response = execute()
//            val body = response.body()
//            if (response.isSuccessful && body != null) {
//                NetworkResult.Success(body)
//            } else {
//                NetworkResult.Error(message = response.message())
//            }
//        } catch (e: Exception) {
//            NetworkResult.Error(message = e.message!!)
//        }
//    }
//}


//    private fun getHeaderMap(user: String, pass: String): Map<String, String> {
//        val headerMap = mutableMapOf<String, String>()
//        headerMap["user"] = user
//        headerMap["pass"] = pass
//        return headerMap
//    }
//}