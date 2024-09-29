package com.jetbrains.bs23_kmp.screens.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun MapTourPage(
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
    endTime: String
) {

    val fusedLocationClient = rememberFusedLocationProviderClient()
    // Permission state
    val locationPermissionState =
        rememberPermissionState(permission = "android.permission.ACCESS_FINE_LOCATION")

    // Request permissions if necessary
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (locationPermissionState.status.isGranted) {
                // Initially show the user's location, then update when tracking is active
                currentLocation?.let {
                    MapWithLocationTracking1(
                        isTracking = isTracking,
                        currentLocation = currentLocation,
                        onLocationUpdate = onLocationUpdate,
                        trackedLocations = lastTrackedLocations,
                        isShowTrack = isShowTrack
                    )
                }
            } else {
                MapLocationPermissionDenied()
            }
        }


//         Start and Stop buttons for location tracking
        Box(
            Modifier
                .weight(.5f)
                .fillMaxWidth(),
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(top = 24.dp)
                    .align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Tracked Locations: ${trackedLocations.size} Times")
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        onStartTracking()
                        onStartTime(timeFormatter.format(Date()))
                    }, // Start tracking
                    enabled = !isTracking  // Disable the start button when tracking is active
                ) {
                    Text("Start Tour")
                }
                Text("Start Time: ${if (startTime.isNotEmpty()) startTime else "Not Started"}")
            }

            //VerticalDivider(modifier = Modifier.align(Alignment.Center))

            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top = 24.dp, bottom = 48.dp),
                onClick = { onToogleShowTrack(!isShowTrack) },
            ) {
                Text(if (isShowTrack) "Hide Last Tour Track" else "Show Last Tour Track")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(top = 24.dp)
                    .align(Alignment.TopEnd),

                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Tracking: ${if (isTracking) "Active" else "Inactive"}")
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        onStopTracking()
                        onEndTime(timeFormatter.format(Date()))
                    }, // Stop tracking and show the path
                    enabled = isTracking  // Disable the stop button when tracking is inactive
                ) {
                    Text("End Tour")
                }
                Text("End Time: ${if (endTime.isNotEmpty()) endTime else "Not Started Yet"}")
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
        Button(
            onClick = {
                // Open App Settings
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)  // Launch settings activity
            }
        ) {
            Text("Open Settings")
        }
    }
}


@Composable
actual fun MapWithLocationTracking1(
    isTracking: Boolean,
    currentLocation: CoordinatesData?,
    onLocationUpdate: (CoordinatesData) -> Unit,
    trackedLocations: List<CoordinatesData>?,
    isShowTrack: Boolean
) {
    val fusedLocationClient = rememberFusedLocationProviderClient()
    val defaultZoom = 10f
    var zoomLevel by remember { mutableStateOf(defaultZoom) } // Track zoom level
    val converTedCurrentPosition = currentLocation?.let {
        LatLng(it.latitude, it.longitude)
    }
    val convertedTrackedLocations = trackedLocations?.map { location ->
        LatLng(location.latitude, location.longitude)
    }

    val cameraPositionState = rememberCameraPositionState {
        position =
            CameraPosition.fromLatLngZoom(converTedCurrentPosition ?: LatLng(23.42, 90.20), defaultZoom)
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
            .setWaitForAccurateLocation(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                "android.permission.ACCESS_FINE_LOCATION"
            ) == 0
        ) {
            scope.launch {
                fusedLocationClient.getLocationUpdates(locationRequest).collect {
                    onLocationUpdate(CoordinatesData(it.latitude, it.longitude))
                }
            }
        }
    }

    // Display the map and update camera position based on current location

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLoaded = {
            converTedCurrentPosition?.let {
                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(
                            it,
                            zoomLevel
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
        )
    ) {
        // Show the user's current location as a marker
        val currentMarker = context.vectorToMapMarker(
            R.drawable.ic_current_marker, MaterialTheme.colorScheme.primary
        )
        converTedCurrentPosition?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Current Location",
                icon = currentMarker
            )
        }

        // If tracking is stopped, show the path
        if (isShowTrack && trackedLocations?.size!! > 1) {
            convertedTrackedLocations?.let { points ->
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
                    state = MarkerState(position = startPoint),
                    title = "Start",
                    icon = startIcon
                )
            }

            val endIcon = context.vectorToMapMarker(
                R.drawable.ic_marker, MaterialTheme.colorScheme.primary
            )
            convertedTrackedLocations?.lastOrNull()?.let { endPoint ->
                Marker(
                    state = MarkerState(position = endPoint),
                    title = "End",
                    icon = endIcon
                )
            }
        }
    }
}

@Composable
actual fun TrackMap1(
    modifier: Modifier,
    trackPoints: List<CoordinatesData>
) {
    val context = LocalContext.current
    val converTedtrackPoints = trackPoints.map {
        LatLng(it.latitude, it.longitude)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            calculateMidpoint(
                converTedtrackPoints.first(),
                converTedtrackPoints.last()
            ), 10f
        )
    }

    Box(modifier = modifier.padding(10.dp)) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
//                zoomControlsEnabled = false,
//                compassEnabled = false,
                myLocationButtonEnabled = true
            )
        ) {
            GradientPolyline(
                polylinePoints = converTedtrackPoints,
                startColor = MaterialTheme.colorScheme.error,
                endColor = MaterialTheme.colorScheme.primary,
                width = 5f,
            )

            val startIcon = context.vectorToMapMarker(
                R.drawable.ic_marker_start
            )
            Marker(
                state = MarkerState(position = converTedtrackPoints.first()),
                title = "Start",
                icon = startIcon
            )

            val endIcon = context.vectorToMapMarker(
                R.drawable.ic_marker, MaterialTheme.colorScheme.primary
            )
            Marker(
                state = MarkerState(position = converTedtrackPoints.last()),
                title = "End",
                icon = endIcon
            )
        }
    }
}

@Composable
actual fun GradientPolyline1(
    polylinePoints: List<CoordinatesData>,
    startColor: Color,
    endColor: Color,
    width: Float
) {

    val segmentCount = polylinePoints.size - 1

    // Iterate over the points and draw poly lines with interpolated colors
    val convertedPoints = polylinePoints.map { ConvertToLatLng(it) }

    for (i in 0 until segmentCount) {
        val fraction = i / segmentCount.toFloat() // Calculate the color interpolation fraction
        val color = interpolateColor(startColor, endColor, fraction) // Get interpolated color
        // Draw each segment with the interpolated color
        Polyline(
            points = listOf(convertedPoints[i], convertedPoints[i + 1]),
            color = color,
            width = width,
            jointType = JointType.ROUND,
            pattern = listOf(Dash(15f), Gap(15f))
        )
    }
}

fun ConvertToLatLng(item:CoordinatesData):LatLng {
    val lat = item.latitude
    val lng = item.longitude
    return LatLng(lat, lng)
}

fun convertToLatLngList(items:List<CoordinatesData>):List<LatLng> {
    val latLngList = mutableListOf<LatLng>()
    for (item in items) {
        val latLng = LatLng(item.latitude, item.longitude)
        latLngList.add(latLng)
    }
    return latLngList
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
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
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