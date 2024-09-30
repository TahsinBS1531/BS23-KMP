package com.jetbrains.bs23_kmp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FormWithTabRow(titles: List<String>, tabContents: List<@Composable () -> Unit>) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .padding(start = 12.dp, end = 12.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            for (i in titles.indices) {
                val isSelected = selectedTabIndex == i

                Tab(
                    selected = selectedTabIndex == i,
                    onClick = { selectedTabIndex = i },
                    selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
//                        .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent),
//                    unselectedContentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    Text(
                        text = titles[i],
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 16.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            tabContents[selectedTabIndex]()
        }
    }
}

@Preview()
@Composable
fun FormWithTabRowPreview() {
    MaterialTheme {
        FormWithTabRow(
            titles = listOf("General", "Advanced", "Media & Data", "Messages"),
            tabContents = listOf({ AppFormCard() },
                { Text("This is an Advanced Tab") },
                { Text("This is a Media & Data Tab") },
                { Text("This is a Messages Tab") })
        )
    }

}

@Composable
fun AppFormCard(modifier: Modifier = Modifier, cardTitle: String = "Card Title") {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(cardTitle, style = MaterialTheme.typography.titleMedium)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppInputField(
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Search") },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Search"
                            )
                        }
                    },
                    label = "Your Name",
                    value = "",
                    onValueChange = {},
                    placeholder = "Search.."
                )
                AppInputField(
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Search") },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Search"
                            )
                        }
                    },
                    label = "Your Name",
                    value = "",
                    onValueChange = {},
                    placeholder = "Search.."
                )
                AppInputField(
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Search") },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Search"
                            )
                        }
                    },
                    label = "Your Name",
                    value = "",
                    onValueChange = {},
                    placeholder = "Search.."
                )
                AppLargeTextField()

                Button(onClick = {}, modifier = Modifier) {
                    Text(text = "Save Changes")
                }


            }
        }
    }
}

@Preview()
@Composable

fun AppFormCardPreview() {
    AppFormCard()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInputField(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit, trailingIcon: @Composable () -> Unit, label: String,
    value: String = "",
    onValueChange: (String) -> Unit,
    placeholder: String = "HEllo"
) {
    TextField(
        modifier = modifier.fillMaxWidth().shadow(elevation = 3.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = { Text(text = label, modifier = Modifier.fillMaxWidth()) },
        placeholder = { Text(text = placeholder) },
        shape = RoundedCornerShape(6.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            cursorColor = MaterialTheme.colorScheme.primary,
        )
    )


}

@Preview
@Composable
fun AppInputFieldPreview() {
    AppInputField(
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Search") },
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Search"
                )
            }
        },
        label = "Your Name",
        value = "",
        onValueChange = {},
        placeholder = "Search.."
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLargeTextField() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(text = "Enter your description") },
        modifier = Modifier.fillMaxWidth().height(160.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            cursorColor = MaterialTheme.colorScheme.primary,
        )
    )

}

@Preview
@Composable
fun AppLargeTextFieldPreview() {
    AppLargeTextField()
}
