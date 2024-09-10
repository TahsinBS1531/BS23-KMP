package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jetbrains.bs23_kmp.core.util.ButtonContent


@Composable
fun AppSuccessDialog(
    title: String = "Title",
    text: String = "Operation Successful",
    icon: Int? = null,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text( title) },
        text = { Text(text) },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                ButtonContent(text = "Ok")
            }
        },
        icon = {
//            AppIcon(icon = icon ?: R.drawable.ic_done)
        },
    )
}
