package com.test.interview.presentation.auth.login

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data object PasswordVisibilityClicked : LoginEvent
    data object LoginClicked : LoginEvent
}