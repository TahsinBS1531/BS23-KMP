package com.jetbrains.bs23_kmp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppNewForm() {
    var isExpanded by remember { mutableStateOf(false) }
    val items = remember {
        mutableStateListOf(
            Item(
                "UD40002022007-028",
                "Self",
                "Scrutinizing",
                "HAMID TEX FASHION LTD. (4000)",
                "Dhaka",
                "9/18/2024, 11:47:38 AM"
            ),
            Item(
                "UD40002022007-029",
                "Other",
                "Pending",
                "ANOTHER TEX FASHION LTD. (4001)",
                "Chittagong",
                "9/18/2024, 10:30:00 AM"
            )
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isExpanded) {
            Text("Forwarded By Mrs. Natasha Ahmed")
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(Icons.Default.MoreVert, null)
            }
        } else {
            HeaderInfo(items = items)
            PrimaryInput(modifier = Modifier, onExpanded = { isExpanded = !isExpanded })
        }

    }
}

@Preview
@Composable
fun PreviewAppNewForm2() {
    MaterialTheme {
        AppNewForm()

    }
}

data class Item(
    val trackingNumber: String,
    val form: String,
    val status: String,
    val memberName: String,
    val zoneName: String,
    val entryAt: String,
    var isExpanded: Boolean = false // Expansion state for each item
)

@Composable
fun HeaderInfo(items: SnapshotStateList<Item>, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.forEachIndexed { index, item ->
            Card(
                modifier = modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(topStart = if (index == 0) 12.dp else 0.dp, topEnd = if(index==0) 12.dp else 0.dp,
                    bottomStart = if (index == items.size -1) 12.dp else 0.dp,
                    bottomEnd = if (index == items.size -1) 12.dp else 0.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                "Tracking Number",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(item.trackingNumber, style = MaterialTheme.typography.titleMedium)
                        }
                        IconButton(onClick = {
                            items[index] = items[index].copy(isExpanded = !item.isExpanded)
                        }) {
                            if (item.isExpanded) {
                                Icon(Icons.Default.KeyboardArrowUp, null)
                            } else {
                                Icon(Icons.Default.KeyboardArrowDown, null)
                            }
                        }
                    }

                    if (items[index].isExpanded) {
                        HorizontalDivider()
                        Text(
                            "Form",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(item.form, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()
                        Text(
                            "Status",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(item.status, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()
                        Text(
                            "Member Name",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(item.memberName, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()
                        Text(
                            "Zone Name",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(item.zoneName, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()
                        Text(
                            "Entry At",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(item.entryAt, style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()
                        Text(
                            "Action",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Delete, null, modifier = Modifier)
                                Text("De Queue", modifier = Modifier)
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
fun PreviewAppNewForm() {
    MaterialTheme {
        val items = remember {
            mutableStateListOf(
                Item(
                    "UD40002022007-028",
                    "Self",
                    "Scrutinizing",
                    "HAMID TEX FASHION LTD. (4000)",
                    "Dhaka",
                    "9/18/2024, 11:47:38 AM"
                ),
                Item(
                    "UD40002022007-029",
                    "Other",
                    "Pending",
                    "ANOTHER TEX FASHION LTD. (4001)",
                    "Chittagong",
                    "9/18/2024, 10:30:00 AM"
                )
            )
        }
        HeaderInfo(items = items)
    }
}

@Composable
fun PrimaryInput(modifier: Modifier = Modifier, onExpanded: () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "Tracking No.")
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Tracking No.") },
                    shape = RoundedCornerShape(8.dp),
                    trailingIcon = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(end = 12.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Card(
                                modifier = Modifier.size(40.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.align(Alignment.Center)
                                    ) {
                                        Icon(
                                            Icons.Default.Search,
                                            null
                                        )
                                    }
                                }
                            }
                        }
                    },
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            null,
                            modifier = Modifier,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text("De Queue", color = MaterialTheme.colorScheme.error)

                    }
                }

                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.PlayArrow, null, modifier = Modifier)
                        Text("Next Application")

                    }
                }
            }

        }

    }
}

@Preview
@Composable
fun PreviewAppPrimaryInput() {
    MaterialTheme {
//        PrimaryInput(onExpanded = {isExpanded = !isExpanded })
    }
}