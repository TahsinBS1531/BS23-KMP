package com.jetbrains.bs23_kmp.screens.auth

import androidx.lifecycle.viewModelScope
import com.jetbrains.bs23_kmp.core.base.viewmodel.MviViewModel
import com.jetbrains.bs23_kmp.core.base.widget.BaseViewState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService
) :MviViewModel<BaseViewState<LoginUiState>, LoginEvent>() {

    var data = LoginUiState()

    init {
        setState(BaseViewState.Data(data))
        viewModelScope.launch {
            authService.currentUser.collect {
                data = data.copy(
                    currentUser = it
                )
            }
        }

    }


    override fun onTriggerEvent(eventType: LoginEvent) {
        when (eventType) {
            is LoginEvent.Login -> onSignInClick(eventType.email, eventType.password)
            is LoginEvent.Logout -> onSignOut()
            is LoginEvent.onEmailChange -> onEmailChange(eventType.newData)
            is LoginEvent.onPasswordChange ->onPasswordChange(eventType.newData)
        }
    }

//
//    private val _emailError = MutableStateFlow(false)
//    val emailError = _emailError.asStateFlow()
//
//    private val _passwordError = MutableStateFlow(false)
//    val passwordError = _passwordError.asStateFlow()
//
//    private val _currentUser = MutableStateFlow<User?>(null)
//    val currentUser = _currentUser.asStateFlow()
//
//    private val _isProcessing = MutableStateFlow(false)
//    val isProcessing = _isProcessing.asStateFlow()
//
//    private val isButtonEnabled: StateFlow<Boolean> = combine(uiState) { states ->
//        val state = states.first()
//        state.email.isNotBlank() && state.password.isNotBlank()
//    }.stateIn(
//        viewModelScope, SharingStarted.WhileSubscribed(5000), false
//    )


    fun onEmailChange(newValue: String) {
        data = data.copy(
            email = newValue
        )
        setState(BaseViewState.Data(data))
        //reset error
    }

    fun onPasswordChange(newValue: String) {
        data = data.copy(
            password = newValue
        )
        setState(BaseViewState.Data(data))

//        if (newValue.isNotBlank()) _passwordError.value = false
    }

    fun onSignInClick(email:String,password:String) {

        if (data.email.isEmpty()) {
            data = data.copy(
                emailError = true
            )
            setState(BaseViewState.Data(data))
            return
        }

        if (data.password.isEmpty()) {
            data = data.copy(
                passwordError = true
            )
            setState(BaseViewState.Data(data))
            return
        }
        viewModelScope.launch {
            data = data.copy(
                isProcessing = true
            )
            setState(BaseViewState.Data(data))
//            authService.currentUser.collect {
//                if (it.isAnonymous) {
//                    authService.createUser(_uiState.value.email, _uiState.value.password)
//                }
//            }
            val res = authService.authenticate(email,password)
            data = data.copy(
                isProcessing = false
            )
            setState(BaseViewState.Data(data))
        }

    }


    fun onSignOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }

}

//
//data class LoginUiState(
//    val email: String = "",
//    val password: String = ""
//)