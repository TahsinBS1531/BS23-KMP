package com.jetbrains.bs23_kmp.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetbrains.kmpapp.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onNavigation: (String?) -> Unit, email: String) {

    val viewModel: MapViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scheme = MaterialTheme.colorScheme
    val tabs = listOf("Tour", "History")


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PrimaryTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .height(48.dp)
                    .clip(
                        RoundedCornerShape(25)
                    )
                    .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(25)),
                selectedTabIndex = uiState.selectedTabIndex,
                indicator = {},
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    MapTab(
                        title = title,
                        isSelected = uiState.selectedTabIndex == index,
                        onClick = { viewModel.updateSelectedTab(index) }
                    )
                }
            }
            if (uiState.selectedTabIndex == 0) {
                TourPage(
                    isTracking = uiState.isTracking,
                    currentLocation = uiState.currentLocation,
                    trackedLocations = uiState.trackedLocations,
                    lastTrackedLocations = if (uiState.trackHistory.isNotEmpty()) uiState.trackHistory.last().locations else emptyList(),
                    onStartTracking = { viewModel.toggleTracking(true) },
                    onStopTracking = {
                        viewModel.toggleTracking(false)
                        viewModel.saveMapData(email)
//                        viewModel.resetTracking()
                    },
                    onLocationUpdate = { viewModel.updateLocation(it) },
                    isShowTrack = uiState.showTrack,
                    onToogleShowTrack = {
                        viewModel.toggleShowTrack(it)
                    },
                    onStartTime = {
                        viewModel.updateStartTime(it)
                    },
                    onEndTime = {
                        viewModel.updateEndTime(it)
                    },
                    startTime = uiState.startTime,
                    endTime = uiState.endTime,
                )
            }
            if (uiState.selectedTabIndex == 1) {
                viewModel.showLocationHistory(email)
                HistoryPage(
                    historyItems = uiState.trackHistory,
                    isShowBottomSheet = uiState.isShowBottomSheet,
                    onToogleBottomSheet = { viewModel.toogleBottomSheet(it) },
                    onDeleteItem = {
                        viewModel.deleteDocument(email, it)
                    },
                )
            }

        }
    }
}

@Composable
fun MapTab(
    modifier: Modifier = Modifier,
    title: String = "Tab",
    isSelected: Boolean = false,
    onClick: () -> Unit,
    activeContainerColor: Color = MaterialTheme.colorScheme.primary,
    inactiveContainerColor: Color = MaterialTheme.colorScheme.background,
    activeContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    inactiveContentColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Tab(isSelected, onClick = onClick) {
        Column(
            modifier
                .background(if (isSelected) activeContainerColor else inactiveContainerColor)
                .padding(5.dp)
                .height(40.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (isSelected) activeContentColor else inactiveContentColor
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

// Content for Tour Tab
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TourPage(
    isTracking: Boolean,
    currentLocation: LatLng?,
    trackedLocations: List<LatLng>,
    lastTrackedLocations: List<LatLng>?,
    onStartTracking: () -> Unit,
    onStopTracking: () -> Unit,
    onLocationUpdate: (LatLng) -> Unit,
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
                    MapWithLocationTracking(
                        fusedLocationClient,
                        isTracking = isTracking,
                        currentLocation = currentLocation,
                        onLocationUpdate = onLocationUpdate,
                        trackedLocations = lastTrackedLocations,
                        isShowTrack = isShowTrack
                    )
                }
            } else {
                LocationPermissionDenied()
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
fun LocationPermissionDenied() {
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
fun MapWithLocationTracking(
    fusedLocationClient: FusedLocationProviderClient,
    isTracking: Boolean,
    currentLocation: LatLng?,
    onLocationUpdate: (LatLng) -> Unit,
    trackedLocations: List<LatLng>?,
    isShowTrack: Boolean,
) {
    val defaultZoom = 10f
    var zoomLevel by remember { mutableStateOf(defaultZoom) } // Track zoom level

    val cameraPositionState = rememberCameraPositionState {
        position =
            CameraPosition.fromLatLngZoom(currentLocation ?: LatLng(23.42, 90.20), defaultZoom)
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

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
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
                    onLocationUpdate.invoke(LatLng(it.latitude, it.longitude))
                }
            }
        }
    }

    // Display the map and update camera position based on current location

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLoaded = {
            currentLocation?.let {
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
        )
    ) {
        // Show the user's current location as a marker
        val currentMarker = context.vectorToMapMarker(
            R.drawable.ic_current_marker, MaterialTheme.colorScheme.primary
        )
        currentLocation?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Current Location",
                icon = currentMarker
            )
        }

        // If tracking is stopped, show the path
        if (isShowTrack && trackedLocations?.size!! > 1) {
            GradientPolyline(
                polylinePoints = trackedLocations,
                startColor = MaterialTheme.colorScheme.error,
                endColor = MaterialTheme.colorScheme.primary,
                width = 5f,
            )

            val startIcon = context.vectorToMapMarker(
                R.drawable.ic_marker_start
            )
            Marker(
                state = MarkerState(position = trackedLocations.first()),
                title = "Start",
                icon = startIcon
            )

            val endIcon = context.vectorToMapMarker(
                R.drawable.ic_marker, MaterialTheme.colorScheme.primary
            )
            Marker(
                state = MarkerState(position = trackedLocations.last()),
                title = "End",
                icon = endIcon
            )
        }
    }
}


// Content for History Tab
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryPage(
    modifier: Modifier = Modifier,
    historyItems: List<HistoryItem> = emptyList(),
    isShowBottomSheet: Boolean = false,
    onToogleBottomSheet: (Boolean) -> Unit,
    onDeleteItem: (String) -> Unit
) {
    val trackIndex = remember { mutableStateOf(HistoryItem("", "", "", "", "", emptyList())) }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(historyItems) { _, item ->
                HistoryCard(
                    item = item,
                    onClick = {
                        trackIndex.value = it
                        onToogleBottomSheet.invoke(!isShowBottomSheet)
                    },
                    onDelete = {
                        onDeleteItem.invoke(item.id)
                    }
                )
            }
        }
        if (isShowBottomSheet) {
            ModalBottomSheet(
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                ),
                onDismissRequest = {
                    onToogleBottomSheet.invoke(false)
                }
            ) {
                TrackMap(
                    modifier = Modifier.fillMaxSize(),
                    trackPoints = trackIndex.value.locations
                )
            }
        }
    }
}

@Composable
fun TrackMap(
    modifier: Modifier = Modifier,
    trackPoints: List<LatLng>,
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            calculateMidpoint(
                trackPoints.first(),
                trackPoints.last()
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
                polylinePoints = trackPoints,
                startColor = MaterialTheme.colorScheme.error,
                endColor = MaterialTheme.colorScheme.primary,
                width = 5f,
            )

            val startIcon = context.vectorToMapMarker(
                R.drawable.ic_marker_start
            )
            Marker(
                state = MarkerState(position = trackPoints.first()),
                title = "Start",
                icon = startIcon
            )

            val endIcon = context.vectorToMapMarker(
                R.drawable.ic_marker, MaterialTheme.colorScheme.primary
            )
            Marker(
                state = MarkerState(position = trackPoints.last()),
                title = "End",
                icon = endIcon
            )
        }
    }
}

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    item: HistoryItem,
    onClick: (HistoryItem) -> Unit,
    onDelete: () -> Unit
) {
    OutlinedCard(
        onClick = { onClick.invoke(item) },
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = item.title, style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(0.6f),
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = { onDelete() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Delete"
                    )
                }
            }
            Text(
                text = item.description, style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "Start Location: ${item.locations.first()}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = "End Location: ${item.locations.last()}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "Start Time: ${if (item.startTime.isNotEmpty()) item.startTime else "N/A"} ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = "End Time: ${if (item.endTime.isNotEmpty()) item.endTime else "N/A"} ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

fun calculateMidpoint(point1: LatLng, point2: LatLng): LatLng {
    val midLat = (point1.latitude + point2.latitude) / 2
    val midLng = (point1.longitude + point2.longitude) / 2
    return LatLng(midLat, midLng)
}


// Common Composable function
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

fun interpolateColor(
    startColor: Color,
    endColor: Color,
    fraction: Float,
    gamma: Float = 2.2f,
): Color {
    // Apply gamma correction to each color channel
    val startRed = startColor.red.pow(gamma)
    val startGreen = startColor.green.pow(gamma)
    val startBlue = startColor.blue.pow(gamma)

    val endRed = endColor.red.pow(gamma)
    val endGreen = endColor.green.pow(gamma)
    val endBlue = endColor.blue.pow(gamma)

    // Linear interpolation with gamma correction
    val r = ((startRed + fraction * (endRed - startRed)).coerceIn(0f, 1f)).pow(1 / gamma)
    val g = ((startGreen + fraction * (endGreen - startGreen)).coerceIn(0f, 1f)).pow(1 / gamma)
    val b = ((startBlue + fraction * (endBlue - startBlue)).coerceIn(0f, 1f)).pow(1 / gamma)

    // Keep alpha interpolation normal, or boost alpha like in the previous example
    val a = (startColor.alpha + fraction * (endColor.alpha - startColor.alpha)).coerceIn(0f, 1f)

    return Color(r, g, b, a)
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


@SuppressLint("UnrememberedMutableState")
@Composable
actual fun MapComponent(email: String) {
    MapScreen(onNavigation = {}, email)
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


//actual class LocationService {
//
//
//    actual suspend fun getCurrentLocation(): Location {
//
//    }
//}


