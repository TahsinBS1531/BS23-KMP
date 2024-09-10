package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun <T> AppInsetList(
    items: List<T>,
    itemText: (T) -> String,
) {
    Column(
        Modifier
    .clip(MaterialTheme.shapes.small)
    ) {
        if (items.isEmpty()) {
            AppSectionCard {
                AppSectionDividerWithText(text = "No Data")
            }
        }
        else{
            items.forEachIndexed { idx, item ->
                AppInsetListItem(text = itemText(item), isAlternateColor = idx % 2 != 0)
            }
        }

    }
}

