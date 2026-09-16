package com.test.interview.presentation.auth.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.interview.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.value,
                        emailError = null,
                        errorMessage = null
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.value,
                        passwordError = null,
                        errorMessage = null
                    )
                }
            }

            LoginEvent.PasswordVisibilityClicked -> {
                _uiState.update {
                    it.copy(
                        passwordVisible = !it.passwordVisible
                    )
                }
            }

            LoginEvent.LoginClicked -> {
                login()
            }
        }
    }

    private fun login() {
        val currentState = _uiState.value
        // Prevent multiple clicks during login.
        if (currentState.isLoading) return

        val email = currentState.email.trim()
        val password = currentState.password

        var emailError: String? = null
        var passwordError: String? = null
        // Email validation
        when {
            email.isBlank() -> {
                emailError = "Please enter your email"
            }

            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> {
                emailError = "Please enter a valid email"
            }
        }
        // Password validation
        when {
            password.isBlank() -> {
                passwordError = "Please enter your password"
            }

            password.length < 6 -> {
                passwordError = "Password must be at least 6 characters"
            }
        }
        // Display errors and stop the login process.
        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    email = email,
                    emailError = emailError,
                    passwordError = passwordError,
                    errorMessage = null
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    email = email,
                    isLoading = true,
                    emailError = null,
                    passwordError = null,
                    errorMessage = null,
                    isSuccess = false
                )
            }

            val result = loginUseCase(
                email = email,
                password = password
            )

            result
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message
                                ?: "Email or password is incorrect"
                        )
                    }
                }
        }
    }
}