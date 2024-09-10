package com.jetbrains.bs23_kmp.core.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val MaterialTheme.customColorsPalette: CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current

@Immutable
data class CustomColorsPalette(
    val successGreen: Color = Color.Unspecified,
    val extraColor2: Color = Color.Unspecified,
    val extraColor3: Color = Color.Unspecified
)

val LightExtraColor1 = Color(color = 0xFF4CAF50)
val LightExtraColor2 = Color(color = 0xFF26A69A)
val LightExtraColor3 = Color(color = 0xFFEF5350)

val DarkExtraColor1 = Color.Green
val DarkExtraColor2 = Color(color = 0xFF00695C)
val DarkExtraColor3 = Color(color = 0xFFC62828)

val LightCustomColorsPalette = CustomColorsPalette(
    successGreen = LightExtraColor1,
    extraColor2 = LightExtraColor2,
    extraColor3 = LightExtraColor3
)

val DarkCustomColorsPalette = CustomColorsPalette(
    successGreen = DarkExtraColor1,
    extraColor2 = DarkExtraColor2,
    extraColor3 = DarkExtraColor3
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }