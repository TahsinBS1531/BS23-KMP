package com.jetbrains.kmpapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.theme.AppTheme

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var showForm by remember { mutableStateOf(true) }

    // Define variables for each form field
    var source by remember { mutableStateOf("UD") }
    var paymentType by remember { mutableStateOf("Normal") }
    var trackingNo by remember { mutableStateOf("") }
    var reviewStatus by remember { mutableStateOf("") }
    var dateInterval by remember { mutableStateOf("This Month") }
    var pageSize by remember { mutableStateOf("10") }
    var membershipId by remember { mutableStateOf("") }

    val trackingNoFocusRequester = remember { FocusRequester() }
    val reviewStatusFocusRequester = remember { FocusRequester() }
    val membershipIdFocusRequester = remember { FocusRequester() }
    var showAdvancedSearch by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (showForm) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                // Source Row (UD, AM radio buttons
                Column(horizontalAlignment = Alignment.Start) {
                    Text("Source")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = source == "UD",
                            onClick = { source = "UD" }
                        )
                        Text("UD")
                        Spacer(modifier = Modifier.width(8.dp))
                        RadioButton(
                            selected = source == "AM",
                            onClick = { source = "AM" }
                        )
                        Text("AM")
                    }
                }

                Column(horizontalAlignment = Alignment.Start) {
                    Text("Payment Type")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = paymentType == "Normal",
                            onClick = { paymentType = "Normal" }
                        )
                        Text("Normal")
                        Spacer(modifier = Modifier.width(8.dp))
                        RadioButton(
                            selected = paymentType == "Emergency",
                            onClick = { paymentType = "Emergency" }
                        )
                        Text("Emergency")
                    }
                }
                Column {
                    Text("Tracking No.")
                    OutlinedTextField(
                        value = trackingNo,
                        onValueChange = { trackingNo = it },
                        label = { Text("Tracking No.(Starts With)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(trackingNoFocusRequester),
                        leadingIcon = {
                            Text(
                                text = "UD",
                                modifier = Modifier
                            )
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                AppDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    dropdownFieldTitle = "Review Status",
                    listOf("Scrutinizing", "Approved", "Pending"),
                    reviewStatus
                ) {
                    reviewStatus = it
                }
                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(membershipIdFocusRequester),
                    textValue = membershipId,
                    label = "Membership No.",
                    onValueChange = {
                        membershipId = it
                    })
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        AppDropdownMenu(
                            modifier = Modifier,
                            dropdownFieldTitle = "Date Interval",
                            listOf("This Month", "Last Month", "Custom"),
                            dateInterval
                        ) {
                            dateInterval = it
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        AppDropdownMenu(
                            modifier = Modifier,
                            dropdownFieldTitle = "Page Size",
                            listOf("10", "20", "50"),
                            pageSize
                        ) {
                            pageSize = it
                        }
                    }
                }
                if (showAdvancedSearch) {
                    AdvancedSearchForm()
                }
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                        TextButton(onClick = { showAdvancedSearch = !showAdvancedSearch }) {
                            Text(
                                "Advanced Search",
                                style = MaterialTheme.typography.titleMedium,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(onClick = {
                            trackingNo = ""
                            membershipId = ""
                            dateInterval = ""
                            pageSize = ""
                            source = ""
                            paymentType = ""
                        }) {
                            Text("Clear")
                        }
                        Button(onClick = {
                            showForm = false
                        }) {
                            Text("Search")
                        }
                    }
                }
            }
        } else {
            Column {
                Text("Search Results", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                // Sample results placeholder
                Text("Search results go here...")

                // Modify search button
                IconButton(onClick = { showForm = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Modify Search")
                }
            }
        }
    }
}

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    textFieldHeader: String = "",
    textValue: String = "",
    label: String = "Enter Here",
    onValueChange: (String) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    Column(horizontalAlignment = Alignment.Start) {
        if (textFieldHeader.isNotEmpty()) {
            Text(textFieldHeader)
        }
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                onValueChange(it)
            },
            label = { Text(label) },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
//            focusedContainerColor = MaterialTheme.colorScheme.surface,
//            unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    AppTheme {
        AppOutlinedTextField(modifier = Modifier.padding(16.dp))
    }
}


@Composable
fun AppDropdownMenu(
    modifier: Modifier = Modifier,
    dropdownFieldTitle: String = "",
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        if (dropdownFieldTitle.isNotEmpty()) {
            Text(dropdownFieldTitle)
        }
        Box {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                label = { Text("Select") },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                },
                readOnly = true,
                shape = RoundedCornerShape(8.dp),
                modifier = modifier.fillMaxWidth()

            )

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }, text = {
                        Text(option)
                    })
                }
            }
        }
    }
}

@Preview()
@Composable
fun PreviewForms() {
    AppTheme {
        SearchScreen()
    }
}

@Composable
fun AdvancedSearchForm(modifier: Modifier = Modifier) {
    var source by remember { mutableStateOf("") }
    var importLcNo by remember { mutableStateOf("") }
    var exportLcNo by remember { mutableStateOf("") }
    var factoryType by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var usedQty by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("Select") }

    Column(
        modifier = modifier
            .fillMaxWidth()
//            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Advanced Search", style = MaterialTheme.typography.titleMedium)
            Column {
                Text("Flagged")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = source == "UD",
                        onClick = { source = "UD" }
                    )
                    Text("Flagged")
                }
            }
            AppOutlinedTextField(
                modifier = Modifier,
                textFieldHeader = "Import LC No",
                textValue = importLcNo,
                label = "Import LC No",
                onValueChange = { importLcNo = it }
            )
            AppOutlinedTextField(
                modifier = Modifier,
                textFieldHeader = "Export LC No",
                textValue = exportLcNo,
                label = "Export LC No",
                onValueChange = { exportLcNo = it }
            )
            AppDropdownMenu(
                modifier = Modifier,
                dropdownFieldTitle = "Factory Type",
                options = listOf("Factory Type 1", "Factory Type 2", "Factory Type 3"),
                selectedOption = factoryType,
                onOptionSelected = { factoryType = it }
            )
            AppDropdownMenu(
                modifier = Modifier,
                dropdownFieldTitle = "Country",
                options = listOf("Country 1", "Country 2", "Country 3"),
                selectedOption = country,
                onOptionSelected = { country = it }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    AppOutlinedTextField(
                        modifier = Modifier,
                        textFieldHeader = "Used Qty",
                        textValue = usedQty,
                        label = "Used Qty",
                        onValueChange = { usedQty = it }
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    AppOutlinedTextField(
                        modifier = Modifier,
                        textFieldHeader = "Qty",
                        textValue = qty,
                        label = "Qty",
                        onValueChange = { qty = it }
                    )
                }
            }
            AppDropdownMenu(
                modifier = Modifier,
                dropdownFieldTitle = "Seperator",
                options = listOf("Seperator 1", "Seperator 2", "Seperator 3"),
                selectedOption = operator,
                onOptionSelected = { operator = it }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvancedSearchFormPreview() {
    AppTheme {
        AdvancedSearchForm(modifier = Modifier.padding(16.dp))
    }
}

