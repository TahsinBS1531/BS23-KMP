package com.jetbrains.bs23_kmp.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}