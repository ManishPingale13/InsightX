package com.example.insightx.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.insightx.R
import com.example.insightx.viewmodel.MachineRecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel:MachineRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.records.observe(this@MainActivity){records->
            for( i in records){
                Log.d("RECORDS", "onCreate: DATA:$i")
            }
        }

    }
}