package com.jetbrains.bs23_kmp.screens.home

import KottieAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.core.base.widget.EmptyView
import contentScale.ContentScale
import kmp_app_template.composeapp.generated.resources.Res
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import utils.KottieConstants
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen1(
    onNavigation: (String?) -> Unit,
    email: String,
    navController: NavController,
    viewModel: HomeViewModel
) {
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
        BaseViewState.Loading -> LottieLoader()
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LottieLoader(){
    var animation by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/lottie3.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation) // Or KottieCompositionSpec.Url || KottieCompositionSpec.JsonString
    )

    var playing by remember { mutableStateOf(true) }

    val animationState by animateKottieCompositionAsState(
        composition = composition,
//        isPlaying = playing,
        reverseOnRepeat = true,
        iterations = KottieConstants.IterateForever
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        KottieAnimation(
            composition = composition,
            progress = { animationState.progress },
            modifier = Modifier.size(150.dp).align(Alignment.Center),
            contentScale = ContentScale.Crop
        )

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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        MapTourPage(
            modifier = Modifier.fillMaxSize(),
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
            email = email,
            navController = navController
        )

        IconButton(
            onClick = {
                navController.navigate("history/$email")
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .size(50.dp)
        ) {
            Icon(
                Icons.Default.Settings, "Settings", tint = MaterialTheme.colorScheme.onPrimary
            )
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
)

@Composable
expect fun MapLocationPermissionDenied()

@Composable
expect fun MapWithLocationTracking1(
    modifier: Modifier,
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
    email: String,
    navController: NavController,
    viewModel: HomeViewModel

) {
    val uiState = viewModel.uiState.collectAsState().value

    when (uiState) {
        is BaseViewState.Data -> {

            val state = uiState.value as? HomeSceenState
            if(state?.historyLoader ==true){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }else{
                val historyItems = state?.trackHistory ?: emptyList()
                val isShowBottomSheet = state?.isShowBottomSheet ?: false

                val trackIndex =
                    remember { mutableStateOf(MapHistoryItem("", "", "", "", "", emptyList())) }

                LaunchedEffect(Unit) {
                    viewModel.onTriggerEvent(HomeScreenEvent.showLocationHistory(email))
                }

                println("Email: $email")

                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }, modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        println("sign out value : ${state?.isSignedOut}")
                        if (state?.isSignedOut == true) {
                            viewModel.onTriggerEvent(HomeScreenEvent.resetState)
                            LaunchedEffect(Unit) {
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }

                        TextButton(
                            onClick = {
                                viewModel.onTriggerEvent(HomeScreenEvent.SignOut)
//                                navController.navigate("login"){
//                                    popUpTo("home")
//                                    launchSingleTop = true
//                                }
                            }, modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text("Sign Out")
                        }
                    }

                    println("historyItems: $historyItems")


                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    ) {
                        if(historyItems.isEmpty()) {
                            item {
                                Text(
                                    text = "No history found",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = Color.Gray
                                )
                            }
                        }else{
                            itemsIndexed(historyItems) { _, item ->
                                HistoryCard(
                                    item = item,
                                    onClick = {
                                        trackIndex.value = it
                                        viewModel.onTriggerEvent(HomeScreenEvent.toogleBottomSheet(!isShowBottomSheet))
                                    },
                                    onDelete = {
                                        viewModel.onTriggerEvent(
                                            HomeScreenEvent.deleteDocument(
                                                email,
                                                item.id
                                            )
                                        )
                                        viewModel.onTriggerEvent(HomeScreenEvent.showLocationHistory(email))
                                    }
                                )
                            }
                        }
                    }
                    if (isShowBottomSheet) {
                        ModalBottomSheet(
                            sheetState = rememberModalBottomSheetState(
                                skipPartiallyExpanded = true
                            ),
                            onDismissRequest = {
                                viewModel.onTriggerEvent(HomeScreenEvent.toogleBottomSheet(false))
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
        }

        BaseViewState.Empty -> EmptyView()
        is BaseViewState.Error -> Text("Error while Loading the state")
        BaseViewState.Loading -> LottieLoader()
    }

}

@Composable
expect fun TrackMap1(modifier: Modifier = Modifier, trackPoints: List<CoordinatesData>)

@Composable
expect fun GetLocationName(item: CoordinatesData): String

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    item: MapHistoryItem,
    onClick: (MapHistoryItem) -> Unit,
    onDelete: () -> Unit
) {
    if (item.locations.isEmpty()) {
        return
    }

    val startLocation = GetLocationName(item.locations.first())
    val endLocation = GetLocationName(item.locations.last())
    OutlinedCard(
        onClick = { onClick.invoke(item) },
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
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
            HorizontalDivider()
//            Text(
//                text = item.description, style = MaterialTheme.typography.bodyLarge.copy(
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//            )
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Start Location: $startLocation",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "End Location: $endLocation",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            HorizontalDivider()

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
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




