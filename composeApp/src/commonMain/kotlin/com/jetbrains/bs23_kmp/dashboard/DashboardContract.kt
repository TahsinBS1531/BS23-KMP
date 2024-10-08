package com.jetbrains.bs23_kmp.dashboard

import com.jetbrains.bs23_kmp.dashboard.model.remote.AmAccessoriesConsumptionResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.AmCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.DailyReviewdResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.EocCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.FocCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.StageWiseSubResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.UdCounResponse

sealed class DashboardViewEvent{
    data class GetAccessoriesConsumption(val fromDate: String, val toDate: String) : DashboardViewEvent()
    data class GetAmCount(val fromDate: String, val toDate: String) : DashboardViewEvent()
    data class GetEocCount(val fromDate: String, val toDate: String) : DashboardViewEvent()
    data class GetFocCount(val fromDate: String, val toDate: String) : DashboardViewEvent()
    data class GetStageWiseSubCount(val fromDate: String, val toDate: String) : DashboardViewEvent()
    data class GetUdCount(val fromDate: String, val toDate: String) : DashboardViewEvent()
    data class GetDailyReviewedCount(val fromDate: String, val toDate: String,val userId:String) : DashboardViewEvent()
}

data class DashboardViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val accessoriesConsumption: List<AmAccessoriesConsumptionResponse> = emptyList(),
    val amCount: List<AmCountResponse> = emptyList(),
    val eocCount: List<EocCountResponse> = emptyList(),
    val focCount: List<FocCountResponse> = emptyList(),
    val stageWiseSubCount: List<StageWiseSubResponse> = emptyList(),
    val udCount: UdCounResponse? = null,
    val dailyReviewedCount: List<DailyReviewdResponse> = emptyList(),
)