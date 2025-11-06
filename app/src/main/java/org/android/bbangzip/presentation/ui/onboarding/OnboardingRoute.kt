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
                OnboardingContract.OnboardingSideEffect.DismissNicknameInputBottomSheet -> viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickNicknameBottomSheetDismissRequest)
                OnboardingContract.OnboardingSideEffect.DismissProfileImgBottomSheet -> viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickProfileImgBottomSheetDismissRequest)
            }
        }
    }

    OnboardingScreen(
        state = state,
        onNicknameChange = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnChangeNickname(it)) },
        onClickNicknameTextField = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickNicknameTextField) },
        onClickNicknameInputBottomSheetDismissRequest = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickNicknameBottomSheetDismissRequest) },
        onNicknameInputDoneAction = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnNicknameInputDone) },
        onClickProfileImg = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickProfileImgSettingBtn) },
        onSelectProfileImg = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnSelectProfileImg(it)) },
        onClickProfileImgBottomSheetDismissRequest = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickProfileImgBottomSheetDismissRequest) },
        onClickProfileImgCancelBtn = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickProfileImgCancelBtn) },
        onClickProfileImgCompleteBtn = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickProfileImgCompleteBtn) },
        onClickBackBtn = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickPreviousBtn) },
        onClickSaveBtn = { viewModel.setEvent(OnboardingContract.OnboardingEvent.OnClickSaveBtn) },
    )
}
