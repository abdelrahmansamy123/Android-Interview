package com.test.interview.presentation.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null
        )
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            passwordVisible = !_uiState.value.passwordVisible
        )
    }

    fun loginValidation() {
        val currentState = _uiState.value
        var emailError: String? = null
        var passwordError: String? = null

        if (currentState.email.isBlank()) {
            emailError = "Please enter your email"
        } else if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(currentState.email)
                .matches()
        ) {
            emailError = "Please enter a valid email"
        }

        if (currentState.password.isBlank()) {
            passwordError = "Please enter your password"
        } else if (currentState.password.length < 6) {
            passwordError = "Password must be at least 6 characters"
        }

        if (emailError != null || passwordError != null) {
            _uiState.value = currentState.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        //  Firebase أو API Login
        _uiState.value = currentState.copy(
            isLoading = true,
        )
    }
}