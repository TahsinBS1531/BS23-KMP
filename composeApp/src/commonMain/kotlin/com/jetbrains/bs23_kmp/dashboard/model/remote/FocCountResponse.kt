package com.jetbrains.bs23_kmp.dashboard.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FocCountResponse(
    @SerialName("FocApproved")
    val focApproved: Int?,
    @SerialName("SubmittedFOC")
    val submittedFOC: Int?
)