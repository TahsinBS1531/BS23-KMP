package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jetbrains.bs23_kmp.core.util.getCurrentDate
import com.jetbrains.bs23_kmp.core.util.getYesterdayDate
import com.jetbrains.bs23_kmp.core.util.toDate
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppSearchBarWithFilter(
    hint: String,
    leading: @Composable () -> Unit,
    trailing: (@Composable () -> Unit)? = null,
    options: List<T>,
    expanded: MutableState<Boolean>,
    selections: List<T>,
    dateShow: MutableState<Boolean>,
    onFilterCleared: () -> Unit,
) {
    val text = remember { mutableStateOf("") } // TODO:
    val isActive = remember { mutableStateOf(false) } // TODO:

    val fromDate = remember { mutableStateOf(getYesterdayDate()) }
    val toDate = remember { mutableStateOf(getCurrentDate()) }
    val fromTap = remember { mutableStateOf(false) }
    val toTap = remember { mutableStateOf(false) }

    if (fromTap.value) {
        AppDatePicker(
            openDialog = fromTap.value,
            onDismissRequest = { fromTap.value = false }) { datePickerState ->
            datePickerState.selectedDateMillis?.toDate()?.let { date ->
                if (fromTap.value) {
                    fromDate.value = date.toString()
                    fromTap.value = false
                }
            }
        }
    }
    if (toTap.value) {
        AppDatePicker(
            openDialog = toTap.value,
            onDismissRequest = { fromTap.value = false }) { datePickerState ->
            datePickerState.selectedDateMillis?.toDate()?.let { date ->
                if (toTap.value) {
                    toDate.value = date.toString()
                    toTap.value = false
                }
            }
        }
    }

    Column {
        DockedSearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            query = text.value,
            onQueryChange = {
                text.value = it
            },
            onSearch = {},
            active = isActive.value,
            onActiveChange = {
                isActive.value = it
            },
            leadingIcon = leading,
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            trailingIcon = trailing,
            content = {},
        )
        if (dateShow.value) {
            AppSpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    SuggestionChip(
                        onClick = { fromTap.value = true },
                        label = { Text(text = fromDate.value) }
                    )
                    AppSpacer()
                    Text(
                        text = "-",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    AppSpacer()
                    SuggestionChip(
                        onClick = { toTap.value = true },
                        label = { Text(text = toDate.value) }
                    )
                }
                AppIconButton(
//                    icon = R.drawable.ic_close,
                ) {
                    fromDate.value = getYesterdayDate()
                    fromTap.value = false
                    toDate.value = getCurrentDate()
                    toTap.value = false
                    dateShow.value = false
                }
            }
        }
        if (expanded.value) {
            AppSpacer()
            FilterPlaceHolder(onFilterCleared = onFilterCleared, {}) {
                AppFilter(
                    text = "By Category",
                    items = options,
                    selections = selections,
                    isSingleSelect = true,
                    onItemSelect = {
//                    onEvent(OrderHistoryEvent.OnCreatorFilterSelected(it))
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun AppSearchPreview() {
//    val expanded = remember { mutableStateOf(true) }
//    val list = listOf(
//        Drop("All Filter"),
//        Drop("Cat1"),
//    )
//
//    AppTheme {
//        AppSearchBarWithFilter(
//            hint = "Search Employee",
//            leading = {
//                AppIcon(
//                    icon = R.drawable.ic_search
//                )
//            },
//            trailing = {
//                FilledTonalIconButton(
//                    onClick = {
//                        expanded.value = !expanded.value
//                    }
//                ) {
//                    AppIcon(
//                        icon = R.drawable.ic_family
//                    )
//                }
//            },
//            list,
//            expanded,
//            onChanged = {expanded.value = true}
//        )
//    }
}