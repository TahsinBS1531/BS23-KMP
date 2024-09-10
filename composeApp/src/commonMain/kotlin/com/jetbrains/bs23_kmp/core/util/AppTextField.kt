package com.jetbrains.bs23_kmp.core.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.jetbrains.bs23_kmp.core.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        textStyle = TextStyle(fontSize = 12.sp),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        enabled = isEnabled,
//        colors = OutlinedTextFieldDefaults.colors(
//            disabledTextColor = Color.Black,
//            focusedBorderColor = Color.Black,
//            unfocusedBorderColor = Color.Gray,
//            disabledBorderColor = Color.Gray,
//        ),
        placeholder = {
            Text(text = placeholder, style = TextStyle(fontSize = 12.sp),)
        }
    )
}

@Preview
@Composable
fun AppTextFieldPreview() {
    AppTheme() {
        Surface{
            AppTextField(
                text = "Hello",
                placeholder = "Placeholder",
                onChange = {},
                keyboardType = KeyboardType.Text
            )
        }

    }
}