package com.jetbrains.bs23_kmp.dashboard

import com.jetbrains.bs23_kmp.core.base.viewmodel.MviViewModel
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.dashboard.data.DashboardRepository

class DashboardViewModel(private val repository: DashboardRepository):MviViewModel<BaseViewState<DashboardViewState>,DashboardViewEvent>() {

    var data = DashboardViewState()

    init {
        setState(BaseViewState.Data(data))
    }

    override fun onTriggerEvent(eventType: DashboardViewEvent) {
        when (eventType) {
            is DashboardViewEvent.GetAccessoriesConsumption -> {
                getAccessoriesConsumption(eventType.fromDate, eventType.toDate)
            }

            is DashboardViewEvent.GetAmCount -> TODO()
            is DashboardViewEvent.GetDailyReviewedCount -> TODO()
            is DashboardViewEvent.GetEocCount -> TODO()
            is DashboardViewEvent.GetFocCount -> TODO()
            is DashboardViewEvent.GetStageWiseSubCount -> TODO()
            is DashboardViewEvent.GetUdCount -> {
                getUdCount(eventType.fromDate, eventType.toDate)
            }
        }
    }

    private fun getUdCount(fromDate: String, toDate: String) {
        safeLaunch {
            data = data.copy(isLoading = true)
            val result = repository.getUdCount(fromDate, toDate)
            data = data.copy(udCount = result[0], isLoading = false)
            setState(BaseViewState.Data(data))
        }
    }

    private fun getAccessoriesConsumption(fromDate: String, toDate: String) {
        safeLaunch {
            data = data.copy(isLoading = true)
            val result = repository.getAmAccessoriesConsumption(fromDate,toDate)
            data = data.copy(accessoriesConsumption = result, isLoading = false)
            setState(BaseViewState.Data(data))
        }

    }
}