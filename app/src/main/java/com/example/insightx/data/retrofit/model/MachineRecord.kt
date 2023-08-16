package com.example.insightx.data.retrofit.model

data class Record(
    val id: Int? = null,
    val machine_name: String?,
    val user: String?,
    val password: String?,
    val air_temp: Double?,
    val process_temp: Double?,
    val rotational_speed: Double?,
    val torque: Double?,
    val tool_wear: Double?,
    val quality: Int?,
    val predictions: List<List<Double>>? = null,
    val status: String?,
    val model:String?
)

data class MachineRecord(
    val records: List<Record>, val errorMessage: String
)