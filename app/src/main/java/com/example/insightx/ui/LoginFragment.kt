package com.example.insightx.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.insightx.R
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.databinding.FragmentLoginBinding
import com.example.insightx.util.DataStoreRepository
import com.example.insightx.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<AuthViewModel>()
    lateinit var navController: NavController

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var dataStoreRepo: DataStoreRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)
        navController = Navigation.findNavController(view)

        subscribeToUi()

//        binding.registerBtn.setOnClickListener {
//            navController.navigate(R.id.action_loginFragment_to_registerFragment)
//        }
//
//        binding.login.setOnClickListener {
//            viewModel.login("admin", "admin")
//        }
    }

    private fun subscribeToUi() {
        viewModel.authResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show()
                    Log.e("TAG", "subscribeToUi: ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d("TAG", "subscribeToUi:LOADING... ")
                }
                is NetworkResult.Success -> {
                    lifecycleScope.launch {
                        navController.navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}