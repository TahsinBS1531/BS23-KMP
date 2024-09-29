package com.jetbrains.bs23_kmp.screens.home

import androidx.lifecycle.viewModelScope
import com.jetbrains.bs23_kmp.core.base.viewmodel.MviViewModel
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.screens.auth.AuthService
import kotlinx.coroutines.launch

class HomeViewModel (private val authService: AuthService): MviViewModel<BaseViewState<HomeSceenState>, HomeScreenEvent>() {

    var _uiState = HomeSceenState()

    init {
        setState(BaseViewState.Data(_uiState))
    }

    override fun onTriggerEvent(eventType: HomeScreenEvent) {
        when (eventType) {
            is HomeScreenEvent.deleteDocument -> deleteDocument(
                eventType.email,
                eventType.id
            )

            is HomeScreenEvent.saveMapData -> saveMapData(eventType.email)
            HomeScreenEvent.showBottomSheet -> toggleBottomSheet(true)
            is HomeScreenEvent.showLocationHistory -> showLocationHistory(eventType.email)
            is HomeScreenEvent.toggleShowTrack -> toggleShowTrack(eventType.isShow)
            is HomeScreenEvent.toggleTracking -> toggleTracking(eventType.isTracking)
            is HomeScreenEvent.toogleBottomSheet -> toggleBottomSheet(eventType.isShow)
            is HomeScreenEvent.updateEndTime -> updateEndTime(eventType.time)
            is HomeScreenEvent.updateLocation -> updateLocation(eventType.location)
            is HomeScreenEvent.updateSelectionTab -> updateSelectedTab(eventType.index)
            is HomeScreenEvent.updateStartTime -> updateStartTime(eventType.time)
            HomeScreenEvent.SignOut -> MapLogOut()
        }
    }

    private fun updateLocation(newLocation: CoordinatesData) {
        _uiState = _uiState.copy(
            currentLocation = newLocation,
            trackedLocations = if (_uiState.isTracking) _uiState.trackedLocations + newLocation else _uiState.trackedLocations
        )
        setState(BaseViewState.Data(_uiState))
    }

    private fun toggleTracking(isTracking: Boolean) {
        _uiState = _uiState.copy(isTracking = isTracking, endTime = "")
        setState(BaseViewState.Data(_uiState))
    }

    private fun updateStartTime(time: String) {
        _uiState = _uiState.copy(startTime = time)
        setState(BaseViewState.Data(_uiState))
    }

    private fun updateEndTime(time: String) {
        _uiState = _uiState.copy(endTime = time)
        setState(BaseViewState.Data(_uiState))
    }

    private fun showLocationHistory(email: String) {
        FetchTrackedLocations(email) { result ->
            _uiState = _uiState.copy(
                trackHistory = result.map { item ->
                    MapHistoryItem(
                        id = item.first,
                        title = item.second.title,
                        description = item.second.description,
                        locations = item.second.locations.map {
                            CoordinatesData(
                                it.latitude,
                                it.longitude
                            )
                        },
                        startTime = item.second.startTime,
                        endTime = item.second.endTime
                    )
                }
            )
            setState(BaseViewState.Data(_uiState))
        }
    }

    private fun deleteDocument(email: String, documentId: String) {
        DeleteDocumentFromFirebase(email, documentId)
    }

    private fun updateSelectedTab(index: Int) {
        _uiState = _uiState.copy(selectedTabIndex = index)
        setState(BaseViewState.Data(_uiState))
    }

    private fun toggleShowTrack(isShow: Boolean) {
        _uiState = _uiState.copy(showTrack = isShow)
        setState(BaseViewState.Data(_uiState))
    }

    private fun toggleBottomSheet(isShow: Boolean) {
        _uiState = _uiState.copy(isShowBottomSheet = isShow)
        setState(BaseViewState.Data(_uiState))
    }

    fun saveMapData(userEmail: String) {
        val locations = _uiState.trackedLocations
        val totalLocations = _uiState.trackedLocations.size
        val startTime = _uiState.startTime
        val endTime = _uiState.endTime
        _uiState = _uiState.copy(
            startTime = startTime,
            endTime = endTime,
            isTracking = false,
            trackedLocations = emptyList(),
        )
        setState(BaseViewState.Data(_uiState))
        SaveTrackedLocations(
            locations,
            "Tracked Locations",
            "Total Locations : $totalLocations",
            userEmail,
            startTime,
            endTime
        )

    }

    fun MapLogOut(){
        viewModelScope.launch {
            authService.signOut()
        }
    }

}

data class HistoryItemFirestore1(
    val title: String = "",
    val description: String = "",
    val locations: List<CoordinatesData> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
)

//data class HistoryItem1(
//    var id: String = "",
//    val title: String,
//    val description: String,
//    val startTime: String,
//    val endTime: String,
//    val locations: List<CoordinatesData>,
//)

expect fun DeleteDocumentFromFirebase(email: String, documentId: String)




expect fun SaveTrackedLocations(
    trackedLocations: List<CoordinatesData>,
    historyTitle: String,
    description: String,
    userEmail: String,
    startTime: String,
    endTime: String
)

expect fun FetchTrackedLocations(
    email: String,
    onResult: (List<Pair<String, HistoryItemFirestore1>>) -> Unit
)
