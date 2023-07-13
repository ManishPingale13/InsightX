package com.example.insightx.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.insightx.R
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.databinding.FragmentHomeBinding
import com.example.insightx.util.AuthManager
import com.example.insightx.viewmodel.MachineRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by viewModels<MachineRecordViewModel>()
    lateinit var authManager: AuthManager
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        Log.d("TAG", "onViewCreated: IN HOME!")
        navController = Navigation.findNavController(view)
        authenticationStatus()
        subscribeToData()

    }

    private fun authenticationStatus() {
        authManager = AuthManager(requireContext())

        this.authManager.credsFlow.asLiveData().observe(this) { creds ->
            if (creds["USER"].isNullOrEmpty() || creds["PASS"].isNullOrEmpty()) {
                Log.d("TAG", "authenticationStatus: ${creds["USER"]}")
                Log.d("TAG", "authenticationStatus: ${creds["PASS"]}")
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
            } else {
                Log.d("TAG", "authenticationStatus: NOT NULL")
                Log.d("TAG", "authenticationStatus: ${creds["USER"]}")
                Log.d("TAG", "authenticationStatus: ${creds["PASS"]}")
            }
        }
    }

    private fun subscribeToData() {
        viewModel.records.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    Log.e("TAG", "subscribeToData: ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d("TAG", "subscribeToData: Loading....")
                }
                is NetworkResult.Success -> {
                    Log.d("TAG", "subscribeToData: ${it.data}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}