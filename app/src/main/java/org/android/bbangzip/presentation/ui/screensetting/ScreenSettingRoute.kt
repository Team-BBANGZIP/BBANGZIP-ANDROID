package org.android.bbangzip.presentation.ui.screensetting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ScreenSettingRoute(
    navigateToBack: () -> Unit,
    viewModel: ScreenSettingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                ScreenSettingContract.ScreenSettingSideEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

    ScreenSettingScreen(
        state = state,
        onBackIconClick = { viewModel.setEvent(ScreenSettingContract.ScreenSettingEvent.OnBackIconClick) },
        onSundayStartToggle = { viewModel.setEvent(ScreenSettingContract.ScreenSettingEvent.OnSundayStartToggle) },
    )
}
