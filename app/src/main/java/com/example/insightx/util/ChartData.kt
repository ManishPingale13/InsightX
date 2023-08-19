package com.example.insightx.util

import kotlinx.serialization.Serializable

@Serializable
data class ChartData(
    val label:List<String>,
    val data:List<Double>
)