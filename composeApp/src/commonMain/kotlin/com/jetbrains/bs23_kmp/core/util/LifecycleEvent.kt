package com.jetbrains.bs23_kmp.core.util

//import androidx.activity.OnBackPressedCallback
//import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

//@Composable
//public fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
//    // Safely update the current `onBack` lambda when a new one is provided
//    val currentOnBack by rememberUpdatedState(onBack)
//    // Remember in Composition a back callback that calls the `onBack` lambda
//    val backCallback = remember {
//        object : OnBackPressedCallback(enabled) {
//            override fun handleOnBackPressed() {
//                currentOnBack()
//            }
//        }
//    }
//    // On every successful composition, update the callback with the `enabled` value
//    SideEffect {
//        backCallback.isEnabled = enabled
//    }
//    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
//        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
//    }.onBackPressedDispatcher
//    val lifecycleOwner = LocalLifecycleOwner.current
//    DisposableEffect(lifecycleOwner, backDispatcher) {
//        // Add callback to the backDispatcher
//        backDispatcher.addCallback(lifecycleOwner, backCallback)
//        // When the effect leaves the Composition, remove the callback
//        onDispose {
//            backCallback.remove()
//        }
//    }
//}

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}

// usage
/**
    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            // initiate data reloading
        }
    }
*/