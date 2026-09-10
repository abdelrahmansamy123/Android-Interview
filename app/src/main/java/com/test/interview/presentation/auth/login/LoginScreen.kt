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
import com.test.interview.presentation.auth.login.LoginEvent
import com.test.interview.presentation.auth.login.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToSignup: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateToHome()
        }
    }

    Column {
        OutlinedTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(LoginEvent.EmailChanged(it))
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
                viewModel.onEvent(LoginEvent.PasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            visualTransformation = if (state.passwordVisible) {
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
                viewModel.onEvent(LoginEvent.LoginClicked)
            },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Login")
            }
        }

        TextButton(
            onClick = onNavigateToSignup
        ) {
            Text("Don't have an account? Sign up")
        }
    }
}