package com.jetbrains.bs23_kmp.core.base
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DropdownText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall
    )
}