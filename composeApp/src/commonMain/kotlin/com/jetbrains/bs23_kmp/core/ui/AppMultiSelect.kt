package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
import com.jetbrains.bs23_kmp.core.theme.AppTheme
import com.jetbrains.bs23_kmp.core.ui.component.MultiSelectDialog
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppMultiSelect(
    modifier: Modifier = Modifier,
    title: String,
    items: List<DropdownItem>,
    checkedItems: List<DropdownItem>,
    onMultiSelectChanged: (List<DropdownItem>) -> Unit,
    isError: Boolean = false,
//    @DrawableRes icon: Int? = null,
    onSearchQueryChanged: ((String)->Unit)? = null,
    isRemovable: (DropdownItem) -> Boolean = {true}

) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    AppCard(modifier = modifier, onClick = { openBottomSheet = true }, isError = isError){
        SectionRow(
            onClick = { openBottomSheet = true }
        ){
            AppText(
                text = title,
//                icon = icon,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            FilledTonalIconButton(
                modifier = Modifier.size(24.dp),
                onClick = { openBottomSheet = true }
            ) {
                AppIcon(
                    modifier = Modifier.size(16.dp),
//                    icon = R.drawable.ic_add,
                )
            }
        }

        if(checkedItems.isNotEmpty()) {
            Column(
                Modifier
                    .clip(MaterialTheme.shapes.small)
            ) {
                checkedItems.forEachIndexed { idx, item ->
//            ListItem(
//                headlineContent = {
//                    Text(
//                        text = item.toString(),
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                },
//                leadingContent = {
//                    Icon(
//                        painterResource(id = icon ?: R.drawable.ic_bullet_point),
//                        contentDescription = "Localized description",
//                        modifier = Modifier.size(8.dp)
//                    )
//                }
//            )

                    AppInsetListItem(text = item.toString(), isAlternateColor = idx % 2 != 0)
                }
            }
        }

//        if (checkedItems.isNotEmpty()) {
//            AppSpacer()
//        }

    }


//    MultiSelectBottomSheet(
//        openBottomSheet = openBottomSheet,
//        items = items,
//        checkedItems = checkedItems.toMutableList(),
//        onCheckedChange = { list ->
//            onMultiSelectChanged(list)
//        },
//        onDismiss = {
//            openBottomSheet = false
//        },
//        icon = icon
//    )

    // full screen dialog for multi select
    MultiSelectDialog(
        isShowDialog = openBottomSheet,
        items = items,
        checkedItems = checkedItems.toMutableList(),
        onCheckedChange = { list ->
            onMultiSelectChanged(list)
        },
        onDismiss = {
            openBottomSheet = false
        },
//        icon = icon,
        onSearchQueryChanged = onSearchQueryChanged,
        isRemovable = isRemovable
    )
}

@Preview
@Composable
fun MultiSelectPreview() {
    AppTheme {
        AppMultiSelect(
            title = "Select Market",
            items = listOf(DropdownItem("1", "cat"), DropdownItem("2", "dobg")),
            checkedItems = emptyList(),
            onMultiSelectChanged = {},
            onSearchQueryChanged = {},
        )
    }

}
