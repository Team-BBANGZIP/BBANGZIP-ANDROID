package org.android.bbangzip.presentation.ui.screensetting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ScreenSettingRoute(
    navigateBack: () -> Unit,
    viewModel: ScreenSettingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                ScreenSettingContract.ScreenSettingSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    ScreenSettingScreen(
        state = state,
        onClickBack = { viewModel.setEvent(ScreenSettingContract.ScreenSettingEvent.OnClickBack) },
        onToggleSundayStart = { viewModel.setEvent(ScreenSettingContract.ScreenSettingEvent.OnToggleSundayStart) },
    )
}
