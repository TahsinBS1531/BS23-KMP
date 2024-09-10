package com.jetbrains.bs23_kmp.core.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

class Movement {
}

@Composable
fun AnimateLeftToRight(
    visible: Boolean = false,
    content: @Composable () -> Unit
){
//    var visible by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally {
            // Slide in from 40 dp from the left.
            with(density) { -80.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }

}
@Composable
fun AnimateFromTop(
    visible: Boolean? = null,
    content: @Composable () -> Unit
){
    val density = LocalDensity.current

    var isAnimationStarted by remember { mutableStateOf(false) }


    AnimatedVisibility(
        visible = visible ?: isAnimationStarted,
        enter = slideInVertically {
            // Slide in from 40 dp from the left.
            with(density) { -80.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }

    if(visible == null){
        LaunchedEffect(visible){
            isAnimationStarted = true
        }
    }

}

@Composable
fun AnimateFadeIn(
    visible: Boolean? = null,
    content: @Composable () -> Unit
) {

    var isAnimationStarted by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visible ?: isAnimationStarted,
        enter =  fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }

    if (visible == null) {
        LaunchedEffect(visible) {
            isAnimationStarted = true
        }
    }
}

@Composable
fun AnimateFromLeft(
    visible: Boolean? = null,
    content: @Composable () -> Unit
){
    val density = LocalDensity.current

    var isAnimationStarted by remember { mutableStateOf(false) }


    AnimatedVisibility(
        visible = visible ?: isAnimationStarted,
        enter = slideInHorizontally {
            // Slide in from 40 dp from the left.
            with(density) { -80.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }

    if(visible == null){
        LaunchedEffect(visible){
            isAnimationStarted = true
        }
    }

}