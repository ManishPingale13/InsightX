package com.example.insightx.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.insightx.R
import com.example.insightx.databinding.FragmentDashboardBinding
import com.example.insightx.util.ChartData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    //UI
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    //Data
    private val labels = listOf(
        "No Failure",
        "Power Failure",
        "Tool Failure",
        "Overstrain Failure",
        "Heat Failure"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        initCharts()

    }

    @OptIn(ExperimentalSerializationApi::class)
    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    private fun initCharts() {

        val currRecord = arguments?.let {
            DashboardFragmentArgs.fromBundle(it).currectRecord
        }
        val predictions = currRecord!!.predictions!![0].map {
            it * 100
        }
        val jsonData = Json.encodeToString(ChartData(labels, predictions))

        binding.chartWebView.apply {
            settings.javaScriptEnabled = true
            loadUrl("file:///android_asset/chart.html")
        }

        binding.machineName.text = currRecord.machine_name!!.trim()
        binding.machineStatus.text = currRecord.status
        binding.machineTimestamp.text = currRecord.timestamp!!.substringBefore("T")


        binding.chartWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                val jsonEscaped = jsonData.replace("\"", "\\\"")
                val jsCall = "displayChart(\"$jsonEscaped\");"
                binding.chartWebView.evaluateJavascript(jsCall, null)
            }
        }
    }


}