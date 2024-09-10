package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
//    blockedDate: DateT? = null,
    onDatePicked: (DatePickerState) -> Unit,
) {
//    val context = LocalContext.current
//    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }

    // Fetching current year, month and day
//    val year = calendar[Calendar.YEAR]
//    val month = calendar[Calendar.MONTH]
//    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

// Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
// TODO demo how to read the selected date from the state.
    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDatePicked(datePickerState)
                        onDismissRequest()
//                        snackScope.launch {
//                            snackState.showSnackbar(
//                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"
//                            )
//                        }
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


//        val tillDay = calendar.apply {
//            calendar.add(Calendar.DATE, -1);
//        }
//
//            DatePicker(state = datePickerState, dateValidator = {
//                if(blockedDate != null){
//                    it> blockedDate.time
//                }else{
//                    it >= tillDay.timeInMillis
//
//                }
//                it >= tillDay.timeInMillis
//            })
        }
    }

}