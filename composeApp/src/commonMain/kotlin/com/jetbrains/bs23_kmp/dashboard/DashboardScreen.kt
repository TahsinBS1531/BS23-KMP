package com.jetbrains.bs23_kmp.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.core.base.widget.EmptyView
import com.jetbrains.bs23_kmp.core.base.widget.LoadingView
import com.jetbrains.bs23_kmp.core.extensions.cast
import com.jetbrains.bs23_kmp.dashboard.presentation.DashboardCard
import com.jetbrains.bs23_kmp.dashboard.presentation.DashboardGraphCard
import com.jetbrains.bs23_kmp.dashboard.presentation.FullScreenDatePickerComponent
import com.jetbrains.bs23_kmp.dashboard.presentation.ShimmeringDashboardCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
) {
    val viewmodel = koinViewModel<DashboardViewModel>()

    val uiState by viewmodel.uiState.collectAsState()

    DashboardScreenBody(navController, Modifier, onEvent = {
        viewmodel.onTriggerEvent(it)
    }) { padding ->
        DashboardScreenPage(uiState, onEvent = {
            viewmodel.onTriggerEvent(it)
        }, Modifier.padding(padding), navController)
    }
//    LaunchedEffect(viewmodel) {
//        viewmodel.onTriggerEvent(DashboardViewEvent.GetUdCount( "2024-07-01", "2024-09-19"))
//        viewmodel.onTriggerEvent((DashboardViewEvent.GetAmCount("2024-07-01", "2024-09-19")))
//        viewmodel.onTriggerEvent((DashboardViewEvent.GetEocCount("2024-07-01", "2024-09-19")))
//        viewmodel.onTriggerEvent((DashboardViewEvent.GetFocCount("2024-07-01", "2024-09-19")))
//    }

}

@Composable
fun DashboardScreenBody(
    navController: NavController,
    modifier: Modifier = Modifier,
    onEvent: (DashboardViewEvent) -> Unit,
    pageContent: @Composable (PaddingValues) -> Unit
) {
    pageContent.invoke(PaddingValues(10.dp))
    Scaffold(
        topBar = {
//            AppToolbar(
//                navController,
//                R.string.Product_Catalog,
//                toolbarMenu = { AppToolbarOption() })
//            Text("Testing Screen")
        },
        content = { pageContent.invoke(it) },
    )

}

@Composable
fun DashboardScreenPage(
    uiState: BaseViewState<*>,
    onEvent: (DashboardViewEvent) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    when (uiState) {
        is BaseViewState.Data -> {
            val data = uiState.cast<BaseViewState.Data<DashboardViewState>>().value
            DashboardScreenContent(
                modifier,
                data,
                onEvent
            )
        }

        BaseViewState.Empty -> EmptyView()
        is BaseViewState.Error -> Text("Error While Fetching Data ")
        BaseViewState.Loading -> LoadingView()
    }

}

@Composable
fun DashboardScreenContent(
    modifier: Modifier = Modifier,
    data: DashboardViewState,
    onEvent: (DashboardViewEvent) -> Unit
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var selectedEvent: ((String, String) -> DashboardViewEvent)? by remember { mutableStateOf(null) }


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            if(data.udLoader){
                ShimmeringDashboardCard(modifier =Modifier)
            }else{
                DashboardCard(
                    modifier = Modifier,
                    title = "UD",
                    totalSubmitted = data.udCount?.submittedUD ?: 0,
                    normal = data.udCount?.normalUD ?: 0,
                    emergency = data.udCount?.emergencyUD ?: 0,
                    passedDispatch = data.udCount?.passedDispatchedUD ?: 0,
                    openDatePicker = {
                        selectedEvent = { fromDate, toDate ->
                            DashboardViewEvent.GetUdCount(fromDate, toDate)
                        }
                        isDatePickerVisible = true
                    }
                )
            }
        }
        item {
            if(data.amLoader){
                ShimmeringDashboardCard(modifier =Modifier)
            }else{
                DashboardCard(
                    modifier = Modifier,
                    title = "AM",
                    totalSubmitted = data.amCount?.submittedAM ?: 0,
                    normal = data.amCount?.normalAM ?: 0,
                    emergency = data.amCount?.emergencyAM ?: 0,
                    passedDispatch = data.amCount?.passedDispatchedAM ?: 0,
                    openDatePicker = {
                        selectedEvent = { fromDate, toDate ->
                            DashboardViewEvent.GetAmCount(fromDate, toDate)
                        }
                        isDatePickerVisible = true
                    }
                )
            }
        }

        item {
            if(data.focLoader){
                ShimmeringDashboardCard(modifier =Modifier)
            }else{
                DashboardCard(
                    modifier = Modifier,
                    title = "FOC",
                    totalSubmitted = data.focCount?.submittedFOC ?: 0,
                    normal = 50,
                    emergency = 50,
                    passedDispatch = data.focCount?.focApproved ?: 0,
                    openDatePicker = {
                        selectedEvent = { fromDate, toDate ->
                            DashboardViewEvent.GetFocCount(fromDate, toDate)
                        }
                        isDatePickerVisible = true
                    }
                )
            }
        }

        item {
            if(data.eocLoader){
                ShimmeringDashboardCard(modifier =Modifier)
            }else{
                DashboardCard(
                    modifier = Modifier,
                    title = "EO",
                    totalSubmitted = data.eocCount?.submittedEO ?: 0,
                    normal = data.eocCount?.normalEO ?: 0,
                    emergency = data.eocCount?.emergencyEO ?: 0,
                    passedDispatch = data.eocCount?.passedDispatchedEO ?: 0,
                    openDatePicker = {
                        selectedEvent = { fromDate, toDate ->
                            DashboardViewEvent.GetEocCount(fromDate, toDate)
                        }
                        isDatePickerVisible = true
                    }
                )
            }
        }
        item {
            DashboardGraphCard(
                modifier = Modifier,
                title = "Accessories Vs Consumption",
                openDatePicker = {}
            )
        }

    }
    if (isDatePickerVisible) {
        FullScreenDatePickerComponent(
            onDismissRequest = { isDatePickerVisible = false },
            onDateSelected = { fromDate, toDate ->
                selectedEvent?.let { eventCreator ->
                    onEvent(eventCreator(fromDate, toDate))
                }
                isDatePickerVisible = false
            }
        )
    }

}