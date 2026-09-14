package com.test.interview.presentation.auth.signup

sealed interface SignupEvent {
    data class NameChanged(val value: String) : SignupEvent
    data class EmailChanged(val value: String) : SignupEvent
    data class PasswordChanged(val value: String) : SignupEvent
    data class ConfirmPasswordChanged(val value: String) : SignupEvent

    data object PasswordVisibilityClicked : SignupEvent
    data object ConfirmPasswordVisibilityClicked : SignupEvent
    data object SignupClicked : SignupEvent
}