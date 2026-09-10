package com.test.interview.presentation.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,

    )