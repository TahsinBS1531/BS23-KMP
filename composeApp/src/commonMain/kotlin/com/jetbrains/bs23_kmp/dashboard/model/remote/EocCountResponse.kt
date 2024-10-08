package com.jetbrains.bs23_kmp.dashboard.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EocCountResponse(
    @SerialName("EmergencyEO")
    val emergencyEO: Int?,
    @SerialName("NormalEO")
    val normalEO: Int?,
    @SerialName("PassedDispatchedEO")
    val passedDispatchedEO: Int?,
    @SerialName("SubmittedEO")
    val submittedEO: Int?
)