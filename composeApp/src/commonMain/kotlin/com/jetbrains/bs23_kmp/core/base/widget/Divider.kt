package com.jetbrains.bs23_kmp.core.base.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun JRDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp),
    )
}

//@Preview("default", showBackground = true)
//@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//private fun DividerPreview() {
//    AppTheme {
//        Box(Modifier.size(height = 10.dp, width = 100.dp)) {
//            JRDivider(Modifier.align(Alignment.Center))
//        }
//    }
//}