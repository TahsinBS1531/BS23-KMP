package com.jetbrains.bs23_kmp.screens.auth

import androidx.lifecycle.viewModelScope
import com.jetbrains.bs23_kmp.core.base.viewmodel.MviViewModel
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import kotlinx.coroutines.launch

class SignUpViewModel(private val authService: AuthService):
    MviViewModel<BaseViewState<SignUpUIState>, SignUpEvent>() {
        private var data = SignUpUIState()

    init {
        setState(BaseViewState.Data(data))
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            authService.currentUser.collect {
                data = data.copy(currentUser = it)
                setState(BaseViewState.Data(data))
            }
        }
    }

    override fun onTriggerEvent(eventType: SignUpEvent) {
        when (eventType) {
            is SignUpEvent.onSignUp -> createNewUser(eventType.email, eventType.password)
            is SignUpEvent.onEmailChange -> onEmailChange(eventType.newData)
            is SignUpEvent.onPasswordChange -> onPasswordChange(eventType.newData)

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

    fun createNewUser(email: String, password: String) {
        if (!validateInput()) return

        viewModelScope.launch {
            data = data.copy(isProcessing = true)
            setState(BaseViewState.Data(data))

            try {
                authService.createUser(email, password)

            } catch (e: Exception) {
                data = data.copy(
                    isSignUpFailed = true,
                    signUpErrorMsg = e.message ?: "User creation failed"
                )
                setState(BaseViewState.Data(data))
            } finally {
                data = data.copy(isProcessing = false)
                setState(BaseViewState.Data(data))
            }
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