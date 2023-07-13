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
import com.example.insightx.databinding.FragmentLoginBinding
import com.example.insightx.util.DataStoreRepository
import com.example.insightx.viewmodel.MachineRecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

//    private val viewModel by viewModels<MachineRecordViewModel>()
    private lateinit var authManager: DataStoreRepository
    lateinit var navController: NavController

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)
        navController = Navigation.findNavController(view)
        authManager = DataStoreRepository(requireContext())

        binding.registerBtn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.login.setOnClickListener {
            lifecycleScope.launch {
                authManager.storeUser("admin", "admin")
                Log.d("TAG", "onViewCreated: SET CREDS! logged in")
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}