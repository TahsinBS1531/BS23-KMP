package com.jetbrains.bs23_kmp.core.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jetbrains.bs23_kmp.core.util.ButtonContent
import com.jetbrains.bs23_kmp.core.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <T> AppListDialog(
    title: String,
//
//    @DrawableRes
//    icon: Int? = null,

    items: List<T>,
    itemText: (T) -> String,
    secondaryText: (T) -> String = { "" },
    supportingText: (T) -> String = { "" },

    searchComparator: (T, String) -> Boolean = { item, query ->
        itemText(item).contains(query, ignoreCase = true)
                || secondaryText(item).contains(query, ignoreCase = true)
    }
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    AppCard(onClick = { openDialog = true }) {
        SectionRow(
            onClick = { openDialog = true }
        ) {
            AppText(
                text = title,
//                icon = icon,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            FilledTonalIconButton(
                modifier = Modifier.size(24.dp),
                onClick = { openDialog = true }
            ) {
//                Icon(
//                    modifier = Modifier.size(16.dp),
//                    contentDescription = null,
//                    painter = painterResource(id = R.drawable.outline_list_24),
////                tint = MaterialTheme.colorScheme.primary,
////                onClick = { openDialog = true }
//                )
//               LabelIcon(label = "Show", icon = painterResource(id = R.drawable.ic_view_list))
            }
            
//            AppButton(
//                text = "Show",
//                onClick = { openDialog = true },
//                icon = R.drawable.ic_view_list
//            )




        }
    }

    if (openDialog) {
        // Show the dialog
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                openDialog = false
            }) {


            Card(
                modifier = Modifier
//                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.90f)
//                    .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f)
                    .padding(vertical = 16.dp),
            ) {
                Column(modifier = Modifier.weight(1f)){
                    AppSearchableList(
                        text = itemText,
                        secondaryText = secondaryText,
                        supportingText = supportingText,
                        items = items,
                        onItemSelected = {},
                        searchComparator = searchComparator,
                        onDialogDismissed = {
                            openDialog = false
                        }
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                    ){
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp, top = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(4.dp),

                                onClick = {
                                    openDialog = false
                                }
                            ) {
                                ButtonContent(
                                    imageVector = Icons.Default.Close,
                                    text = "Close"
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun AppListDialogPreview() {
    AppTheme {
        AppListDialog(
            title = "Select an item",
//            icon = R.drawable.ic_bullet_point,
            items = listOf("Item 1", "Item 2", "Item 3"),
            itemText = { it }
        )
    }
}
