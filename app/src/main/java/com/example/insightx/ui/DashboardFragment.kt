package com.example.insightx.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.insightx.R
import com.example.insightx.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupChart()
    }

    private fun setupChart() {
//        val pie = AnyChart.pie()
    }

}