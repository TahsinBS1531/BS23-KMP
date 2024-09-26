package com.jetbrains.bs23_kmp.screens.auth

sealed class LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent()
    object Logout : LoginEvent()
    data class onEmailChange(val newData :String) : LoginEvent()
    data class onPasswordChange(val newData:String) : LoginEvent()
    data class onSignUp(val email: String, val password: String) : LoginEvent()

}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isProcessing: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isLoginFailed: Boolean = false,
    val currentUser: User? = null,
    val loginErrorMessage: String = ""
)