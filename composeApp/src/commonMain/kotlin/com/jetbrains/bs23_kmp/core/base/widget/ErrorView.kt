package com.jetbrains.bs23_kmp.core.base.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.jetbrains.bs23_kmp.core.domain.error.ExceptionModel


data class ErrorWrapper(
    val title: String,
    val message: String,
    val action: String,
    val actionCallback: () -> Unit
)


//fun Throwable.handleThrowable(): String {
//    return when (this) {
//        is ExceptionModel.Http.Custom -> {
//            this.errorBody ?: ""
//        }
//
//        else -> {
//            this.printStackTrace()
//
//            if(this.message != null){
//                this.message
//            }
//            else throw this
//
//        }
//    }
//}

fun Throwable.handleThrowable(): ErrorWrapper {
//fun Throwable.handleThrowable(): String {d
    return when (this) {
        is ExceptionModel.Http.Custom -> {
            ErrorWrapper(
                title = "${this.code} ${this.message}" ?: "",
                message = this.errorBody ?: "",
                action = "Retry",
                actionCallback = {}
            )
        }

        else -> {
//            this.printStackTrace()

            if(this.message != null){
                ErrorWrapper(
                    title = this.message ?: "",
                    message = this.stackTraceToString(),
                    action = "Retry",
                    actionCallback = {}
                )
            }
            else throw this

        }

    }
}

//@Composable
//fun BaseWarning() {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.warning_full))
//    val animationState by animateLottieCompositionAsState(
//        composition = composition,
//        isPlaying = true,
//        iterations = LottieConstants.IterateForever
//    )
//
//    LottieAnimation(composition = composition, progress = {animationState})
//}



@Composable
fun ShowDetails(errorWrapper: ErrorWrapper?) {
    errorWrapper?.let{
        CollapsableErrorView(
            title = it.title,
            message = it.message,
            action = it.action,
            actionCallback = it.actionCallback
        )
    }
}

@Composable
fun CollapsableErrorView(
    title: String,
    message: String,
    action: String,
    actionCallback: () -> Unit
) {
    ExpandableText(message)
}

@Composable
fun ExpandableText(text: String) {
    var isExpanded by remember { mutableStateOf(false) }
    var lineCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 8,
                onTextLayout = {
                    lineCount = it.lineCount
                },
            )
            if(lineCount >= 8){
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            isExpanded = !isExpanded
                        },
                    text = "See more",
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }




        ErrorDetailsDialog(isExpanded, text){
            isExpanded = false
        }
    }
}

@Composable
fun ErrorDetailsDialog(expanded: Boolean, text: String, onDismiss: () -> Unit) {
    val clipboardManager = LocalClipboardManager.current

    if (expanded) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = {
                Text(text = "Error Details")
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = text)
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString((text)))
                    }
                ) {
                    Text(text = "Copy to clip board")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}


