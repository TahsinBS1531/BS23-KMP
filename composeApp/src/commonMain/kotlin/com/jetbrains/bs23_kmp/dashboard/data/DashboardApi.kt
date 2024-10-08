package com.jetbrains.bs23_kmp.dashboard.data

import com.jetbrains.bs23_kmp.dashboard.model.remote.AmAccessoriesConsumptionResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.AmCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.DailyReviewdResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.EocCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.FocCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.StageWiseSubResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.UdCounResponse

interface DashboardApi {
    suspend fun getAmAccessoriesConsumption(fromDate:String, toDate:String ):List<AmAccessoriesConsumptionResponse>

    suspend fun getAmCount(fromDate: String,toDate: String): List<AmCountResponse>

    suspend fun getEocCount(fromDate: String,toDate: String): List<EocCountResponse>

    suspend fun getFocCount(fromDate: String, toDate: String): List<FocCountResponse>

    suspend fun getStageWiseSubCount(fromDate: String, toDate: String): List<StageWiseSubResponse>

    suspend fun getUdCount(fromDate: String, toDate: String): List<UdCounResponse>

    suspend fun getDailyReviewedCount(fromDate: String, toDate: String,userId:String): List<DailyReviewdResponse>
}