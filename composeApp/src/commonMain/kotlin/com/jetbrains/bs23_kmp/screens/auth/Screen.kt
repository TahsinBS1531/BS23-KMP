package com.jetbrains.bs23_kmp.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import com.jetbrains.bs23_kmp.core.base.widget.EmptyView
import com.jetbrains.bs23_kmp.core.base.widget.LoadingView
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun LoginScreen1(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel,
    navController: NavHostController,
) {

    val uiState by viewModel.uiState.collectAsState()

//    val emailError by viewModel.emailError.collectAsState()
//    val passwordError by viewModel.passwordError.collectAsState()
//    val isProcessing by viewModel.isProcessing.collectAsState()
//    val isButtonEnabled by viewModel.isProcessing.collectAsState()
//    val currentUser by viewModel.currentUser.collectAsState()


    LoginScreenBody(
        uiState = uiState,
        onEvent = { viewModel.onTriggerEvent(it) },
        navController = navController
    )

}

@Composable
fun LoginScreenBody(
    modifier: Modifier = Modifier,
    uiState: BaseViewState<*>,
    onEvent: (LoginEvent) -> Unit,
    navController: NavController
) {
    when (uiState) {
        is BaseViewState.Data -> {
            val state = uiState.value as? LoginUiState

            if (state != null) {
                LoginScreenContent(
                    uiState = state, onEvent = onEvent, navController = navController
                )
            }
        }

        BaseViewState.Empty -> EmptyView()
        is BaseViewState.Error -> Text("Error while Loading the state")
        BaseViewState.Loading -> LoadingView()
    }

}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    navController: NavController
) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        val width = this.maxWidth
        val finalModifier = if (width >= 780.dp) modifier.width(400.dp) else modifier.fillMaxWidth()
        Column(
            modifier = finalModifier.padding(16.dp).fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = uiState.email, label = {
                Text("Email")
            }, onValueChange = { onEvent(LoginEvent.onEmailChange(it)) })

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text("Password")
                },
                onValueChange = { onEvent(LoginEvent.onPasswordChange(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isProcessing) {
                CircularProgressIndicator()
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth().height(48.dp), onClick = {
                        onEvent(
                            LoginEvent.Login(
                                uiState.email,
                                uiState.password
                            )
                        )
                    })
                {
                    Text("SIGN IN")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            //This is just for example, Ideally user will go to some other screen after login
            AnimatedVisibility(uiState.currentUser != null && !uiState.currentUser.isAnonymous) {
                navController.navigate("home") {
                    popUpTo("login") {
                        inclusive = true
                    }

                }

//            AnimatedVisibility(isError) {
//                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
//                    Text("Error in email or password!", color = MaterialTheme.colorScheme.error)
//                }
//            }

            }
        }

    }}