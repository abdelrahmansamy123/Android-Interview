package com.test.interview.presentation.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
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

    Column {
        OutlinedTextField(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(SignupEvent.NameChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Name")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(SignupEvent.EmailChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(SignupEvent.PasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = {
                viewModel.onEvent(
                    SignupEvent.ConfirmPasswordChanged(it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Confirm Password")
            },
            visualTransformation = if (state.isConfirmPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )

        state.errorMessage?.let {
            Text(
                text = it,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.onEvent(SignupEvent.SignupClicked)
            },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Create Account")
            }
        }

        TextButton(
            onClick = onNavigateToLogin
        ) {
            Text("Already have an account? Login")
        }
    }
}