package org.android.bbangzip.presentation.ui.my

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.common.util.extension.openStore
import org.android.bbangzip.presentation.common.util.extension.openUrl

@Composable
fun MyRoute(
    navigateToProfileEdit: () -> Unit,
    navigateToScreenSetting: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: MyViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                MyContract.MySideEffect.NavigateToProfileEdit -> navigateToProfileEdit()
                MyContract.MySideEffect.NavigateToScreenSetting -> navigateToScreenSetting()
                MyContract.MySideEffect.NavigateToLogin -> navigateToLogin()
                MyContract.MySideEffect.OpenAppStoreReview -> openStore(context)
                MyContract.MySideEffect.OpenCustomerCenterWeb -> openUrl(context, "")
                MyContract.MySideEffect.OpenFeedbackForm -> openUrl(context, "")
                MyContract.MySideEffect.OpenTermsOfServiceWeb -> openUrl(context, "")
            }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.setEvent(MyContract.MyEvent.Initialize)
    }

    MyScreen(
        state = state,
        onClickProfileArea = { viewModel.setEvent(MyContract.MyEvent.OnClickProfileArea) },
        onClickScreenSetting = { viewModel.setEvent(MyContract.MyEvent.OnClickScreenSetting) },
        onClickNotification = { viewModel.setEvent(MyContract.MyEvent.OnClickAlarmSetting) },
        onClickCustomerCenter = { viewModel.setEvent(MyContract.MyEvent.OnClickCustomerCenter) },
        onClickTermsOfService = { viewModel.setEvent(MyContract.MyEvent.OnClickTermsOfService) },
        onClickFeedback = { viewModel.setEvent(MyContract.MyEvent.OnClickFeedback) },
        onClickAppReview = { viewModel.setEvent(MyContract.MyEvent.OnClickReviewApp) },
        onClickLogoutBtn = { viewModel.setEvent(MyContract.MyEvent.OnClickLogoutBtn) },
        onConfirmLogoutBtn = { viewModel.setEvent(MyContract.MyEvent.OnConfirmLogout) },
        onCancelLogoutBtn = { viewModel.setEvent(MyContract.MyEvent.OnCancelLogoutBottomSheet) },
        onClickLogoutBottomSheetDismissRequest = { viewModel.setEvent(MyContract.MyEvent.OnClickLogoutBottomSheetDismissRequest) },
        onClickWithdrawalBtn = { viewModel.setEvent(MyContract.MyEvent.OnClickWithdrawalBtn) },
        onConfirmWithdrawalBtn = { viewModel.setEvent(MyContract.MyEvent.OnConfirmWithdrawal) },
        onCancelWithdrawalBtn = { viewModel.setEvent(MyContract.MyEvent.OnCancelWithdrawalBottomSheet) },
        onClickWithdrawalBottomSheetDismissRequest = { viewModel.setEvent(MyContract.MyEvent.OnClickWithdrawalBottomSheetDismissRequest) },
    )
}
