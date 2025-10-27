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
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                is OnboardingContract.OnboardingSideEffect.NavigateToTodo -> navigateToTodo()
                is OnboardingContract.OnboardingSideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    OnboardingScreen(
        state = state,
    )
}