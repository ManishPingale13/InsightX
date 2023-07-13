package com.example.insightx.data.retrofit.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("loginusername") user: String,
        @Field("loginpass") pass: String
    ): Response<String>

    @POST("signup")
    @FormUrlEncoded
    suspend fun signupUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("pass1") pass: String
    ): Response<String>
}
