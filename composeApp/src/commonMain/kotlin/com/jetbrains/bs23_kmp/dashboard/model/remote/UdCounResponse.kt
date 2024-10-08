package com.jetbrains.bs23_kmp.dashboard.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UdCounResponse(
    @SerialName("EmergencyUD")
    val emergencyUD: Int?,
    @SerialName("NormalUD")
    val normalUD: Int?,
    @SerialName("PassedDispatchedUD")
    val passedDispatchedUD: Int?,
    @SerialName("SubmittedUD")
    val submittedUD: Int?
)