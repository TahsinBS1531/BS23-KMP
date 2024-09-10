package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.util.DropdownItem
import com.jetbrains.bs23_kmp.core.base.component.ProgressIndicatorSmall
import com.jetbrains.bs23_kmp.core.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/*
jetpack compose  editable list
when user click add button, dialog polutate wiht "items"
delete button appears on each item
when user click delete button, item will be deleted



 */
@Composable
fun AppListEditable(
    modifier: Modifier = Modifier,
    title: String,
    items: List<DropdownItem>,
    itemsAdded: List<DropdownItem>,
    icon: Int? = null,

    onAdd: (DropdownItem) -> Unit,
    onRemove: (DropdownItem) -> Unit,
    isError: Boolean = false,
    isResourceLoading: Boolean = false,
    isRemovable: (DropdownItem) -> Boolean = {true}

){

    val openDialog = rememberSaveable { mutableStateOf(false) }
//    ElevatedCard(
//        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.vertical)),
//    ){
//        AppComponentHeaderWithAction(
//            text = title,
//            onAction = {
//                openDialog.value = true
//            }
//        )
//        AppListRemovable(list = itemsAdded, onRemove = onRemove)
//        AppItemPickerDialog(
//            openDialog = openDialog.value,
//            items = items,
//            onItemSelected = onAdd,
//            itemText = { item ->
//                item.toString()
//            },
//            onDialogDismissed = {
//                openDialog.value = false
//            }
//        )
//
//
//    }
    AppCard(
        isError = isError,
        onClick = {openDialog.value = true}){
        SectionRow(
            onClick = { openDialog.value = true }
        ){
            AppText(
                text = title,
                icon = icon,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if(isResourceLoading){
                // linear progress indiactor
                ProgressIndicatorSmall(modifier = Modifier.size(16.dp))
            }
            else{
                FilledTonalIconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { openDialog.value = true }
                ) {
                    AppIcon(
                        modifier = Modifier.size(16.dp),
//                        icon = R.drawable.ic_add,
                    )
                }
            }

        }
        AppListRemovable(
            list = itemsAdded,
            onRemove = onRemove,
            isRemovable = isRemovable
        )
        AppItemPickerDialog(
            openDialog = openDialog.value,
            items = items,
            onItemSelected = onAdd,
            itemText = { item ->
                item.toString()
            },
            onDialogDismissed = {
                openDialog.value = false
            }
        )

    }


}

@Composable
@Preview
fun AppListEditablePreview(){
    AppTheme {
        AppListEditable(
            title = "Select Chemist",
            items = listOf(DropdownItem("1", "Chemist 1"), DropdownItem("2", "Chemist 2"), DropdownItem("3", "Chemist 3")),
            itemsAdded = listOf(DropdownItem("1", "Chemist 1"), DropdownItem("2", "Chemist 2")),
            onAdd = {},
            onRemove = {}
        )
    }
}