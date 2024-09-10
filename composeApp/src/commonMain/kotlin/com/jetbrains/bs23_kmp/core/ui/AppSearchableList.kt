package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <T>AppSearchableList(
    text: (T) -> String,
    secondaryText: (T) -> String = {""},
    supportingText: (T) -> String = {""},
    items: List<T>,
    onItemSelected: (T) -> Unit,
    searchComparator: (T, String) -> Boolean,
    onDialogDismissed: () -> Unit

) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = items.filter { searchComparator(it, searchQuery) }

    if(items.isNullOrEmpty()){
        AppNoItem(
            modifier = Modifier.fillMaxHeight(),
            onDialogDismissed = onDialogDismissed
        )
    }
    else {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
//                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.9f)
//                    .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f)
                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())
            ) {

                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search ") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
//                        AppIcon(
//                            icon = R.drawable.ic_search
//                        )
                    }
                )
                LazyColumn(
//                    modifier = Modifier.height(480.dp)
//                    modifier = Modifier.requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.8f)
                ) {
                    items(filteredItems.size) { selectedItem ->
                        ListItem(
                            modifier = Modifier.clickable {
                                onItemSelected(filteredItems[selectedItem])
                                onDialogDismissed()
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                            headlineContent = {
                                Text(
                                    text = text(filteredItems[selectedItem]),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize

                                )

                            },
                            overlineContent =
                                if (secondaryText(filteredItems[selectedItem]).isNotEmpty()) {
                                    {
                                        Text(
                                            text = secondaryText(filteredItems[selectedItem]),
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        )
                                    }
                                }
                                else {
                                    null
                                },

                            supportingContent =
                                if (supportingText(filteredItems[selectedItem]).isNotEmpty()) {
                                    {
                                        Text(
                                            text = supportingText(filteredItems[selectedItem]),
                                            color = MaterialTheme.colorScheme.outline,
                                            fontSize = MaterialTheme.typography.labelMedium.fontSize

                                        )
                                    }
                                }
                                else{
                                    null
                                },

                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun SearchableListPreview(){
    AppSearchableList(
        text = { it.toString() },
        items = listOf(1,2,3,4,5,6,7,8,9,10),
        onItemSelected = {},
        searchComparator = { item, query -> item.toString().contains(query) },
        onDialogDismissed = {}
    )
}