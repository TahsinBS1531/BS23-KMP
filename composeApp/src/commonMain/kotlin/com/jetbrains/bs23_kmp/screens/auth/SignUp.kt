package com.jetbrains.bs23_kmp.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.core.base.widget.EmptyView
import com.jetbrains.bs23_kmp.core.base.widget.LoadingView
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun SignUp(
    onLoginSuccess: () -> Unit,
    viewModel: SignUpViewModel,
    navController: NavController,
) {

    val uiState by viewModel.uiState.collectAsState()

    SignUpScreenBody(
        uiState = uiState,
        onEvent = { viewModel.onTriggerEvent(it) },
        navController = navController
    )

}

@Composable
fun SignUpScreenBody(
    modifier: Modifier = Modifier,
    uiState: BaseViewState<*>,
    onEvent: (SignUpEvent) -> Unit,
    navController: NavController
) {
    when (uiState) {
        is BaseViewState.Data -> {
            val state = uiState.value as? SignUpUIState

            if (state != null) {
                SignUpScreenContent(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    uiState = state, onEvent = onEvent, navController = navController
                )
            }
        }

        BaseViewState.Empty -> EmptyView()
        is BaseViewState.Error -> Text("Error while Loading the state")
        BaseViewState.Loading -> LoadingView()
    }

}


@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUIState,
    onEvent: (SignUpEvent) -> Unit,
    navController: NavController
) {

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        LoginTitle(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).padding(top = 46.dp),
            title = "Create Account",
            subtitle = "Sign up to get started!"
        )

        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            LoginTextField(modifier = Modifier.fillMaxWidth().border(
                BorderStroke(
                    1.dp, Brush.linearGradient(
                        listOf(Color(0XFFfa568f), Color(0XFFfda78f))
                    )
                ), shape = RoundedCornerShape(8.dp)
            ), value = uiState.email,
                label = "Email ID",
                onValueChange = {
                    onEvent(SignUpEvent.onEmailChange(it))
                },
                isError = uiState.emailError
            )

            if (uiState.emailError) {
                Text(
                    text = "Please enter a valid email address",
                    color = MaterialTheme.colorScheme.error
                )
            }
            LoginTextField(modifier = Modifier.fillMaxWidth().border(
                BorderStroke(
                    1.dp, Brush.linearGradient(
                        listOf(Color(0XFFfa568f), Color(0XFFfda78f))
                    )
                ), shape = RoundedCornerShape(8.dp)
            ), value = uiState.password,
                label = "Password",
                onValueChange = {
                    onEvent(SignUpEvent.onPasswordChange(it))
                },
                isError = uiState.passwordError,
                visualTransformation = PasswordVisualTransformation()

            )

            if (uiState.passwordError) {
                Text(
                    text = "Please enter a valid password",
                    color = MaterialTheme.colorScheme.error
                )
            }


            if (uiState.isProcessing) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LoginButton(modifier =Modifier.fillMaxWidth(), onButtonClick = {
                    onEvent(SignUpEvent.onSignUp(uiState.email, uiState.password))
                }, buttonText = "SIGN UP")
            }

            if (uiState.signUpErrorMsg.isNotEmpty()) {
                Text(text = uiState.signUpErrorMsg, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(uiState.currentUser != null && !uiState.currentUser.isAnonymous) {
                Snackbar {
                    Text("Sign up successful")
                }
                LaunchedEffect(Unit) {
                    navController.navigate("home")
                }
            }
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("I'm already a member,", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Sign In", style = MaterialTheme.typography.titleMedium)
            }
        }


    }
}