package com.jetbrains.bs23_kmp.core.ui
//
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.interaction.collectIsPressedAsState
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.requiredSize
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.selection.selectable
//import androidx.compose.material.ripple.rememberRipple
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.CornerRadius
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.PathEffect
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.semantics.Role
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import org.jetbrains.compose.ui.tooling.preview.Preview
//
//@Composable
//fun SquareRadioButton(
//    selected: Boolean,
//    onClick: (() -> Unit)?,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    colors: RadioButtonColors = RadioButtonDefaults.colors(),
//    cornerRadius: Dp = 0.dp
//) {
//    val dotRadius by animateDpAsState(
//        targetValue = if (selected) SquareRadioButtonDotSize / 2 else 0.dp,
//        animationSpec = tween(durationMillis = SquareRadioAnimationDuration)
//    )
//
//    val radioColor by rememberUpdatedState(
//        if (enabled) {
//            if (selected) colors.selectedColor else colors.unselectedColor
//        } else {
//            colors.disabledUnselectedColor
//        }
//    )
//
//    val selectableModifier =
//        if (onClick != null) {
//            Modifier.selectable(
//                selected = selected,
//                onClick = onClick,
//                enabled = enabled,
//                role = Role.RadioButton,
//                interactionSource = interactionSource,
//                indication = rememberRipple(
//                    bounded = false,
//                    radius = SquareRadioButtonRippleRadius
//                )
//            )
//        } else {
//            Modifier
//        }
//
//    Canvas(
//        modifier
//            .then(selectableModifier)
//            .wrapContentSize(Alignment.Center)
//            .padding(SquareRadioButtonPadding)
//            .requiredSize(SquareRadioButtonSize)
//    ) {
//        drawRadio(radioColor, dotRadius, cornerRadius)
//    }
//}
//
//private fun DrawScope.drawRadio(color: Color, dotRadius: Dp, cornerRadius: Dp) {
//    val strokeWidth = SquareRadioStrokeWidth.toPx()
//    drawRect(
//        color,
//        topLeft = Offset(0f, 0f),
//        size = Size(SquareRadioRadius.toPx() * 2, SquareRadioRadius.toPx() * 2),
//        style = Stroke(
//            width = 2.dp.toPx(),
//            pathEffect = PathEffect.cornerPathEffect(cornerRadius.toPx())
//        )
//    )
//    if (dotRadius > 0.dp) {
//        drawRoundRect(
//            color,
//            topLeft = Offset(SquareRadioStrokeWidth.toPx(), SquareRadioStrokeWidth.toPx()),
//            size = Size(
//                (SquareRadioRadius.toPx() - strokeWidth) * 2,
//                (SquareRadioRadius.toPx() - strokeWidth) * 2
//            ),
//            cornerRadius = CornerRadius(
//                x = cornerRadius.toPx() * 0.5f,
//                y = cornerRadius.toPx() * 0.5f
//            )
//        )
//    }
//}
//
//object SquareRadioButtonDefaults {
//    @Composable
//    fun colors(
//        selectedColor: Color = RadioButtonDefaults.colors().selectedColor,
//        unselectedColor: Color = RadioButtonDefaults.colors().unselectedColor,
//        disabledSelectedColor: Color = RadioButtonDefaults.colors().disabledSelectedColor,
//        disabledUnselectedColor: Color = RadioButtonDefaults.colors().disabledUnselectedColor
//    ): RadioButtonColors {
//        return RadioButtonColors(
//            selectedColor = selectedColor,
//            unselectedColor = unselectedColor,
//            disabledSelectedColor = disabledSelectedColor,
//            disabledUnselectedColor = disabledUnselectedColor
//        )
//    }
//}
//
//private const val SquareRadioAnimationDuration = 100
//
//private val SquareRadioButtonRippleRadius = 24.dp
//private val SquareRadioButtonPadding = 2.dp
//private val SquareRadioButtonSize = 20.dp
//private val SquareRadioRadius = SquareRadioButtonSize / 2
//private val SquareRadioButtonDotSize = 12.dp
//private val SquareRadioStrokeWidth = 4.dp
//
//
//@Preview
//@Composable
//fun SquareRadioButtonPreview() {
//    var selected by remember { mutableStateOf(false) }
//    Surface {
//        SquareRadioButton(selected = selected, onClick = { selected = !selected })
//    }
//}
