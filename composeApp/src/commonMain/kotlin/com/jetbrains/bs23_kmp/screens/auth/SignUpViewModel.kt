package com.jetbrains.bs23_kmp.screens.auth

import androidx.lifecycle.viewModelScope
import com.jetbrains.bs23_kmp.core.base.viewmodel.MviViewModel
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpViewModel(private val authService: AuthService) :
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
            is SignUpEvent.isEmailVerified -> checkIfEmailIsVerified()
            is SignUpEvent.resetState -> resetState()
        }
    }

    private fun resetState() {
        data = SignUpUIState()
        setState(BaseViewState.Data(data))
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
                authService.sendVerificationEmail()
                data = data.copy(
                    isEmailSent = true,
                )
                setState(BaseViewState.Data(data))
                pollForEmailVerification()

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
//            checkIfEmailIsVerified()
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun checkIfEmailIsVerified() {
        viewModelScope.launch {
            val currentUser = authService.currentUser
            val isVerified = authService.isEmailVerified()
            if (isVerified) {
                data = data.copy(
                    isSignUpSuccess = true,
                    signUpMsg = "Email verified. Sign-up successful! Please Wait for Redirecting to Home Page",
                    isEmailSent = false,
                    email = "",
                    password = ""
                )
                setState(BaseViewState.Data(data))
            } else {
                data = data.copy(
                    isSignUpFailed = true,
                    signUpErrorMsg = "Email not verified. Please check your inbox."
                )
                setState(BaseViewState.Data(data))
            }
//            deleteUserIfUnverified(currentUser)
        }
    }

    private fun pollForEmailVerification() {
        viewModelScope.launch {
            var isVerified = false
            while (!isVerified) {
                delay(5000) // Check every 5 seconds
                isVerified = authService.isEmailVerified()
                if (isVerified) {
                    data = data.copy(
                        isSignUpSuccess = true,
                        signUpMsg = "Email verified. Sign-up successful! Please Wait for Redirecting to Home Page",
                        isEmailSent = false,
                        email = "",
                        password = ""
                    )
                    setState(BaseViewState.Data(data))
                    break
                }
            }
        }
    }


    // Function to delete user if not verified
    private fun deleteUserIfUnverified(user: FirebaseUser?) {
        user?.let {
            if (!it.isEmailVerified) {
                viewModelScope.launch {
                    try {
                        it.delete()
                        data = data.copy(
                            signUpErrorMsg = "User deleted as email verification was not completed."
                        )
                        setState(BaseViewState.Data(data))
                    } catch (e: Exception) {
                        // Handle any failure during deletion
                        data = data.copy(
                            signUpErrorMsg = e.message ?: "Failed to delete unverified user."
                        )
                        setState(BaseViewState.Data(data))
                    }
                }
            }
        }
    }


}