package com.example.insightx.data.retrofit.model

data class Record(
    val id: Int? = null,
    val machine_name: String?,
    val user: String?,
    val password: String?,
    val air_temp: Int?,
    val process_temp: Int?,
    val rotational_speed: Int?,
    val torque: Int?,
    val tool_wear: Int?,
    val quality: Int?,
    val predictions: List<List<Double>>? = null,
    val status: String?
)

data class MachineRecord(
    val records: List<Record>, val errorMessage: String
)