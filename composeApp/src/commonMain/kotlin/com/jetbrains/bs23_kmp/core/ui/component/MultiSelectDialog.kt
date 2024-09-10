package com.jetbrains.bs23_kmp.core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jetbrains.bs23_kmp.core.ui.AppIcon
import com.jetbrains.bs23_kmp.core.ui.AppSpacer
import com.jetbrains.bs23_kmp.core.util.ButtonContent
import com.jetbrains.bs23_kmp.core.util.DropdownItem
import com.jetbrains.bs23_kmp.core.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultiSelectDialog(
    isShowDialog: Boolean,
    items: List<DropdownItem>,
    checkedItems: List<DropdownItem>,
    onCheckedChange: (List<DropdownItem>) -> Unit,
    onDismiss: () -> Unit,
//    @DrawableRes icon: Int? = null,
    onSearchQueryChanged: ((String)->Unit)? = null,
    isRemovable: (DropdownItem) -> Boolean = {true}
) {

    if(isShowDialog){
        val localCheckedItems = remember { checkedItems.toMutableStateList() }
        var searchQuery by remember { mutableStateOf("") }
        val filteredItems = if (searchQuery.isEmpty()) items
        else
            if (onSearchQueryChanged==null) items.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            } else items


        Dialog(
            onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false)
        ) {

            Card( // or Surface
                modifier = Modifier
//                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.94f)
//                    .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f)
                    .padding(vertical = 16.dp),
                colors =  CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {


                    Column {
                        AppSpacer()
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                if (onSearchQueryChanged != null) onSearchQueryChanged(it)
                            },
                            label = { Text("Search ") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            leadingIcon = {
                                AppIcon(
//                                    icon = Res.drawable.ic_search
                                )
                            },
                           // add done ime action
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                        )

                        // modify the lazy column such that it takes upto the "Done" button

                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(items = filteredItems) { dropdownItem ->
                                ListItem(
                                    colors = ListItemDefaults.colors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = localCheckedItems.contains(dropdownItem),
                                            onClick = {
                                                if(isRemovable(dropdownItem)) {
                                                    if (localCheckedItems.any {
                                                            it.id == dropdownItem.id
                                                        }) {
                                                        localCheckedItems.remove(dropdownItem)
                                                    } else {
                                                        localCheckedItems.add(dropdownItem)
                                                    }
                                                }
                                            }
                                        ),
                                    headlineContent = { Text(dropdownItem.toString()) },
//                                    leadingContent = {
//                                        Icon(
//                                            modifier = Modifier.size(8.dp),
//                                            painter = painterResource(
//                                                id = icon ?: R.drawable.ic_bullet_point
//                                            ),
//                                            contentDescription = null,
//                                        )
//
//                                    },
                                    trailingContent = {
                                        Checkbox(
                                            checked = localCheckedItems.contains(dropdownItem),
                                            onCheckedChange = null,
                                            enabled =isRemovable(dropdownItem)
                                        )
                                    },
                                )
                            }
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp, top = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Spacer(modifier = Modifier.size(4.dp))

//                    val keyboardController = LocalSoftwareKeyboardController.current

                            Button(
                                modifier = Modifier
                                    .padding(4.dp),

                                onClick = {
//                                    Log.d("dbg", localCheckedItems.toList().toString())
                                    onCheckedChange(localCheckedItems.toList())

                                    onDismiss()
                                    localCheckedItems.clear()
                                    searchQuery = ""

                                    // hide keyboard
//                                    keyboardController?.hide()


                                }
                            ) {
                                ButtonContent(
                                    imageVector = Icons.Default.Done,
                                    text = "Done"
                                )
                            }
                        }
                    }


            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MultiSelectDialogPreview() {
    AppTheme{
        Surface{
            Column(Modifier.fillMaxWidth()) {


                MultiSelectDialog(
                    isShowDialog = true,
                    items = listOf(DropdownItem("1", "Item 1"), DropdownItem("2", "Item 2")),
                    checkedItems = listOf(DropdownItem("1", "Item 1")),
                    onCheckedChange = {},
                    onDismiss = {}
                )
            }
        }
    }
}

