package org.android.bbangzip.presentation.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    navigateToTodo: (NavOptions) -> Unit,
    navigateToOnboarding: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                is LoginContract.LoginSideEffect.NavigateToTodo -> {
                    val navOptions =
                        navOptions {
                            popUpTo(LoginRoute) {
                                inclusive = true
                            }
                        }
                    navigateToTodo(navOptions)
                }

                is LoginContract.LoginSideEffect.NavigateToOnboarding -> navigateToOnboarding()
            }
        }
    }

    LoginScreen(
        state = state,
        onClickKakaoLoginBtn = { viewModel.setEvent(LoginContract.LoginEvent.OnClickKakaoLoginBtn(context)) },
    )
}
