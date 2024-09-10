package com.jetbrains.bs23_kmp.core.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.ui.AppIcon
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MultiSelectBottomSheet(
    openBottomSheet: Boolean,
    items: List<DropdownItem>,
    checkedItems: List<DropdownItem>,
    onCheckedChange: (List<DropdownItem>) -> Unit,
    onDismiss: () -> Unit,
//    @DrawableRes icon: Int? = null,
    onSearchQueryChanged: ((String)->Unit)? = null,
) {

    val localCheckedItems: SnapshotStateList<DropdownItem> = checkedItems.toMutableStateList()
    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = if (searchQuery.isEmpty()) items
    else
        if (onSearchQueryChanged==null) items.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        } else items


    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onCheckedChange(localCheckedItems.toList())
                onDismiss()
                localCheckedItems.clear()
            },
            sheetState = bottomSheetState,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.size(4.dp))

                val keyboardController = LocalSoftwareKeyboardController.current

                Button(
                    modifier = Modifier
                        .padding(4.dp),

                    onClick = {
//                        Log.d("dbg", localCheckedItems.toList().toString())
                        onCheckedChange(localCheckedItems.toList())
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                onDismiss()
                                localCheckedItems.clear()
                                searchQuery = ""

                                // hide keyboard
                                keyboardController?.hide()
                            }
                        }
                    }
                ) {
//                    ButtonContent(
//                        imageVector = Icons.Default.Done,
//                        text = "Done"
//                    )
                }


            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (onSearchQueryChanged != null) {
                        onSearchQueryChanged(it)
                    }
                },
                label = { Text("Search ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                leadingIcon = {
                    AppIcon(
//                        icon = R.drawable.ic_search
                    )
                }
            )
            LazyColumn {
                items(items = filteredItems) { dropdownItem ->
                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = localCheckedItems.contains(dropdownItem),
                                onClick = {
                                    if (localCheckedItems.any {
                                            it.id == dropdownItem.id
                                        }) {
                                        localCheckedItems.remove(dropdownItem)
                                    } else {
                                        localCheckedItems.add(dropdownItem)
                                    }
                                }
                            ),
                        headlineContent = { Text(dropdownItem.toString()) },
                        leadingContent = {
//                            Icon(
//                                modifier = Modifier.size(8.dp),
//                                painter = painterResource(id = icon ?: R.drawable.ic_bullet_point),
//                                contentDescription = null,
//                            )

                        },
                        trailingContent = {
                            Checkbox(
                                checked = localCheckedItems.contains(dropdownItem),
                                onCheckedChange = null
                            )
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

}


@Preview
@Composable
fun MultiSelectBottomSheetPreview() {
    MultiSelectBottomSheet(
        true,
        listOf(DropdownItem("1", "One"), DropdownItem("2", "Two"), DropdownItem("3", "Three")),
        mutableListOf<DropdownItem>(),
        {},
        {},
        onSearchQueryChanged =  {},
    )
}


