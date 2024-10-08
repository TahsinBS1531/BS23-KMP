package com.jetbrains.bs23_kmp.dashboard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    totalSubmitted:Int =0,
    normal:Int =0,
    emergency:Int =0,
    passedDispatch:Int=0,
    onDateSelected:(String,String)->Unit
) {
    OutlinedCard (modifier =modifier.fillMaxWidth()){
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("UD", style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
                FilterDropdown(
                    modifier = Modifier,
                    onDateSelected = onDateSelected
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
    onDateSelected:(String,String)->Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Box {
            FilterChip(
                onClick = { expanded = true },
                label = { Text("Select Date", style = MaterialTheme.typography.bodyMedium) },
                selected = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            )
            if (expanded) {
                FullScreenDatePickerComponent(
                    onDismissRequest = { expanded = false },
                    onDateSelected = onDateSelected
                )
            }

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

    val selectedStartDate = state.selectedStartDateMillis?.let { it }
    val selectedEndDate = state.selectedEndDateMillis?.let { it }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Date Range") },
                actions = {
                    TextButton(onClick = {
                        if (selectedStartDate != null && selectedEndDate != null) {
                            onDateSelected(formatTimestampToDate(selectedStartDate), formatTimestampToDate(selectedEndDate))
                        }
                        onDismissRequest()
                    }) {
                        Text("Save")
                    }
                    TextButton(onClick = { onDismissRequest() }) {
                        Text("Cancel")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DateRangePicker(
                state = state,
                title = {
                    DateRangePickerDefaults.DateRangePickerTitle(
                        displayMode = state.displayMode,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


fun formatTimestampToDate(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
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
