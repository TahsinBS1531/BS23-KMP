package com.jetbrains.bs23_kmp.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.core.base.widget.EmptyView
import com.jetbrains.bs23_kmp.core.base.widget.LoadingView
import com.jetbrains.bs23_kmp.core.extensions.cast
import com.jetbrains.bs23_kmp.dashboard.presentation.DashboardCard
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
    LaunchedEffect(viewmodel) {
        viewmodel.onTriggerEvent(
            DashboardViewEvent.GetAccessoriesConsumption(
                "2024-07-01",
                "2024-09-19"
            )
        )
    }

}

@Composable
fun DashboardScreenBody(
    navController: NavController,
    modifier: Modifier = Modifier,
    onEvent: (DashboardViewEvent) -> Unit,
    pageContent: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
//            AppToolbar(
//                navController,
//                R.string.Product_Catalog,
//                toolbarMenu = { AppToolbarOption() })
            Text("Testing Screen")
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DashboardCard(
            modifier = Modifier.padding(16.dp),
            totalSubmitted = data.udCount?.submittedUD ?: 0,
            normal = data.udCount?.normalUD ?: 0,
            emergency = data.udCount?.emergencyUD ?: 0,
            passedDispatch = data.udCount?.passedDispatchedUD ?: 0,
            onDateSelected = { fromDate, toDate ->
                println("From Date $fromDate")
                println("To Date $toDate")
                onEvent(
                    DashboardViewEvent.GetUdCount(
                        fromDate,
                        toDate
                    )
                )
            }
        )
    }

}