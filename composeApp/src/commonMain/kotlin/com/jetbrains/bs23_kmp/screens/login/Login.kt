package com.jetbrains.bs23_kmp.screens.login

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.screens.components.AppOutlinedTextField
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.ic_apple_logo
import kmp_app_template.composeapp.generated.resources.ic_google_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
//
//    var authReady by remember { mutableStateOf(false) }
//    LaunchedEffect(Unit) {
//        GoogleAuthProvider.create(
//            credentials = GoogleAuthCredentials(
//                serverId = "789043953254-jtgh40e6cn6qcit36mtje2h0vq2ggjdp.apps.googleusercontent.com"
//            )
//        )
//        authReady = true
//    }
//
//    var signedInUserName: String by remember { mutableStateOf("") }


//    val onFirebaseResult: (Result<FirebaseUser?>) -> Unit = { result ->
//        if (result.isSuccess) {
//            val firebaseUser = result.getOrNull()
//            signedInUserName =
//                firebaseUser?.displayName ?: firebaseUser?.email ?: "Null User"
//            println("Success Result: ${firebaseUser?.email}")
//            println("Phone Number: ${firebaseUser?.phoneNumber}")
//            println("Success Result: ${firebaseUser?.displayName ?: firebaseUser?.email ?: "Null User"}")
//        } else {
//            signedInUserName = "Null User"
//            println("Error Result: ${result.exceptionOrNull()?.message}")
//        }


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppOutlinedTextField(
                modifier = Modifier,
                textFieldHeader = "Email Address",
                textValue = email,
                label = "yourname@gmail.com",
                onValueChange = {
                    email = it
                },
            )
            AppOutlinedTextField(
                modifier = Modifier,
                textFieldHeader = "Password",
                textValue = password,
                label = "password",
                onValueChange = {
                    password = it
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("or")
                HorizontalDivider(modifier = Modifier.weight(1f))
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Login with", style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

//                    GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
//                        GoogleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
//                    }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    Icon(
                        painter = painterResource(Res.drawable.ic_apple_logo),
                        contentDescription = "Apple",
                        modifier = Modifier.size(64.dp).border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            RoundedCornerShape(8.dp)
                        ).padding(16.dp).clickable {

                        },
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Icon(
                        painter = painterResource(Res.drawable.ic_google_logo),
                        contentDescription = "Facebook",
                        modifier = Modifier.size(64.dp).border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            RoundedCornerShape(8.dp)
                        ).padding(16.dp).clickable {
                        },
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

    }

}

//@Composable
//fun AuthUiHelperButtonsAndFirebaseAuth(
//    modifier: Modifier = Modifier,
//    onFirebaseResult: (Result<FirebaseUser?>) -> Unit,
//) {
//    Column(modifier = modifier,verticalArrangement = Arrangement.spacedBy(10.dp)) {
//
//        //Google Sign-In Button and authentication with Firebase
//        GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
//            GoogleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
//        }
//
//        //Apple Sign-In Button and authentication with Firebase
//        AppleButtonUiContainer(onResult = onFirebaseResult) {
//            AppleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
//        }
//
//        //Github Sign-In with Custom Button and authentication with Firebase
//        GithubButtonUiContainer(onResult = onFirebaseResult) {
//            Button(onClick = { this.onClick() }) { Text("Github Sign-In (Custom Design)") }
//        }
//
//    }
//}
