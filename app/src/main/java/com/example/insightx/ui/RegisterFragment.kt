package com.example.insightx.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.insightx.R
import com.example.insightx.databinding.FragmentRegisterBinding
import com.example.insightx.util.DataStoreRepository

class RegisterFragment : Fragment(R.layout.fragment_register) {
    //    private val viewModel by viewModels<MachineRecordViewModel>()
    private lateinit var authManager: DataStoreRepository
    private lateinit var navController: NavController

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        authManager = DataStoreRepository(requireContext())

        _binding = FragmentRegisterBinding.bind(view)

        binding.loginBtn.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}