package com.jetbrains.bs23_kmp.core.ui



import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerTillDate(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onDatePicked: (DatePickerState) -> Unit,
) {
//    val calendar = Calendar.getInstance()
    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDatePicked(datePickerState)
                        onDismissRequest()
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Cancel")
                }
            }
        ){
//            val tillDay = calendar.apply {
//                calendar.add(Calendar.DATE, -1);
//            }
//
//            DatePicker(state = datePickerState, dateValidator = {
//                it < tillDay.timeInMillis
//            })
        }
    }

}