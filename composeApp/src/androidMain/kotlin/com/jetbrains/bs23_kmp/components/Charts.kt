package com.jetbrains.bs23_kmp.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class PieChartItem(
    val value: Float, val color: Color, val label: String
)

@Composable
fun AnimatedPieChart(
    items: List<PieChartItem>, modifier: Modifier = Modifier, animationDuration: Int = 1000
) {
    val total = items.sumOf { it.value.toDouble() }.toFloat()
    var animatedAngle by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        animatedAngle = 360f
    }

    val animatedSweepAngles = items.mapIndexed { index, item ->
        val targetSweepAngle = 360 * (item.value / total)
        animateFloatAsState(
            targetValue = if (animatedAngle >= targetSweepAngle) targetSweepAngle else 0f,
            animationSpec = tween(durationMillis = animationDuration, delayMillis = index * 200),
            label = ""
        )
    }


    Canvas(modifier = modifier) {
        var startAngle = 0f

        items.forEachIndexed { index, item ->
            val animatedSweepAngle = animatedSweepAngles[index].value

            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(item.color.copy(alpha = 0.5f), item.color.copy(alpha = 0.3f)),
                    center = center,
                ), startAngle = startAngle, sweepAngle = animatedSweepAngle, useCenter = true
            )
            startAngle += animatedSweepAngle
        }
    }
}

@Composable
fun PieChartWithAnimatedLabels(
    items: List<PieChartItem>,
    modifier: Modifier = Modifier,
    animationDuration: Int = 1000 // Duration for animations
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        AnimatedPieChart(
            items = items, modifier = Modifier.fillMaxSize(), animationDuration = animationDuration
        )
    }
}

@Preview
@Composable
fun AnimatedPieChartPreview() {
    val data = listOf(
        PieChartItem(25f, MaterialTheme.colorScheme.primary, "Red"),
        PieChartItem(35f, MaterialTheme.colorScheme.secondary, "Green"),
        PieChartItem(20f, MaterialTheme.colorScheme.tertiary, "Blue"),
        PieChartItem(20f, MaterialTheme.colorScheme.primaryContainer, "Yellow")
    )

    Surface {
        PieChartWithAnimatedLabels(items = data, modifier = Modifier.padding(16.dp))
    }
}

