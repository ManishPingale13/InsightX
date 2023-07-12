package com.example.insightx.data.retrofit.repository

import com.example.insightx.data.retrofit.api.AuthApi

class AuthRepoImpl(
    private val authApi: AuthApi
) {
    suspend fun getUserLogin(user: String, pass: String) = authApi.loginUser(user, pass)

    suspend fun getUserSignup(name: String, email: String, pass: String) =
        authApi.signupUser(name, email, pass)

}