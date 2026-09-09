import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.interview.presentation.auth.login.LoginContent
import com.test.interview.presentation.auth.login.LoginViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {
    val uiState by loginViewModel.uiState.collectAsState()

    LoginContent(
        uiState = uiState,
        onEmailChange = loginViewModel::onEmailChange,
        onPasswordChange = loginViewModel::onPasswordChange,
        onPasswordVisibilityClick = loginViewModel::togglePasswordVisibility,
        onLoginClick = loginViewModel::loginValidation
    )
}