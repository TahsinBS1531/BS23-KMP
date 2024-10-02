package com.jetbrains.bs23_kmp.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
actual fun MapTourPage(
    modifier: Modifier ,
    isTracking: Boolean,
    currentLocation: CoordinatesData?,
    trackedLocations: List<CoordinatesData>,
    lastTrackedLocations: List<CoordinatesData>?,
    onStartTracking: () -> Unit,
    onStopTracking: () -> Unit,
    onLocationUpdate: (CoordinatesData) -> Unit,
    isShowTrack: Boolean,
    onToogleShowTrack: (Boolean) -> Unit,
    onStartTime: (String) -> Unit,
    onEndTime: (String) -> Unit,
    startTime: String,
    endTime: String,
    email:String,
    navController: NavController
) {
}

@Composable
actual fun MapLocationPermissionDenied() {
}

@Composable
actual fun MapWithLoactionTracking1() {
}

@Composable
actual fun MapWithLocationTracking1(
    modifier: Modifier,
    isTracking: Boolean,
    currentLocation: CoordinatesData?,
    onLocationUpdate: (CoordinatesData) -> Unit,
    trackedLocations: List<CoordinatesData>?,
    isShowTrack: Boolean
) {
}

@Composable
actual fun TrackMap1(
    modifier: Modifier,
    trackPoints: List<CoordinatesData>
) {
}

@Composable
actual fun GradientPolyline1(
    polylinePoints: List<CoordinatesData>,
    startColor: Color,
    endColor: Color,
    width: Float
) {
}
@Composable
actual fun GetLocationName(item: CoordinatesData): String {

}

actual fun formatMillsToTime(mills: Long): String {
    TODO("Not yet implemented")
}