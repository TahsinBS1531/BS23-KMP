package com.jetbrains.bs23_kmp.screens.auth

sealed class SignUpEvent {
    data class onEmailChange(val newData :String) : SignUpEvent()
    data class onPasswordChange(val newData:String) : SignUpEvent()
    data class onSignUp(val email: String, val password: String) : SignUpEvent()
    object isEmailVerified : SignUpEvent()
    object resetState : SignUpEvent()

}

data class SignUpUIState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isProcessing: Boolean = false,
    val isSignUpSuccess: Boolean = false,
    val isSignUpFailed: Boolean = false,
    val currentUser: User? = null,
    val signUpErrorMsg: String = "",
    val isEmailSent: Boolean = false,
    val signUpMsg: String = "",
)