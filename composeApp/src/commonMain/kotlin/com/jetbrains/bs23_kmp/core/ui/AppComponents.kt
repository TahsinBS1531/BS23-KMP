package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun FormWithTabRow(){
    var selectedTabIndex by remember { mutableStateOf(0) }

    TabRow(selectedTabIndex = selectedTabIndex) {
        Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
            Text(text = "Tab 1")
        }
        Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
            Text(text = "Tab 2")
        }
        Tab(selected = selectedTabIndex == 2, onClick = { selectedTabIndex = 2 }) {
            Text(text = "Tab 3")
        }

    }
}