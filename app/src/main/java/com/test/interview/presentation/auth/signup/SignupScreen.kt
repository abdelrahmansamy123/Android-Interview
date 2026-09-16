package com.test.interview.presentation.auth.signup


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateToHome()
        }
    }

    SignupContent(
        uiState = state,

        onNameChange = {
            viewModel.onEvent(SignupEvent.NameChanged(it))
        },

        onEmailChange = {
            viewModel.onEvent(SignupEvent.EmailChanged(it))
        },

        onPasswordChange = {
            viewModel.onEvent(SignupEvent.PasswordChanged(it))
        },

        onConfirmPasswordChange = {
            viewModel.onEvent(
                SignupEvent.ConfirmPasswordChanged(it)
            )
        },

        onPasswordVisibilityClick = {
            viewModel.onEvent(
                SignupEvent.PasswordVisibilityClicked
            )
        },

        onConfirmPasswordVisibilityClick = {
            viewModel.onEvent(
                SignupEvent.ConfirmPasswordVisibilityClicked
            )
        },

        onSignupClick = {
            viewModel.onEvent(SignupEvent.SignupClicked)
        },

        onLoginClick = onNavigateToLogin
    )
}

