package com.example.insightx.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.insightx.R
import com.example.insightx.util.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var authManager: AuthManager
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        authManager = AuthManager(requireContext())


        lifecycleScope.launch {
            authManager.storeUser("admin", "admin")
        }
        authenticationStatus()

    }

    private fun authenticationStatus() {

        this.authManager.credsFlow.asLiveData().observe(this) { creds ->
            if (creds["USER"].isNullOrEmpty() || creds["PASS"].isNullOrEmpty()) {
                Log.d("TAG", "authenticationStatusLogin : ${creds["USER"]}")
                Log.d("TAG", "authenticationStatusLogin : ${creds["PASS"]}")
            } else {
                Log.d("TAG", "authenticationStatus : NOT NULL")
                Log.d("TAG", "authenticationStatusLogin : ${creds["USER"]}")
                Log.d("TAG", "authenticationStatusLogin : ${creds["PASS"]}")
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}