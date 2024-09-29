package com.jetbrains.bs23_kmp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.theme.AppTheme
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.ic_apple_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AppOutlinedTextField(
            modifier = Modifier,
            textFieldHeader = "yourname@gmail.com",
            textValue = email,
            onValueChange = {
                email = it
            },
        )
        AppOutlinedTextField(
            modifier = Modifier,
            textFieldHeader = "Password",
            textValue = password,
            label = "enter password",
            onValueChange = {
                password = it
            }
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = {}, shape = RoundedCornerShape(8.dp)) {
            Text("Login")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text("or")
            HorizontalDivider(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login with", style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.border(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface,
                        RoundedCornerShape(8.dp)
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(resource = Res.drawable.ic_apple_logo),
                            contentDescription = "Apple",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(26.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Box(
                    modifier = Modifier.border(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface,
                        RoundedCornerShape(8.dp)
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(Res.drawable.ic_apple_logo),
                            contentDescription = "Apple",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(26.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen()
    }

}
