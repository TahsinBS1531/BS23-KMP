package com.jetbrains.bs23_kmp.core.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.ui.AppIcon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppFilterDropdown(
    modifier: Modifier = Modifier,
    options: List<T>,
    label: String,
    selectedOption: T?,
    enabled: Boolean = true,
    onSelectionChange: (T?) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable { if(enabled) expanded.value = true }
    ) {
//        OutlinedTextField(
//            value = selectedOption?.toString() ?: "",
//            onValueChange = { },
//            label = { Text(label) },
//            trailingIcon = {
//                Icon(
//                    imageVector = Icons.Default.ArrowDropDown,
//                    contentDescription = "Dropdown Arrow",
//                    modifier = Modifier.clickable { if(enabled) expanded.value = true }
//                )
//            },
//            modifier = Modifier
//                .onFocusChanged { focusState ->
//                    if (focusState.isFocused) {
//                        expanded.value = true
//                    }
//                }
//                .focusable(false)
//                .fillMaxWidth()
//                .padding(vertical = 4.dp),
//            readOnly = true,
//            enabled = enabled
//        )

//        TextButton(
//            onClick = {
//                expanded.value = true
//            }
//        ){
//            LabelIcon(
//                label = "Filter",
//                icon = painterResource(id = R.drawable.ic_filter)
//            )
//        }

//        FilledTonalIconButton(
//            onClick = { expanded.value = true }
//        ) {
//            AppIcon(
//                icon = R.drawable.ic_filter
//            )
//        }

        AssistChip(
            leadingIcon = {
                AppIcon(
//                    icon = R.drawable.ic_filter
                )
            },
            onClick = { expanded.value = true},
            label = {
                Text(
                    text = selectedOption?.toString() ?: label,
                )
            },
        )


        DropdownMenu(
            modifier = Modifier.fillMaxWidth(0.6f),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp), text = option.toString()
                        )
                    },
                    onClick = {
                        onSelectionChange(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}
