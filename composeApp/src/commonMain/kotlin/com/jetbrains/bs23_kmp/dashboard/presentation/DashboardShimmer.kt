package com.jetbrains.bs23_kmp.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ShimmeringDashboardCard(modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Shimmer for Text placeholders
            Box(modifier = Modifier.fillMaxWidth().height(24.dp).shimmer()) // For title shimmer
            HorizontalDivider()
            Box(modifier = Modifier.fillMaxWidth().height(20.dp).shimmer()) // For submitted UD shimmer
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.width(100.dp).height(20.dp).shimmer()) // For Normal UD shimmer
                Box(modifier = Modifier.width(100.dp).height(20.dp).shimmer()) // For Emergency UD shimmer
            }
            HorizontalDivider()
            Box(modifier = Modifier.fillMaxWidth().height(20.dp).shimmer()) // For Passed & Dispatched shimmer
        }
    }
}

fun Modifier.shimmer(): Modifier = this
    .background(
        brush = Brush.linearGradient(
            colors = listOf(Color.Gray.copy(alpha = 0.5f), Color.Gray.copy(alpha = 0.1f), Color.Gray.copy(alpha = 0.5f)),
            start = Offset.Zero,
            end = Offset.Infinite
        )
    )
    .clip(RectangleShape)
