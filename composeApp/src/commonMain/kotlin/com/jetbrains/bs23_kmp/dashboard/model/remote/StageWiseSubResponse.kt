package com.jetbrains.bs23_kmp.dashboard.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StageWiseSubResponse(
    @SerialName("Application")
    val application: String?,
    @SerialName("ApplicationCount")
    val applicationCount: Int?,
    @SerialName("StatusDescription")
    val statusDescription: String?
)