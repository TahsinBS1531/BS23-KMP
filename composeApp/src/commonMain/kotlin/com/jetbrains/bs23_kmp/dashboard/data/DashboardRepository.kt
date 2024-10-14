package com.jetbrains.bs23_kmp.dashboard.data

import com.bs23.msfa.core.extensions.mapSuccess
import com.jetbrains.bs23_kmp.core.base.BASE_URL
import com.jetbrains.bs23_kmp.dashboard.model.remote.AmAccessoriesConsumptionResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.AmCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.DailyReviewdResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.EocCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.FocCountResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.StageWiseSubResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.UdAccessoriesConsumptionResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.UdCounResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class DashboardRepository(private val client: HttpClient) :DashboardApi{

    override suspend fun getAmAccessoriesConsumption(
        fromDate: String,
        toDate: String
    ): List<AmAccessoriesConsumptionResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/am-accessories-consumption")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
        }.mapSuccess<List<AmAccessoriesConsumptionResponse>>()
    }

    override suspend fun getUdAccessoriesConsumption(
        fromDate: String,
        toDate: String
    ): List<UdAccessoriesConsumptionResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/ud-accessories-consumption")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
        }.mapSuccess<List<UdAccessoriesConsumptionResponse>>()
    }

    override suspend fun getAmCount(fromDate: String, toDate: String): List<AmCountResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/am-count")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
        }.mapSuccess<List<AmCountResponse>>()
    }

    override suspend fun getEocCount(fromDate: String, toDate: String): List<EocCountResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/eoc-count")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
        }.mapSuccess<List<EocCountResponse>>()
    }

    override suspend fun getFocCount(fromDate: String, toDate: String): List<FocCountResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/foc-count")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
        }.mapSuccess<List<FocCountResponse>>()
    }

    override suspend fun getStageWiseSubCount(
        fromDate: String,
        toDate: String
    ): List<StageWiseSubResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/stage-wise-submitted-count")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
        }.mapSuccess<List<StageWiseSubResponse>>()
    }

    override suspend fun getUdCount(fromDate: String, toDate: String): List<UdCounResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/ud-count")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
        }.mapSuccess<List<UdCounResponse>>()
    }

    override suspend fun getDailyReviewedCount(
        fromDate: String,
        toDate: String,
        userId:String
    ): List<DailyReviewdResponse> {
        return client.get {
            url("$BASE_URL/api/dashboard/daily-reviewed")
            parameter("fromDate", fromDate)
            parameter("toDate", toDate)
            parameter("userId", userId)
        }.mapSuccess<List<DailyReviewdResponse>>()
    }

}