package com.example.insightx.data.retrofit.repository

import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.data.retrofit.api.AuthApi
import com.example.insightx.util.DataStoreRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepoImpl(
    private val authApi: AuthApi, private val dataStoreRepo: DataStoreRepository
) {
    suspend fun getUserLogin(user: String, pass: String) = flow {
        emit(NetworkResult.Loading())
        val response = authApi.loginUser(user, pass)
        if (response.isSuccessful) {
            dataStoreRepo.storeUser(user, pass)
            emit(NetworkResult.Success(response.body()))
        } else
            emit(NetworkResult.Error(response.message()))
    }.catch {
        emit(NetworkResult.Error("Error!"))
    }

    suspend fun getUserSignup(name: String, email: String, pass: String) = flow {
        emit(NetworkResult.Loading())
        val response = authApi.signupUser(name, email, pass)
        if (response.isSuccessful) {
            dataStoreRepo.storeUser(email, pass)
            emit(NetworkResult.Success(response.body()))
        } else
            emit(NetworkResult.Error(response.message()))
    }.catch {
        emit(NetworkResult.Error("Error!"))

    }

}