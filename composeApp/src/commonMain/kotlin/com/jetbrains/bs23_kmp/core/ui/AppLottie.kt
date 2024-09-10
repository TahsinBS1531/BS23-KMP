package com.bs23.msfa.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun AppLottie(
    modifier: Modifier = Modifier,
//    @RawRes animation: Int,
    size: Dp? = null

) {
    Column{
        Row(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Box(modifier = modifier.size(size ?: 200.dp)) {
//                Asset(animation)
            }
        }

    }


}

//@Composable
//fun Asset(@RawRes animation: Int){
//    val composition by rememberLottieComposition(
//        LottieCompositionSpec.RawRes(
//            animation
//        )
//    )
//    val animationState by animateLottieCompositionAsState(
//        composition = composition,
//        isPlaying = true,
//        iterations = LottieConstants.IterateForever
//    )
//
//    LottieAnimation(composition = composition, progress = { animationState })
//}


