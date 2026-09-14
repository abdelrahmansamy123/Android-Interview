package com.test.interview.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.interview.domain.usecase.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
                        errorMessage = null
                    )
                }
            }

            is SignupEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.value,
                        errorMessage = null
                    )
                }
            }

            is SignupEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.value,
                        errorMessage = null
                    )
                }
            }

            is SignupEvent.ConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.value,
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
        val state = _uiState.value

        when {
            state.name.isBlank() -> {
                showError("Name is required")
                return
            }

            state.email.isBlank() -> {
                showError("Email is required")
                return
            }

            state.password.length < 6 -> {
                showError("Password must be at least 6 characters")
                return
            }

            state.password != state.confirmPassword -> {
                showError("Passwords do not match")
                return
            }
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val result = signupUseCase(
                name = state.name,
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
                                ?: "Signup failed"
                        )
                    }
                }
        }
    }

    private fun showError(message: String) {
        _uiState.update {
            it.copy(errorMessage = message)
        }
    }
}