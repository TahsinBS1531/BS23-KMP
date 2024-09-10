package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jetbrains.bs23_kmp.core.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AppConfirmationDialog(
    title: String = "Title",
    text: String = "Are you confirm ?",
//    @DrawableRes icon: Int? = null,
    iconContentColor: Color = MaterialTheme.colorScheme.secondary,
    openDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            icon = {
//                if (icon != null) {
//                    Icon(
//                        painter = painterResource(resource = icon),
//                        contentDescription = null,
////                        painter = painterResource(resource = icon),
//                        tint = iconContentColor
//                    )
//                } else {
//                    Icon(Icons.Filled.Info, contentDescription = null)
//                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("No")
                }
            },
        )
    }
}

@Preview
@Composable
fun AppConfirmationDialogPreview() {
    AppTheme {
        AppConfirmationDialog(openDialog = true, onConfirm = {} , onDismiss = {})
    }
}