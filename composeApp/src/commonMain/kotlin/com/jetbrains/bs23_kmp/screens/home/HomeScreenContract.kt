package com.jetbrains.bs23_kmp.screens.home

import kotlinx.datetime.Clock


sealed class HomeScreenEvent {
    data class updateLocation(val location : CoordinatesData) : HomeScreenEvent()
    data class toggleTracking(val isTracking: Boolean) : HomeScreenEvent()
    data class updateStartTime(val time:String) : HomeScreenEvent()
    data class updateEndTime(val time:String) : HomeScreenEvent()
    data class saveMapData(val email:String) : HomeScreenEvent()
    data class showLocationHistory(val email: String) : HomeScreenEvent()
    data class deleteDocument(val email: String, val id: String) : HomeScreenEvent()
    data class updateSelectionTab(val index: Int) : HomeScreenEvent()
    data class toggleShowTrack(val isShow: Boolean) : HomeScreenEvent()
    data class toogleBottomSheet(val isShow: Boolean) : HomeScreenEvent()
    object showBottomSheet : HomeScreenEvent()
    object SignOut : HomeScreenEvent()
    object resetState : HomeScreenEvent()
    object isShowTrack: HomeScreenEvent()
}


data class HomeSceenState(
    val isTracking: Boolean = false, // Tracking state
    val isShowTrack: Boolean = false, // Tracking state
    val currentLocation: CoordinatesData? = CoordinatesData(LocationData(23.42, 90.20),),
    val trackedLocations: List<CoordinatesData> = emptyList(), // List to store tracked points
    val selectedTabIndex: Int = 0, // Index of the selected tab
    val trackHistory: List<MapHistoryItem> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val showTrack: Boolean = false,
    val isShowBottomSheet: Boolean = false,
    val historyLoader : Boolean = false,
    val isSignedOut:Boolean = false,
)

data class MapHistoryItem(
    var id: String = "",
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val locations: List<CoordinatesData>,
){
    constructor() : this("", "", "", "", "", emptyList())
}

data class CoordinatesData(
    val coordinates : LocationData = LocationData(),
    val time: Long = Clock.System.now().toEpochMilliseconds(),
)

data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)