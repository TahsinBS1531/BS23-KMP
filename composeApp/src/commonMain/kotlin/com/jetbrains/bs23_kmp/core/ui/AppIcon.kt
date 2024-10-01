package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class AppIcon {
}

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    drawableResource: DrawableResource,
    tint: Color? = null,
){
    Icon(
        modifier = modifier,
        painter = painterResource(drawableResource),
        tint = tint ?: LocalContentColor.current,
        contentDescription = null,
    )

}