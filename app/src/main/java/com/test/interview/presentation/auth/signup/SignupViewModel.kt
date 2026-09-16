package com.test.interview.presentation.auth.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.interview.domain.usecase.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.NameChanged -> {
                _uiState.update {
                    it.copy(
                        name = event.value,
                        nameError = null,
                        errorMessage = null
                    )
                }
            }

            is SignupEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.value,
                        emailError = null,
                        errorMessage = null
                    )
                }
            }

            is SignupEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.value,
                        passwordError = null,
                        confirmPasswordError = null,
                        errorMessage = null
                    )
                }
            }

            is SignupEvent.ConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.value,
                        confirmPasswordError = null,
                        errorMessage = null
                    )
                }
            }

            SignupEvent.PasswordVisibilityClicked -> {
                _uiState.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }

            SignupEvent.ConfirmPasswordVisibilityClicked -> {
                _uiState.update {
                    it.copy(
                        isConfirmPasswordVisible =
                            !it.isConfirmPasswordVisible
                    )
                }
            }

            SignupEvent.SignupClicked -> {
                signup()
            }
        }
    }

    private fun signup() {
        val currentState = _uiState.value

        if (currentState.isLoading) return

        val name = currentState.name.trim()
        val email = currentState.email.trim()
        val password = currentState.password
        val confirmPassword = currentState.confirmPassword

        var nameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        // Name validation
        when {
            name.isBlank() -> {
                nameError = "Please enter your name"
            }

            name.length < 2 -> {
                nameError = "Name must be at least 2 characters"
            }
        }

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

        // Confirm password validation
        when {
            confirmPassword.isBlank() -> {
                confirmPasswordError = "Please confirm your password"
            }

            password != confirmPassword -> {
                confirmPasswordError = "Passwords do not match"
            }
        }

        if (
            nameError != null ||
            emailError != null ||
            passwordError != null ||
            confirmPasswordError != null
        ) {
            _uiState.update {
                it.copy(
                    name = name,
                    email = email,
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError,
                    errorMessage = null
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    name = name,
                    email = email,
                    isLoading = true,
                    isSuccess = false,
                    nameError = null,
                    emailError = null,
                    passwordError = null,
                    confirmPasswordError = null,
                    errorMessage = null
                )
            }

            val result = signupUseCase(
                name = name,
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
                                ?: "Signup failed. Please try again."
                        )
                    }
                }
        }
    }
}