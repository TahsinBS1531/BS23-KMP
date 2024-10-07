package com.jetbrains.bs23_kmp.screens.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetbrains.bs23_kmp.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun MapTourPage(
    modifier: Modifier,
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
    email: String,
    navController: NavController
) {

    val fusedLocationClient = rememberFusedLocationProviderClient()

    val locationPermissionState =
        rememberPermissionState(permission = "android.permission.ACCESS_FINE_LOCATION")

    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentTime = System.currentTimeMillis()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center
        ) {
            if (locationPermissionState.status.isGranted) {
                currentLocation?.let {

                    MapWithLocationTracking1(
                        modifier = Modifier.fillMaxSize(),
                        isTracking = isTracking,
                        currentLocation = currentLocation,
                        onLocationUpdate = onLocationUpdate,
                        trackedLocations = if (isTracking) trackedLocations else lastTrackedLocations,
                        isShowTrack = isShowTrack
                    )

                }
            } else {
                MapLocationPermissionDenied()
            }
        }


        Box(
            Modifier
                .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Start Time:",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    Text(
                        if (startTime.isNotEmpty()) startTime else "Not Started",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "End Time:",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    Text(
                        if (endTime.isNotEmpty()) endTime else "Not Started",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Tracked Locations:",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    Text(
                        trackedLocations.size.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                HorizontalDivider()
                TextButton(onClick = {
                    navController.navigate("history/$email")
                }) {
                    Text("Check History")
                }
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (isTracking) {
                            onToogleShowTrack(true)
                            onStopTracking()
                            onEndTime(timeFormatter.format(Date()))
                        } else {
                            onStartTracking()
                            onStartTime(timeFormatter.format(Date()))
                        }

                    },
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isTracking) Color.Transparent else MaterialTheme.colorScheme.primary),
//                    enabled = !isTracking
                ) {
                    Text(
                        text = if (isTracking) "End Tour" else "Start Tour",
                        color = if (isTracking) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }


        }
    }
}

@Composable
actual fun MapLocationPermissionDenied() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Location permission required to show map.")
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            // Open App Settings
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)  // Launch settings activity
        }) {
            Text("Open Settings")
        }
    }
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

    println("isTracking: $isTracking")
    println("currentLocation: $currentLocation")
    println("trackedLocations: $trackedLocations")
    val fusedLocationClient = rememberFusedLocationProviderClient()
    val defaultZoom = 15f
    var zoomLevel by remember { mutableStateOf(defaultZoom) } // Track zoom level
    val converTedCurrentPosition = currentLocation?.coordinates?.let {
        LatLng(it.latitude, it.longitude)
    }
    val convertedTrackedLocations = trackedLocations?.map { location ->
        location.coordinates.let { LatLng(it.latitude, it.longitude) }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            converTedCurrentPosition ?: LatLng(23.42, 90.20), defaultZoom
        )
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Update zoom level only when user interacts with the map manually (like pinch zoom)
    DisposableEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            zoomLevel = cameraPositionState.position.zoom // Capture current zoom level
        }
        onDispose { }
    }

    LaunchedEffect(converTedCurrentPosition) {
        converTedCurrentPosition?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLng(it), // Only update the position, not the zoom
                durationMs = 500
            )
        }
    }

    LaunchedEffect(isTracking) {
        //Request Interval Time

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setWaitForAccurateLocation(true).build()

        if (ActivityCompat.checkSelfPermission(
                context, "android.permission.ACCESS_FINE_LOCATION"
            ) == 0
        ) {
            scope.launch {
                fusedLocationClient.getLocationUpdates(locationRequest).collect {
                    onLocationUpdate(
                        CoordinatesData(
                            coordinates = LocationData(
                                it.latitude,
                                it.longitude
                            )
                        )
                    )
                }
            }
        }
    }

    // Display the map and update camera position based on current location

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLoaded = {
            converTedCurrentPosition?.let {
                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(
                            it, zoomLevel
                        ), // Maintain the same zoom level
                        durationMs = 500
                    )
                }
            }
        },
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true
        ),
        properties = MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = true,
            isTrafficEnabled = true,
        )
    ) {
        // Show the user's current location as a marker
        val currentMarker = context.vectorToMapMarker(
            R.drawable.ic_current_marker, MaterialTheme.colorScheme.primary
        )
        converTedCurrentPosition?.let {
            Marker(
                state = MarkerState(position = it), title = "Current Location", icon = currentMarker
            )
        }

        if (isTracking && convertedTrackedLocations?.size!! > 1) {

            convertedTrackedLocations.let { points ->
                GradientPolyline(
                    polylinePoints = points,
                    startColor = MaterialTheme.colorScheme.error,
                    endColor = MaterialTheme.colorScheme.primary,
                    width = 5f,
                )
            }

            val startIcon = context.vectorToMapMarker(
                R.drawable.ic_marker_start
            )
            convertedTrackedLocations.firstOrNull()?.let { startPoint ->
                Marker(
                    state = MarkerState(position = startPoint), title = "Start", icon = startIcon
                )
            }

        }else if (!isTracking && convertedTrackedLocations?.size!! > 1 && isShowTrack) {
            convertedTrackedLocations.let { points ->
                GradientPolyline(
                    polylinePoints = points,
                    startColor = MaterialTheme.colorScheme.error,
                    endColor = MaterialTheme.colorScheme.primary,
                    width = 5f,
                )
            }

            val startIcon = context.vectorToMapMarker(
                R.drawable.ic_marker_start
            )

            convertedTrackedLocations?.firstOrNull()?.let { startPoint ->
                Marker(
                    state = MarkerState(position = startPoint), title = "Start", icon = startIcon
                )
            }

            val endIcon = context.vectorToMapMarker(
                R.drawable.ic_marker, MaterialTheme.colorScheme.primary
            )
            convertedTrackedLocations?.lastOrNull()?.let { endPoint ->
                Marker(
                    state = MarkerState(position = endPoint), title = "End", icon = endIcon
                )
            }

        }
    }
}


@Composable
actual fun TrackMap1(
    modifier: Modifier, trackPoints: List<CoordinatesData>
) {
    val context = LocalContext.current

    val converTedtrackPoints = trackPoints.mapNotNull { location ->
        location.coordinates?.let { LatLng(it.latitude, it.longitude) }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            calculateMidpoint(
                converTedtrackPoints.firstOrNull() ?: LatLng(0.0, 0.0),
                converTedtrackPoints.lastOrNull() ?: LatLng(0.0, 0.0)
            ), 10f
        )
    }

    Box(modifier = modifier.padding(10.dp)) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = true
            )
        ) {
            GradientPolyline(
                polylinePoints = converTedtrackPoints,
                startColor = MaterialTheme.colorScheme.error,
                endColor = MaterialTheme.colorScheme.primary,
                width = 5f,
            )

            converTedtrackPoints.firstOrNull()?.let { firstPoint ->
                val startIcon = context.vectorToMapMarker(
                    R.drawable.ic_marker_start
                )
                Marker(
                    state = MarkerState(position = firstPoint),
                    title = "Start",
                    icon = startIcon
                )
            }

            converTedtrackPoints.lastOrNull()?.let { lastPoint ->
                val endIcon = context.vectorToMapMarker(
                    R.drawable.ic_marker, MaterialTheme.colorScheme.primary
                )
                Marker(
                    state = MarkerState(position = lastPoint),
                    title = "End",
                    icon = endIcon
                )
            }
        }
    }
}


@Suppress("MissingPermission")
fun FusedLocationProviderClient.getLocationUpdates(locationRequest: LocationRequest): Flow<Location> =
    callbackFlow {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    trySend(location)
                }
            }
        }
        requestLocationUpdates(locationRequest, locationCallback, null)
        awaitClose {
            removeLocationUpdates(
                locationCallback
            )
        }
    }

@Composable
fun rememberFusedLocationProviderClient(): FusedLocationProviderClient {
    val context = LocalContext.current
    return remember {
        LocationServices.getFusedLocationProviderClient(
            context
        )
    }
}

fun Context.vectorToMapMarker(
    @DrawableRes vectorResId: Int,
    tintColor: Color? = null,
): BitmapDescriptor? {
    val drawable: Drawable = ContextCompat.getDrawable(this, vectorResId) ?: return null

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )

    val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
    tintColor?.let {
        DrawableCompat.setTint(wrappedDrawable, it.toArgb())
    }

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Composable
fun GradientPolyline(
    polylinePoints: List<LatLng>,
    startColor: Color,
    endColor: Color,
    width: Float = 5f,
) {
    val segmentCount = polylinePoints.size - 1

    // Iterate over the points and draw poly lines with interpolated colors
    for (i in 0 until segmentCount) {
        val fraction = i / segmentCount.toFloat() // Calculate the color interpolation fraction
        val color = interpolateColor(startColor, endColor, fraction) // Get interpolated color

        // Draw each segment with the interpolated color
        Polyline(
            points = listOf(polylinePoints[i], polylinePoints[i + 1]),
            color = color,
            width = width,
            jointType = JointType.ROUND,
            pattern = listOf(Dash(15f), Gap(15f))
        )
    }
}

fun calculateMidpoint(point1: LatLng, point2: LatLng): LatLng {
    val midLat = (point1.latitude + point2.latitude) / 2
    val midLng = (point1.longitude + point2.longitude) / 2
    return LatLng(midLat, midLng)
}

@Composable
actual fun GetLocationName(item: CoordinatesData): String {

    val convertedData =
        LatLng(item.coordinates?.latitude ?: 0.0, item.coordinates?.longitude ?: 0.0)
    val geocoder = Geocoder(LocalContext.current, Locale.getDefault())
    return try {

        val addresses = geocoder.getFromLocation(convertedData.latitude, convertedData.longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            addresses[0].getAddressLine(0) ?: "Unknown Location"
        } else {
            "Unknown Location"
        }
    } catch (e: IOException) {
        "Location Unavailable"
    }
}

actual fun formatMillsToTime(mills: Long): String {
    val instant = Instant.ofEpochMilli(mills)
//    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
//    return timeFormatter.format(instant)
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}