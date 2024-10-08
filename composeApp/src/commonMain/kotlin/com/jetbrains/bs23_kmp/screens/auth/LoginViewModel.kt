package com.jetbrains.bs23_kmp.screens.auth

import androidx.lifecycle.viewModelScope
import com.jetbrains.bs23_kmp.core.base.viewmodel.MviViewModel
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService
) : MviViewModel<BaseViewState<LoginUiState>, LoginEvent>() {

    private var data = LoginUiState()

    init {
        setState(BaseViewState.Data(data))
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            authService.currentUser.collect {
                //println("current user : $it")
                data = data.copy(currentUser = it)
                setState(BaseViewState.Data(data))
            }
        }
    }

    override fun onTriggerEvent(eventType: LoginEvent) {
        when (eventType) {
            is LoginEvent.Login -> onSignInClick(eventType.email, eventType.password)
            is LoginEvent.Logout -> onSignOut()
            is LoginEvent.onEmailChange -> onEmailChange(eventType.newData)
            is LoginEvent.onPasswordChange -> onPasswordChange(eventType.newData)
            is LoginEvent.onSignUp -> createNewUser(eventType.email, eventType.password)
            else -> {

            }
        }
    }

    private fun onEmailChange(newValue: String) {
        data = data.copy(email = newValue, emailError = false)
        setState(BaseViewState.Data(data))
    }

    private fun onPasswordChange(newValue: String) {
        data = data.copy(password = newValue, passwordError = false)
        setState(BaseViewState.Data(data))
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (data.email.isEmpty() || !isValidEmail(data.email)) {
            data = data.copy(emailError = true)
            isValid = false
        }

        if (data.password.isEmpty() || !isValidPassword(data.password)) {
            data = data.copy(passwordError = true)
            isValid = false
        }

        setState(BaseViewState.Data(data))
        return isValid
    }

    fun onSignInClick(email: String, password: String) {
        if (!validateInput()) return

        viewModelScope.launch {
            data = data.copy(isProcessing = true)
            setState(BaseViewState.Data(data))

            try {
                authService.authenticate(email, password)
                if (authService.isAuthenticated) {
                    data = data.copy(
                        isLoginSuccess = true,
                        email = "",
                        password = ""
                    )
                    setState(BaseViewState.Data(data))
                } else {
                    data = data.copy(isLoginFailed = true)
                }
            } catch (e: Exception) {
                data = data.copy(
                    isLoginFailed = true,
                    loginErrorMessage = e.message ?: "Authentication failed"
                )
            } finally {
                data = data.copy(isProcessing = false)
                setState(BaseViewState.Data(data))
            }
        }
    }

    fun createNewUser(email: String, password: String) {

        if (!validateInput()) return

        viewModelScope.launch {
            try {
                authService.createUser(email, password)
            } catch (e: Exception) {
                // Handle user creation errors
                data = data.copy(loginErrorMessage = e.message ?: "User creation failed")
                setState(BaseViewState.Data(data))
            }
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            authService.signOut()
            data = data.copy(currentUser = null)
            setState(BaseViewState.Data(data))
        }
    }


    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

}
