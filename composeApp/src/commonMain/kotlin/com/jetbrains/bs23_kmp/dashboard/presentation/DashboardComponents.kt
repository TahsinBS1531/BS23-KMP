package com.jetbrains.bs23_kmp.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.dashboard.model.remote.AmAccessoriesConsumptionResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.StageWiseSubResponse
import com.jetbrains.bs23_kmp.dashboard.model.remote.UdAccessoriesConsumptionResponse
import com.jetbrains.bs23_kmp.screens.components.PieChartItem
import com.jetbrains.bs23_kmp.screens.components.PieChartWithoutLines
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    title: String = "",
    totalSubmitted: Int = 0,
    normal: Int = 0,
    emergency: Int = 0,
    passedDispatch: Int = 0,
    openDatePicker: () -> Unit,
    bgColor: Color = Color.White,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = bgColor,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                FilterDropdown(
                    modifier = Modifier,
                    openDatePicker,
                )
            }
            HorizontalDivider()
            Text(
                "Total Submitted : $totalSubmitted",
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Normal: $normal")
                Text("Emergency: $emergency")

            }
            HorizontalDivider()
            Text(
                "Total Passed & Dispatched : $passedDispatch",
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}

@Composable
fun FilterDropdown(
    modifier: Modifier = Modifier,
    openDatePicker: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Box {
            FilterChip(
                onClick = {
                    openDatePicker()
                },
                label = { Text("Select Date", style = MaterialTheme.typography.bodyMedium) },
                selected = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White,
                    selectedContainerColor = Color.White,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDatePickerComponent(
    onDismissRequest: () -> Unit,
    onDateSelected: (String, String) -> Unit
) {
    val state = rememberDateRangePickerState()

    val selectedStartDate = state.selectedStartDateMillis
    val selectedEndDate = state.selectedEndDateMillis

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { onDismissRequest() }) {
                    Text("Cancel")
                }

                TextButton(onClick = {
                    if (selectedStartDate != null && selectedEndDate != null) {
                        onDateSelected(
                            selectedStartDate.formatTimestampToDate(),
                            selectedEndDate.formatTimestampToDate()
                        )
                    }
                    onDismissRequest()
                }) {
                    Text("Save")
                }
            }

            DateRangePicker(
                state = state,
                title = {
                    DateRangePickerDefaults.DateRangePickerTitle(
                        displayMode = state.displayMode,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun DashboardGraphCard(
    modifier: Modifier = Modifier,
    title: String,
    openDatePicker: () -> Unit,
    amData: AmAccessoriesConsumptionResponse,
    udData: UdAccessoriesConsumptionResponse,
    isLoading: Boolean = false,
) {
    val amTotal = (amData.totalAMAccessories ?: 0) + (amData.totalAMConsumptions ?: 0) + (amData.both ?: 0)
    val udTotal = (udData.totalUDAccessories ?: 0) + (udData.totalUDConsumptions ?: 0) + (udData.both ?: 0)
//    if (amTotal == 0 || udTotal == 0) return

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isLoading) "" else title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                        .then(if (isLoading) Modifier.shimmer() else Modifier)

                )
                FilterDropdown(
                    modifier = if(isLoading) Modifier.shimmer() else Modifier,
                    openDatePicker,
                )
            }
            HorizontalDivider(modifier = if (isLoading) Modifier.shimmer() else Modifier)
            val udDataItems = listOf(
                PieChartItem((udData.totalUDAccessories?.toFloat() ?: 0f)/udTotal*100f, MaterialTheme.colorScheme.primary, "Total Ud Accessories: ${udData.totalUDAccessories}"),
                PieChartItem((udData.totalUDConsumptions?.toFloat() ?: 0f)/udTotal*100f, MaterialTheme.colorScheme.secondary, "Total Ud Consumptions: ${udData.totalUDConsumptions}"),
                PieChartItem((udData.both?.toFloat() ?: 0f)/udTotal*100f, MaterialTheme.colorScheme.tertiary, "Both: ${udData.both}"),
            )

            val amDataItems = listOf(
                PieChartItem((amData.totalAMAccessories?.toFloat() ?: 0f)/amTotal*100f, MaterialTheme.colorScheme.primary, "Total AM Accessories: ${amData.totalAMAccessories}"),
                PieChartItem((amData.totalAMConsumptions?.toFloat() ?: 0f)/amTotal*100f, MaterialTheme.colorScheme.secondary, "Total AM Consumptions: ${amData.totalAMConsumptions}"),
                PieChartItem((amData.both?.toFloat() ?: 0f)/amTotal*100f, MaterialTheme.colorScheme.tertiary, "Both: ${amData.both}"),
            )

            PieChartWithoutLines(
                items = amDataItems,
//                modifier = Modifier.background(Color.Red),
                title = "AM",
                isLoading = isLoading,
            )
            HorizontalDivider(modifier = if (isLoading) Modifier.shimmer() else Modifier)

            PieChartWithoutLines(
                items = amDataItems,
                modifier = Modifier,
                title = "UD",
                isLoading = isLoading,
            )
        }
    }
}

@Composable
fun StageWiseGraphCard(
    modifier: Modifier = Modifier,
    title: String,
    openDatePicker: () -> Unit,
    data: List<StageWiseSubResponse>,
    isLoading: Boolean = false,
) {
    val eoData = data.filter { it.application == "EO" }
    val focData = data.filter { it.application == "FOC" }
    val udAmData = data.filter { it.application == "UDAM" }

    val eoTotal = eoData.sumOf { it.applicationCount?.toInt()?:0 }
    val focTotal = focData.sumOf { it.applicationCount?.toInt()?:0 }
    val udAmTotal = udAmData.sumOf { it.applicationCount?.toInt()?:0 }

    val pieChartColors = listOf(
        MaterialTheme.colorScheme.primary,   // First color
        MaterialTheme.colorScheme.secondary, // Second color
        MaterialTheme.colorScheme.tertiary,  // Third color
        Color.Red,                           // Fourth color
        Color.Green,                         // Fifth color
        Color.Blue                           // Sixth color
    )

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isLoading) "" else title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                        .then(if (isLoading) Modifier.shimmer() else Modifier)
                )
                FilterDropdown(
                    modifier = if (isLoading) Modifier.shimmer() else Modifier,
                    openDatePicker,
                )
            }
            HorizontalDivider(modifier = if (isLoading) Modifier.shimmer() else Modifier)
            val eoDataItems = eoData.mapIndexed { index, stageWiseSubResponse ->
                PieChartItem(
                    (stageWiseSubResponse.applicationCount?.toFloat() ?: 0f) / eoTotal * 100f,
                    pieChartColors[index % pieChartColors.size],
                    "${stageWiseSubResponse.statusDescription}: ${stageWiseSubResponse.applicationCount}"
                )
            }
            val focDataItems = focData.mapIndexed { index, stageWiseSubResponse ->
                PieChartItem(
                    (stageWiseSubResponse.applicationCount?.toFloat() ?: 0f) / focTotal * 100f,
                    pieChartColors[index % pieChartColors.size],
                    "${stageWiseSubResponse.statusDescription}: ${stageWiseSubResponse.applicationCount}"
                )
            }

            val udAmDataItems = udAmData.mapIndexed { index, stageWiseSubResponse ->
                PieChartItem(
                    (stageWiseSubResponse.applicationCount?.toFloat() ?: 0f) / udAmTotal * 100f,
                    pieChartColors[index % pieChartColors.size],
                    "${stageWiseSubResponse.statusDescription}: ${stageWiseSubResponse.applicationCount}"
                )
            }

            PieChartWithoutLines(
                items = eoDataItems,
                title = "EO",
                isLoading = isLoading,
            )
            HorizontalDivider(modifier = if (isLoading) Modifier.shimmer() else Modifier)

            PieChartWithoutLines(
                items = focDataItems,
                title = "FOC",
                isLoading = isLoading,
            )
            HorizontalDivider(modifier = if (isLoading) Modifier.shimmer() else Modifier)

            PieChartWithoutLines(
                items = udAmDataItems,
                title = "UDAM",
                isLoading = isLoading,
            )
        }
    }
}


fun Long.formatTimestampToDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.year}-${
        dateTime.monthNumber.toString().padStart(2, '0')
    }-${dateTime.dayOfMonth.toString().padStart(2, '0')}"
}


//@Preview(showBackground = true)
//@Composable
//fun DashboardCardPreview() {
//    AppTheme {
//        DashboardCard(modifier = Modifier.padding(16.dp))
//    }
//}
