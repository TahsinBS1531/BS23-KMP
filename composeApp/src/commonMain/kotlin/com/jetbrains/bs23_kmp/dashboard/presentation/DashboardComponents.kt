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
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import com.jetbrains.bs23_kmp.screens.components.PieChartItem
import com.jetbrains.bs23_kmp.screens.components.PieChartWithAnimatedLabels
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
) {
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
                }
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
    openDatePicker: () -> Unit
) {
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
            val data = listOf(
                PieChartItem(25f, MaterialTheme.colorScheme.primary, "Red"),
                PieChartItem(35f, MaterialTheme.colorScheme.secondary, "Green"),
                PieChartItem(20f, MaterialTheme.colorScheme.tertiary, "Blue"),
                PieChartItem(20f, MaterialTheme.colorScheme.primaryContainer, "Yellow")
            )

            PieChartWithAnimatedLabels(
                items = data,
                modifier = Modifier.padding(16.dp),
                title = "UD"
            )

            PieChartWithAnimatedLabels(
                items = data,
                modifier = Modifier.padding(16.dp),
                title = "AM"
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
