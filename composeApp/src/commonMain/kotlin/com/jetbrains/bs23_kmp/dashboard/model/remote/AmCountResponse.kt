package com.jetbrains.bs23_kmp.dashboard.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class AmCountResponse(
    @SerialName("EmergencyAM")
    val emergencyAM: Int?,
    @SerialName("NormalAM")
    val normalAM: Int?,
    @SerialName("PassedDispatchedAM")
    val passedDispatchedAM: Int?,
    @SerialName("SubmittedAM")
    val submittedAM: Int?
)