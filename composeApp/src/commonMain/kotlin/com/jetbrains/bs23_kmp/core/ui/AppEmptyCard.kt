package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppEmptyCard(
    modifier: Modifier = Modifier,
    text: String = "No data found",
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(width = 0.65.dp, color = MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AppIcon(
                modifier = Modifier.size(50.dp),
//                icon = R.drawable.ic_empty,
                tint = MaterialTheme.colorScheme.outlineVariant
            )

            AppText(text = text, color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}

@Composable
fun AppEmptyCardPreview() {
    AppEmptyCard()
}