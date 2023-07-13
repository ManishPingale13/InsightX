package com.example.insightx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.data.retrofit.repository.AuthRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepoImpl
) : ViewModel() {

    private val _authResponse = MutableLiveData<NetworkResult<String>>()
    val authResponse: LiveData<NetworkResult<String>> = _authResponse

    fun login(user: String, pass: String) {
        viewModelScope.launch {
            authRepo.getUserLogin(user, pass).collect {
                _authResponse.postValue(it)
            }
        }
    }

    fun signup(name: String, email: String, pass: String) {
        viewModelScope.launch {
            authRepo.getUserSignup(name, email, pass).collect {
                _authResponse.postValue(it)
            }
        }
    }

}