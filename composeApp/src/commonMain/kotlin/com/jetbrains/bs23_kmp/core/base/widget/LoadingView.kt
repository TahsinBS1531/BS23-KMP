package com.jetbrains.bs23_kmp.core.base.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jetbrains.bs23_kmp.core.base.Delayed
import com.jetbrains.bs23_kmp.core.base.component.ProgressIndicator
import com.jetbrains.bs23_kmp.core.theme.AppTheme


@Composable
fun LoadingView(modifier: Modifier = Modifier, delayMillis: Long = 100L) {
    Delayed(delayMillis = delayMillis) {
        println("Delayed LoadingView : $delayMillis")
        Box(
            contentAlignment = Alignment.Center,
            modifier = when (modifier == Modifier) {
                true -> Modifier.fillMaxSize()
                false -> modifier
            }
        ) {
            ProgressIndicator()
        }
    }
}

//@Composable
//fun DashboardLoadingView(modifier: Modifier = Modifier) {
//   Delayed {
////       Box(
////           contentAlignment = Alignment.Center,
////           modifier = when (modifier == Modifier) {
////               true -> Modifier.fillMaxSize()
////               false -> modifier
////           }
////       ) {
////
////       }
//       BaseAnimationPlaceHolder(
//           lottieResource = R.raw.dashboard_loading,
//           text = "Data Sync In Progress..."
//       )
//
//   }
//
//}

//@Preview(
//    showBackground = true,
//    name = "Light Mode"
//)
//@Preview(
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    name = "Dark Mode"
//)
@Composable
fun LoadingViewPreview() {
    AppTheme {
        LoadingView()
    }
}