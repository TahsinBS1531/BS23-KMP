package com.jetbrains.bs23_kmp.dashboard.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UdAccessoriesConsumptionResponse(
    @SerialName("Both")
    val both: Int?,
    @SerialName("TotalUDAccessories")
    val totalUDAccessories: Int?,
    @SerialName("TotalUDConsumptions")
    val totalUDConsumptions: Int?
)
