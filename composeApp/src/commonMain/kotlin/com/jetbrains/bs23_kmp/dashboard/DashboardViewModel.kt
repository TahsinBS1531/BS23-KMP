package com.jetbrains.bs23_kmp.dashboard

import com.jetbrains.bs23_kmp.core.base.viewmodel.MviViewModel
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.dashboard.data.DashboardRepository

class DashboardViewModel(private val repository: DashboardRepository):MviViewModel<BaseViewState<DashboardViewState>,DashboardViewEvent>() {

    var data = DashboardViewState()

    init {
        observeViewModel()
        setState(BaseViewState.Data(data))
    }

    private fun observeViewModel() {
        getUdCount("2024-07-01", "2024-09-19")
        getAmCount("2024-07-01", "2024-09-19")
        getEocCount("2024-07-01", "2024-09-19")
        getFocCount("2024-07-01", "2024-09-19")
    }

    override fun onTriggerEvent(eventType: DashboardViewEvent) {
        when (eventType) {
            is DashboardViewEvent.GetAccessoriesConsumption -> {
                getAccessoriesConsumption(eventType.fromDate, eventType.toDate)
            }
            is DashboardViewEvent.GetAmCount -> {
                getAmCount(eventType.fromDate, eventType.toDate)
            }

            is DashboardViewEvent.GetDailyReviewedCount -> TODO()
            is DashboardViewEvent.GetEocCount -> {
                getEocCount(eventType.fromDate, eventType.toDate)
            }
            is DashboardViewEvent.GetFocCount -> {
                getFocCount(eventType.fromDate, eventType.toDate)
            }
            is DashboardViewEvent.GetStageWiseSubCount -> TODO()
            is DashboardViewEvent.GetUdCount -> {
                getUdCount(eventType.fromDate, eventType.toDate)
            }
        }
    }

    private fun getFocCount(fromDate: String, toDate: String) {
        safeLaunch(disableStartLoading = true) {
            data = data.copy(focLoader = true)
            setState(BaseViewState.Data(data))
            val result = repository.getFocCount(fromDate, toDate)
            data = data.copy(focCount = result[0], focLoader = false)
            setState(BaseViewState.Data(data))
        }
    }

    private fun getEocCount(fromDate: String, toDate: String) {
        safeLaunch(disableStartLoading = true) {
            data = data.copy(eocLoader = true)
            setState(BaseViewState.Data(data))
            val result = repository.getEocCount(fromDate, toDate)
            data = data.copy(eocCount = result[0], eocLoader = false)
            setState(BaseViewState.Data(data))
        }
    }

    private fun getAmCount(fromDate: String, toDate: String) {
        safeLaunch (disableStartLoading = true) {
            data = data.copy(amLoader = true)
            setState(BaseViewState.Data(data))
            val result = repository.getAmCount(fromDate, toDate)
            data = data.copy(amCount = result[0], amLoader = false)
            setState(BaseViewState.Data(data))
        }
    }

    private fun getUdCount(fromDate: String, toDate: String) {
        safeLaunch(disableStartLoading = true) {
            data = data.copy(udLoader = true)
            setState(BaseViewState.Data(data))
            val result = repository.getUdCount(fromDate, toDate)
            data = data.copy(udCount = result[0], udLoader = false)
            setState(BaseViewState.Data(data))
        }
    }

    private fun getAccessoriesConsumption(fromDate: String, toDate: String) {
        safeLaunch {
            data = data.copy(udLoader = true)
            val result = repository.getAmAccessoriesConsumption(fromDate,toDate)
            data = data.copy(accessoriesConsumption = result, udLoader = false)
            setState(BaseViewState.Data(data))
        }

    }
}