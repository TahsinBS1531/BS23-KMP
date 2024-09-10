package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePlaceHolder(
    onDismissRequest: () -> Unit,
    onDateRangeChanged: (
        from: LocalDate,
        to: LocalDate
    ) -> Unit,
) {

    val state = rememberDateRangePickerState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onDismissRequest) {
                Icon(Icons.Filled.Close, contentDescription = "Localized description")
            }
            TextButton(
                onClick = {
//                   onDateRangeChanged(
//                       state.selectedStartDateMillis?.toDate() ?: LocalDate.now(),
//                       state.selectedEndDateMillis?.toDate() ?: LocalDate.now()
//                   )
                    onDismissRequest()
                },
                enabled = state.selectedEndDateMillis != null
            ) {
                Text(text = "Save")
            }
        }
//        DateRangePicker(
//            state = state, modifier = Modifier.weight(1f),
//            dateFormatter = DatePickerFormatter("dd MM yy", "dd MM yy", "dd MM yy"),
//        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDaterRangePicker(
    openDialog: Boolean,
    onDialogDismiss: () -> Unit,

    fromDate: LocalDate?,
    toDate: LocalDate?,
    onDateRangeChanged: (
        from: LocalDate,
        to: LocalDate
    ) -> Unit,

    onCloseDateFilter: (() -> Unit)? = null,
    onOpenDateRangePicker: (() -> Unit)? = null,
) {
//    val openDialog = remember { mutableStateOf(showDateRangeFilter) }
    if(fromDate != null && toDate != null) {

        AppSection {
            AppOutlinedCard(
                text = "Date Filter",
//                actionIcon = if (onCloseDateFilter==null) null else R.drawable.ic_close,
                onActionClick = {
                    if (onCloseDateFilter != null) {
                        onCloseDateFilter()
                    }
                }
            ) {
//                AppSection {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row {
//                            ElevatedAssistChip(
//                                onClick = {
//                                     onOpenDateRangePicker?.invoke()
//                                },
//                                label = { Text(text = "${fromDate.toDisplayDate()}") }
//                            )

                            Text(
                                text = "-",
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterVertically)
                                    .padding(horizontal = 8.dp)
                            )
//
//                            ElevatedAssistChip(
//                                onClick = {
//                                    onOpenDateRangePicker?.invoke()
//
//                                },
//                                label = { Text(text = "${toDate?.toDisplayDate()}") }
//                            )
                        }
                    }
//                }
            }

        }
    }



    if (openDialog) {
        AlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDialogDismiss) {
            AppCard{
                DateRangePlaceHolder(
                    onDismissRequest = onDialogDismiss,
                    onDateRangeChanged = onDateRangeChanged,
                )
            }
        }
    }
}

@Preview
@Composable
fun DateRangePickerSamplePreview() {
    DateRangePlaceHolder(
        onDismissRequest = {},
        onDateRangeChanged = { from, to ->

        }
    )
}