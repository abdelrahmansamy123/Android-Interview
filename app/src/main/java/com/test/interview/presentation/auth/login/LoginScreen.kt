import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.interview.presentation.auth.login.LoginContent
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

    LoginContent(
        uiState = state,

        onEmailChange = { email ->
            viewModel.onEvent(
                LoginEvent.EmailChanged(email)
            )
        },

        onPasswordChange = { password ->
            viewModel.onEvent(
                LoginEvent.PasswordChanged(password)
            )
        },

        onPasswordVisibilityClick = {
            viewModel.onEvent(
                LoginEvent.PasswordVisibilityClicked
            )
        },

        onLoginClick = {
            viewModel.onEvent(
                LoginEvent.LoginClicked
            )
        },

        onSignupClick = onNavigateToSignup
    )

}