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
import androidx.compose.ui.platform.LocalUriHandler

@Composable
fun MyRoute(
    navigateToProfileEdit: () -> Unit,
    navigateToScreenSetting: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: MyViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                MyContract.MySideEffect.NavigateToProfileEdit -> navigateToProfileEdit()
                MyContract.MySideEffect.NavigateToScreenSetting -> navigateToScreenSetting()
                MyContract.MySideEffect.NavigateToLogin -> navigateToLogin()
                MyContract.MySideEffect.OpenAppStoreReview -> {
                    uriHandler.openUri("https://play.google.com/store/apps/details?id=${context.packageName}")
                }

                MyContract.MySideEffect.OpenCustomerCenterWeb -> {
                    // TODO: Replace with actual URL
                    val url = "https://southern-comet-4a3.notion.site/2ac01508929380518f17feaa3aa64870?source=copy_link"
                    if (url.isNotEmpty()) uriHandler.openUri(url)
                }

                MyContract.MySideEffect.OpenFeedbackForm -> {
                    // TODO: Replace with actual URL
                    val url = "https://southern-comet-4a3.notion.site/2ac01508929380518f17feaa3aa64870?source=copy_link"
                    if (url.isNotEmpty()) uriHandler.openUri(url)
                }

                MyContract.MySideEffect.OpenInstagram -> {
                    // TODO: Replace with actual URL
                    val url = "https://www.instagram.com/bbangzip.official/"
                    if (url.isNotEmpty()) uriHandler.openUri(url)
                }
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
