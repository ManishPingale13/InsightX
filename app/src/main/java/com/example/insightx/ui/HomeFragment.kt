package com.example.insightx.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.insightx.R
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.databinding.FragmentHomeBinding
import com.example.insightx.util.DataStoreRepository
import com.example.insightx.viewmodel.MachineRecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by viewModels<MachineRecordViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    @Inject
    lateinit var dataStoreRepo: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticationStatus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)
        subscribeToData()
    }

    private fun authenticationStatus() {
        lifecycleScope.launch {
            if (dataStoreRepo.getPass() == null || dataStoreRepo.getUser() == null)
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
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