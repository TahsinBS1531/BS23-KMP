package com.jetbrains.bs23_kmp.screens.home

import androidx.compose.runtime.Composable

@Composable
actual fun MapComponent(email:String) {
}

actual class LocationService actual constructor() {
    actual suspend fun getCurrentLocation(): Location {
        TODO("Not yet implemented")
    }
}