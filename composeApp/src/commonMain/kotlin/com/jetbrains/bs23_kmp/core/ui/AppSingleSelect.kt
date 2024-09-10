package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.util.DropdownItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSingleSelect(
    modifier: Modifier = Modifier,
    title: String,
    items: List<DropdownItem>,
    selectedItem: DropdownItem?,
    isError: Boolean = false,
    onSelectionChanged: (DropdownItem) -> Unit,
//    @DrawableRes icon: Int? = null

) {
    var showItemPicker by rememberSaveable { mutableStateOf(false) }

    val errorColor = MaterialTheme.colorScheme.error
    val errorShape = MaterialTheme.shapes.small

    /*
    ElevatedCard(
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.item_vertical_padding))
            .conditional(isError) {
                border(
                    width = 1.dp,
                    color = errorColor,
                    shape = errorShape
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(dimensionResource(id = R.dimen.column_padding))
//            ) {
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                )
//                OutlinedIconButton(onClick = {
//                    showItemPicker = true
//                }, modifier = Modifier.size(24.dp)) {
//                    Icon(
//                        Icons.Outlined.Add,
//                        contentDescription = "Add Item",
//                        modifier = Modifier.size(16.dp)
//                    )
//                }
//            }
            AppComponentHeaderWithAction(
                text = title,
                onAction = {
                    showItemPicker = true
                }
            )

            selectedItem?.let{
                ListItem(
                    headlineContent = { Text(selectedItem.toString()) },
                    leadingContent = {
                        Icon(
                            painterResource(id = icon ?: R.drawable.ic_bullet_point),
                            contentDescription = "Localized description",
                            modifier = Modifier.size(8.dp)
                        )
                    }
                )
            }

            if(selectedItem != null){
                AppSpacer()
            }
        }
    }


     */

    AppCard(onClick = { showItemPicker = true }, isError = isError){
        SectionRow(
            onClick = { showItemPicker = true }
        ){
            AppText(
                text = title,
//                icon = icon,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            FilledTonalIconButton(
                modifier = Modifier.size(24.dp),
                onClick = { showItemPicker = true }
            ) {
                AppIcon(
                    modifier = Modifier.size(16.dp),
//                    icon = R.drawable.ic_add,
                )
            }
        }

        selectedItem?.let{
//            ListItem(
//                headlineContent = { Text(selectedItem.toString()) },
//                leadingContent = {
//                    Icon(
//                        painterResource(id = icon ?: R.drawable.ic_bullet_point),
//                        contentDescription = "Localized description",
//                        modifier = Modifier.size(8.dp)
//                    )
//                }
//            )

//            AppFilledCard {
//                SectionItem(text = it.toString(), value = "")
//            }

            Column(modifier = Modifier.clip(MaterialTheme.shapes.small)){
                AppInsetListItem(text = it.toString())
            }

        }


    }


    AppItemPickerDialog(
        openDialog  = showItemPicker,
        items = items,
        onItemSelected = { item ->
            onSelectionChanged(item)
            showItemPicker = false
        },
        itemText = { item ->
            item.toString()
        },

        secondaryText = { item ->
            item.secondaryText
        },

        onDialogDismissed = {
            showItemPicker = false
        }
    )

}
