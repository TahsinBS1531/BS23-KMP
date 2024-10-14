package com.jetbrains.bs23_kmp.screens.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.jetbrains.bs23_kmp.dashboard.presentation.shimmer
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin


data class PieChartItem(
    val value: Float,
    val color: Color,
    val label: String
)

@Composable
fun AnimatedPieChart(
    items: List<PieChartItem>,
    modifier: Modifier = Modifier,
    animationDuration: Int = 1000
) {
    val total = items.sumOf { it.value.toDouble() }.toFloat()
    var animatedAngle by remember { mutableStateOf(0f) }

    var selectedSlice by remember { mutableStateOf(-1) }


    LaunchedEffect(Unit) {
        animatedAngle = 360f
    }

    val animatedSweepAngles = items.mapIndexed { index, item ->
        val targetSweepAngle = 360 * (item.value / total)
        animateFloatAsState(
            targetValue = if (animatedAngle >= targetSweepAngle) targetSweepAngle else 0f,
            animationSpec = tween(durationMillis = animationDuration, delayMillis = index * 200)
        )
    }

    Canvas(modifier = modifier) {
        var startAngle = 0f

        items.forEachIndexed { index, item ->
            val animatedSweepAngle = animatedSweepAngles[index].value

            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(item.color, item.color.copy(alpha = 0.5f)),
                    center = center,
                ),
                startAngle = startAngle,
                sweepAngle = animatedSweepAngle,
                useCenter = true
            )
            startAngle += animatedSweepAngle
        }
    }
}

@Composable
fun PieChartWithLines(
    title: String,
    items: List<PieChartItem>,
    modifier: Modifier = Modifier,
    animationDuration: Int = 1000
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val canvasSize =
            min(maxWidth, maxHeight)  // Take the smaller dimension to calculate radii dynamically
        val radius = canvasSize * 0.4f  // Outer radius (40% of the canvas size)
        val density = LocalDensity.current
        val radiusPx = with(density) { radius.toPx() }
        val innerRadius = canvasSize * 0.2f  // Inner radius (20% of the canvas size)
        val innerRadiusPx = with(density) { innerRadius.toPx() }
        val labelRadiusOffset = canvasSize * 0.05f  // Offset for labels
        val labelRadiusOffsetPx = with(density) { labelRadiusOffset.toPx() }

        var startAngle = 0f
        val total = items.sumOf { it.value.toDouble() }.toFloat()

        // Title at the top
        Text(
            title,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            style = MaterialTheme.typography.titleMedium
        )

        // Pie chart
        AnimatedPieChart(
            items = items,
            modifier = Modifier
                .fillMaxSize(0.5f)
                .align(Alignment.Center),
            animationDuration = animationDuration
        )

        // Labels with small cards for each item, placed outside the pie chart
        items.forEachIndexed { index, item ->
            val sweepAngle = 360 * (item.value / total)
            val midAngle = startAngle + sweepAngle / 2f
            val angleRad = toRadians(midAngle.toDouble()) // Use `kotlin.math.toRadians()`

            startAngle += sweepAngle

            // Calculate position for the label card based on the mid-angle, move outside the chart
            val labelX = (cos(angleRad) * radiusPx).toFloat()
            val labelY = (sin(angleRad) * radiusPx).toFloat()

            // Calculate the start point (middle of the pie segment)
            val startX = (cos(angleRad) * innerRadiusPx).toFloat()
            val startY = (sin(angleRad) * innerRadiusPx).toFloat()

            Box(
                modifier = Modifier
                    .offset { IntOffset(labelX.roundToInt(), labelY.roundToInt()) }
                    .align(Alignment.Center)
            ) {
                // Label card
                Card(
                    modifier = Modifier
                        .padding(4.dp),
//                    backgroundColor = Color.White,
//                    elevation = 4.dp
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(item.color, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "${item.label}: ${item.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Draw pointer lines from the pie segment to the edge of the label
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLine(
                    color = item.color,
                    start = Offset(
                        center.x + startX,
                        center.y + startY
                    ),  // Start from the pie segment center
                    end = Offset(
                        center.x + labelX - (cos(angleRad) * labelRadiusOffsetPx).toFloat(),
                        center.y + labelY - (sin(angleRad) * labelRadiusOffsetPx).toFloat()
                    ), // End just before hitting the card
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }
}

@Composable
fun PieChartWithoutLines(
    title: String,
    items: List<PieChartItem>,
    modifier: Modifier = Modifier,
    animationDuration: Int = 1000,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = if (isLoading) "" else title,
            modifier = Modifier
                .padding(8.dp)
                .then(if (isLoading) Modifier.shimmer() else Modifier),
            style = MaterialTheme.typography.titleMedium
        )

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Legend with shimmer effect for each row
            Column(modifier = Modifier.weight(1f).padding(4.dp)) {
                if (isLoading) {
                    repeat(3) { // Show 3 placeholder rows with shimmer
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .shimmer(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(Color.Gray, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(16.dp)
                                    .background(Color.Gray)
                            )
                        }
                    }
                } else {
                    items.forEachIndexed { index, pieChartItem ->
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(pieChartItem.color, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                pieChartItem.label,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterVertically)
                    .then(if (isLoading) Modifier.shimmer() else Modifier)
            ) {
                if (!isLoading) {
                    AnimatedPieChart(
                        items = items,
                        modifier = Modifier.fillMaxSize(),
                        animationDuration = animationDuration
                    )
                }
            }
        }
    }
}



fun toRadians(deg: Double): Double = deg / 180.0 * PI

@Preview
@Composable
fun AnimatedPieChartPreview() {
    val data = listOf(
        PieChartItem(25f, MaterialTheme.colorScheme.primary, "Red"),
        PieChartItem(35f, MaterialTheme.colorScheme.secondary, "Green"),
        PieChartItem(20f, MaterialTheme.colorScheme.tertiary, "Blue"),
        PieChartItem(20f, MaterialTheme.colorScheme.primaryContainer, "Yellow")
    )
//
//    Surface {
//        PieChartWithAnimatedLabels(items = data, modifier = Modifier.padding(16.dp))
//    }
}

