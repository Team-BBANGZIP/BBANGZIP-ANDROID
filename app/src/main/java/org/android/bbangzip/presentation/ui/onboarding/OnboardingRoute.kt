package org.android.bbangzip.presentation.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnboardingRoute(
    navigateToTodo: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                OnboardingContract.OnboardingSideEffect.NavigateToTodo -> navigateToTodo()
                OnboardingContract.OnboardingSideEffect.NavigateToLogin -> navigateToLogin()
                OnboardingContract.OnboardingSideEffect.DismissNicknameInputBottomSheet -> TODO()
                OnboardingContract.OnboardingSideEffect.DismissProfileImgBottomSheet -> TODO()
                is OnboardingContract.OnboardingSideEffect.TriggerNicknameValidation -> TODO()
            }
        }
    }

    OnboardingScreen(
        state = state,
    )
}
