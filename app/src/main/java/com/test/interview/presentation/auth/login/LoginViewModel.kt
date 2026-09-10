package com.test.interview.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.interview.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
                        emailError = null
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.value,
                        passwordError = null
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
        val state = _uiState.value

        if (state.email.isBlank()) {
            _uiState.update {
                it.copy(emailError = "Email is required")
            }
            return
        }

        if (state.password.isBlank()) {
            _uiState.update {
                it.copy(passwordError = "Password is required")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val result = loginUseCase(
                email = state.email,
                password = state.password
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
                                ?: "Login failed"
                        )
                    }
                }
        }
    }
}

//    fun loginValidation() {
//        val currentState = _uiState.value
//        var emailError: String? = null
//        var passwordError: String? = null
//
//        if (currentState.email.isBlank()) {
//            emailError = "Please enter your email"
//        } else if (!android.util.Patterns.EMAIL_ADDRESS
//                .matcher(currentState.email)
//                .matches()
//        ) {
//            emailError = "Please enter a valid email"
//        }
//
//        if (currentState.password.isBlank()) {
//            passwordError = "Please enter your password"
//        } else if (currentState.password.length < 6) {
//            passwordError = "Password must be at least 6 characters"
//        }
//
//        if (emailError != null || passwordError != null) {
//            _uiState.value = currentState.copy(
//                emailError = emailError,
//                passwordError = passwordError
//            )
//            return
//        }
//
//        //  Firebase أو API Login
//        _uiState.value = currentState.copy(
//            isLoading = true,
//        )
//    }
//}