package com.example.insightx.data.retrofit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
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
    val model: String?,
    val timestamp:String?
):Parcelable
