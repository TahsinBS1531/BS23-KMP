package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun <T> AppSingleSelectWithDetails(
    modifier: Modifier = Modifier,
    title: String,
    items: List<T>,
    selectedItem: T?,
    isError: Boolean = false,
    onSelectionChanged: (T) -> Unit,
    itemText: (T) -> String ,
    secondaryText: (T) -> String = { "" },
    supportingText: (T) -> String = { "" },
    actionIcon: Int? = null,
    content: @Composable (T) -> Unit
){
    var showItemPicker by rememberSaveable { mutableStateOf(false) }

    val errorColor = MaterialTheme.colorScheme.error
    val errorShape = MaterialTheme.shapes.small


    AppSelectable(
        text = title,
        actionIcon = actionIcon,
        onClick = {showItemPicker = true},
        ) {
        selectedItem?.let{content.invoke(it)}
    }
    AppItemPickerDialog(
        openDialog  = showItemPicker,
        items = items,
        onItemSelected = { item ->
            onSelectionChanged(item)
            showItemPicker = false
        },
        itemText = itemText,
        secondaryText = secondaryText,
        supportingText = supportingText,
        onDialogDismissed = {
            showItemPicker = false
        }
    )

}