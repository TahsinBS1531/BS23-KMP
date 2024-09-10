package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jetbrains.bs23_kmp.core.theme.AppTheme

@Composable
fun <T> AppItemPickerDialog(
    openDialog: Boolean,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    itemText: (T) -> String,
    secondaryText: (T) -> String = { "" },
    supportingText: (T) -> String = { "" },
    onDialogDismissed: () -> Unit,
    searchComparator: (T, String) -> Boolean = { item, query ->
        itemText(item).contains(query, ignoreCase = true)
                || secondaryText(item).contains(query, ignoreCase = true)
    }
) {

    if (openDialog) {
        // Show the dialog
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                onDialogDismissed()
            }) {


            Column(
                modifier = Modifier
//                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.90f)
//                    .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f)
                    .padding(vertical = 16.dp),
            ){
                AppSearchableList(
                    text = itemText,
                    secondaryText = secondaryText,
                    supportingText = supportingText,
                    items = items,
                    onItemSelected = onItemSelected,
                    searchComparator = searchComparator,
                    onDialogDismissed = onDialogDismissed

                )
            }
        }
    }

}
//@SuppressLint("UnrememberedMutableState")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun <T> AppItemPickerDialog(
//    openDialog: Boolean,
//    items: List<T>,
//    onItemSelected: (T) -> Unit,
//    itemText: (T) -> String,
//    secondaryItem: (T) -> String = { "" },
//    onDialogDismissed: () -> Unit,
//    searchComparator: (T, String) -> Boolean = { item, query ->
//        itemText(item).contains(query, ignoreCase = true)
//    }
//) {
//    val selectedItem = remember { mutableStateOf<T?>(null) }
//
//    if (openDialog) {
//        // Show the dialog
//
//        Dialog(
//            properties = DialogProperties(
//                dismissOnBackPress = true,
//                dismissOnClickOutside = true
//            ),
//            onDismissRequest = {
//            onDialogDismissed()
//        }) {
//            Surface(
//                color = MaterialTheme.colorScheme.secondaryContainer,
//                shape = MaterialTheme.shapes.medium
//            ) {
//
//                Column(
//                    horizontalAlignment = Alignment.Start,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    Text(
//                        text = "Select an item",
//                        style = MaterialTheme.typography.titleLarge,
//                        modifier = Modifier.padding(16.dp)
//                    )
//
//                    HairlineDivider()
//
//                    /*
//              query = ,
//                  onQueryChange = ,
//                  onSearch = ,
//                  active = ,
//                  onActiveChange =
//               */
//                    val itemCopy = mutableStateListOf<T>()
//                    itemCopy.addAll(items)
//
//
//                    val query = rememberSaveable { mutableStateOf("") }
//
//                    SearchBar(
//                        query = query.value,
//                        onQueryChange = {
//                            query.value = it
//                            itemCopy.filter { item ->
//                                searchComparator(item, query.value)
//                            }
//
//                        },
//
//                        onSearch = {
//                            itemCopy.filter { item ->
//                                searchComparator(item, query.value)
//                            }
//                        },
//
//                        active = false,
//                        onActiveChange = {
//
//                        }
//                    ) {
//
//
//                    }
//
//                    Column(
//                        horizontalAlignment = Alignment.Start,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//
//                        LazyColumn(
//                            modifier = Modifier.height(450.dp)
//                        ){
//                            items(itemCopy.size){
//                                val item = itemCopy[it]
//                                AppSection(
//                                    modifier = Modifier.clickable(onClick = {
//                                        selectedItem.value = item
//                                        onItemSelected(item)
//                                        onDialogDismissed()
//                                    })
//                                ) {
//                                    Column(
//                                        modifier = Modifier.fillMaxWidth()
//                                    ) {
//                                        Text(
//                                            text = itemText(item),
//                                            style = MaterialTheme.typography.bodyMedium.copy(
//                                                color = MaterialTheme.colorScheme.onSecondaryContainer
//                                            ),
//                                        )
//                                        if (secondaryItem(item).isNotEmpty()) {
//                                            Text(
//                                                text = secondaryItem(item),
//                                                style = MaterialTheme.typography.bodySmall.copy(
//                                                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                                                ),
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
////                        items.forEach { item ->
////
////                            AppSection(
////                                modifier = Modifier.clickable(onClick = {
////                                    selectedItem.value = item
////                                    onItemSelected(item)
////                                    onDialogDismissed()
////                                })
////                            ) {
////                                Column(
////                                    modifier = Modifier.fillMaxWidth()
////                                ) {
//////                                Text(
//////                                    modifier = Modifier
//////                                        .clickable(onClick = {
//////                                            selectedItem.value = item
//////                                            onItemSelected(item)
//////                                            onDialogDismissed()
//////                                        })
//////                                        .padding(start = 32.dp, end = 4.dp, top = 16.dp, bottom = 16.dp)
//////                                        .fillMaxWidth(),
//////
//////                                    text = itemText(item),
//////                                    textAlign = TextAlign.Start,
//////                                    style = MaterialTheme.typography.bodyMedium.copy(
//////                                        color = MaterialTheme.colorScheme.onSecondaryContainer
//////                                    ),
//////                                )
////
////                                    Text(
////                                        text = itemText(item),
////                                        style = MaterialTheme.typography.bodyMedium.copy(
////                                            color = MaterialTheme.colorScheme.onSecondaryContainer
////                                        ),
////                                    )
////                                    if (secondaryItem(item).isNotEmpty()) {
////                                        Text(
////                                            text = secondaryItem(item),
////                                            style = MaterialTheme.typography.bodySmall.copy(
////                                                color = MaterialTheme.colorScheme.onSecondaryContainer
////                                            ),
////                                        )
////                                    }
////
////
////                                }
////                            }
////                        }
//                    }
//                }
//            }
//        }
//    }
//}
//


@Composable
fun HairlineDivider() {
    Divider(
//        color = DividerDefaults.color.copy(alpha = 0.85f),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
        thickness = 0.45.dp,

    )

}


//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Preview(showSystemUi = true)
@Composable
fun ItemPickerDialogPreview() {
    AppTheme() {
        AppItemPickerDialog(
            openDialog = true,
            items = listOf("Item 1", "Item 2", "Item 3"),
            onItemSelected = { item ->
                // Do something with the selected item
            },
            itemText = { item ->
                item
            },
            secondaryText = { item ->
                "Secondary Item"
            },
            supportingText = { item ->
                "Supporting Item"
            },
            onDialogDismissed = {
                // Do something when the dialog is dismissed
            }
        )
    }

}