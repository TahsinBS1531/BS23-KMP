package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AppNoItem(
    modifier: Modifier = Modifier,
    onDialogDismissed: () -> Unit
){
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {

                AppIconButton(
//                    icon = R.drawable.ic_close,
                    tint = MaterialTheme.colorScheme.primary,
                    onClick = onDialogDismissed,
                    modifier = Modifier.size(26.dp).padding(end = 16.dp)
                )
            }



//            AppLottie(animation = R.raw.not_found_4, size = 200.dp)


//            Text(
//                text = stringResource(id = R.string.text_no_data_found),
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    color = MaterialTheme.colorScheme.onSecondaryContainer,
//                    fontWeight = FontWeight.SemiBold
//                ),
//                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
//            )
        }
    }
}

@Preview
@Composable
fun AppNoItemPreview() {
    AppNoItem(onDialogDismissed = {})
}
