package com.example.insightx.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.insightx.R
import com.example.insightx.data.retrofit.NetworkResult
import com.example.insightx.viewmodel.MachineRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MachineRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.records.observe(this@MainActivity) {
            when (it) {
                is NetworkResult.Error -> {
                    Log.e("TAG1", "onCreate: ERROR OCCURRED ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d("TAG2", "onCreate: LOADING...")
                }
                is NetworkResult.Success -> {
                    Log.d("TAG3", "Success: ${it.data}")
                }
            }
        }

    }
}