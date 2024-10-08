package com.jetbrains.bs23_kmp.dashboard.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class AmAccessoriesConsumptionResponse(
    @SerialName("Both")
    val both: Int?,
    @SerialName("TotalAMAccessories")
    val totalAMAccessories: Int?,
    @SerialName("TotalAMConsumptions")
    val totalAMConsumptions: Int?
)