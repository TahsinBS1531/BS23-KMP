package com.jetbrains.bs23_kmp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.core.base.widget.EmptyView
import com.jetbrains.bs23_kmp.core.base.widget.LoadingView
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen1(onNavigation: (String?) -> Unit, email: String, navController: NavController, viewModel: HomeViewModel) {
//    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scheme = MaterialTheme.colorScheme

    MapScreenBody1(
        modifier = Modifier,
        uiState = uiState,
        onEvent = { viewModel.onTriggerEvent(it) }, email,
        navController
    )
}

@Composable
fun MapScreenBody1(
    modifier: Modifier = Modifier,
    uiState: BaseViewState<*>,
    onEvent: (HomeScreenEvent) -> Unit,
    email: String,
    navController: NavController
) {
    when (uiState) {
        is BaseViewState.Data -> {
            val state = uiState.value as? HomeSceenState

            if (state != null) {
                MapScreenContent1(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    uiState = state, onEvent = onEvent,
                    email,
                    navController
                )
            }
        }

        BaseViewState.Empty -> EmptyView()
        is BaseViewState.Error -> Text("Error while Loading the state")
        BaseViewState.Loading -> LoadingView()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenContent1(
    modifier: Modifier = Modifier,
    uiState: HomeSceenState,
    onEvent: (HomeScreenEvent) -> Unit,
    email: String,
    navController: NavController
) {
    val tabs = listOf("Tour", "History")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
    ) {
        Column(modifier =Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome $email",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Explore the city with us",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            TextButton(onClick ={
                onEvent(HomeScreenEvent.SignOut)
                navController.navigate("login")

            }){
                Text(text = "Sign Out")
            }

        }
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PrimaryTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .height(48.dp)
                    .clip(
                        RoundedCornerShape(25)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(25)
                    ),
                selectedTabIndex = uiState.selectedTabIndex,
                indicator = {},
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    MapTab(
                        title = title,
                        isSelected = uiState.selectedTabIndex == index,
                        onClick = { onEvent(HomeScreenEvent.updateSelectionTab(index)) }
                    )
                }
            }
            if (uiState.selectedTabIndex == 0) {
                MapTourPage(
                    isTracking = uiState.isTracking,
                    currentLocation = uiState.currentLocation,
                    trackedLocations = uiState.trackedLocations,
                    lastTrackedLocations = if (uiState.trackHistory.isNotEmpty()) uiState.trackHistory.last().locations else emptyList(),
                    onStartTracking = { onEvent(HomeScreenEvent.toggleTracking(true)) },
                    onStopTracking = {
                        onEvent(HomeScreenEvent.toggleTracking(false))
                        onEvent(HomeScreenEvent.saveMapData(email))
                    },
                    onLocationUpdate = {
                        onEvent(HomeScreenEvent.updateLocation(it))
                    },
                    isShowTrack = uiState.showTrack,
                    onToogleShowTrack = {
                        onEvent(HomeScreenEvent.toggleShowTrack(it))
                    },
                    onStartTime = {
                        onEvent(HomeScreenEvent.updateStartTime(it))
                    },
                    onEndTime = {
                        onEvent(HomeScreenEvent.updateEndTime(it))
                    },
                    startTime = uiState.startTime,
                    endTime = uiState.endTime,
                )
            }
            if (uiState.selectedTabIndex == 1) {
                onEvent(HomeScreenEvent.showLocationHistory(email))
                HistoryPage(
                    historyItems = uiState.trackHistory,
                    isShowBottomSheet = uiState.isShowBottomSheet,
                    onToogleBottomSheet = {
                        onEvent(HomeScreenEvent.toogleBottomSheet(it))
                    },
                    onDeleteItem = {
                        onEvent(HomeScreenEvent.deleteDocument(email,it))
                        onEvent(HomeScreenEvent.showLocationHistory(email))
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

@Composable
expect fun MapTourPage(
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
)

@Composable
expect fun MapLocationPermissionDenied()

@Composable
expect fun MapWithLocationTracking1(
    isTracking: Boolean,
    currentLocation: CoordinatesData?,
    onLocationUpdate: (CoordinatesData) -> Unit,
    trackedLocations: List<CoordinatesData>?,
    isShowTrack: Boolean,
)



// Content for History Tab
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryPage(
    modifier: Modifier = Modifier,
    historyItems: List<MapHistoryItem> = emptyList(),
    isShowBottomSheet: Boolean = false,
    onToogleBottomSheet: (Boolean) -> Unit,
    onDeleteItem: (String) -> Unit
) {
    val trackIndex = remember { mutableStateOf(MapHistoryItem("", "", "", "", "", emptyList())) }
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
                TrackMap1(
                    modifier = Modifier.fillMaxSize(),
                    trackPoints = trackIndex.value.locations
                )
            }
        }
    }
}

@Composable
expect fun TrackMap1(modifier: Modifier =Modifier, trackPoints: List<CoordinatesData>)


@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    item: MapHistoryItem,
    onClick: (MapHistoryItem) -> Unit,
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
                IconButton(onClick = {
                    onDelete()
                }) {
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

fun calculateMidpoint(point1: CoordinatesData, point2: CoordinatesData): CoordinatesData {
    val midLat = (point1.latitude + point2.latitude) / 2
    val midLng = (point1.longitude + point2.longitude) / 2
    return CoordinatesData(midLat, midLng)
}

@Composable
expect fun GradientPolyline1(
    polylinePoints: List<CoordinatesData>,
    startColor: Color,
    endColor: Color,
    width: Float = 5f
)


fun interpolateColor(
    startColor: Color,
    endColor: Color,
    fraction: Float,
    gamma: Float = 2.2f,
): Color {
    val startRed = startColor.red.pow(gamma)
    val startGreen = startColor.green.pow(gamma)
    val startBlue = startColor.blue.pow(gamma)

    val endRed = endColor.red.pow(gamma)
    val endGreen = endColor.green.pow(gamma)
    val endBlue = endColor.blue.pow(gamma)

    val r = ((startRed + fraction * (endRed - startRed)).coerceIn(0f, 1f)).pow(1 / gamma)
    val g = ((startGreen + fraction * (endGreen - startGreen)).coerceIn(0f, 1f)).pow(1 / gamma)
    val b = ((startBlue + fraction * (endBlue - startBlue)).coerceIn(0f, 1f)).pow(1 / gamma)

    val a = (startColor.alpha + fraction * (endColor.alpha - startColor.alpha)).coerceIn(0f, 1f)

    return Color(r, g, b, a)
}




