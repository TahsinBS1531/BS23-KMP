package com.jetbrains.bs23_kmp.dashboard.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyReviewdResponse(
    @SerialName("AMCount")
    val aMCount: Int?,
    @SerialName("EOCount")
    val eOCount: Int?,
    @SerialName("FOCCount")
    val fOCCount: Int?,
    @SerialName("UDCount")
    val uDCount: Int?
)