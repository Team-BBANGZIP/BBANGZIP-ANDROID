package org.android.bbangzip.presentation.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.ui.shared.SharedContract
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.presentation.ui.timer.contract.TimerContract
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerSessionUiState
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun TimerRoute(
    sharedViewModel: SharedViewModel,
    navigateToTimerTodo: (timeOptionIndex: Int, timerStartDate: String) -> Unit,
    shouldRestartTimer: Boolean,
    modifier: Modifier = Modifier,
    timerViewmodel: TimerViewModel = hiltViewModel(),
) {
    val timerState by timerViewmodel.uiState.collectAsStateWithLifecycle()
    val sharedState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    val success by timerViewmodel.success.collectAsStateWithLifecycle(initialValue = true)

    LaunchedEffect(timerViewmodel.uiSideEffect) {
        timerViewmodel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                is TimerContract.TimerSideEffect.NavigateToTimerTodo -> navigateToTimerTodo(effect.timeOptionIndex, effect.timerStartDate)
            }
        }
    }

    LaunchedEffect(shouldRestartTimer) {
        if (shouldRestartTimer) {
            timerViewmodel.setEvent(TimerContract.TimerEvent.OnRestartSheetApproveBtnClick)
        }
    }

    LaunchedEffect(timerState.timerSessionState) {
        if (timerState.timerSessionState is TimerSessionUiState.Ready) {
            sharedViewModel.setEvent(SharedContract.SharedEvent.OnShowBottomBar)
        } else {
            sharedViewModel.setEvent(SharedContract.SharedEvent.OnHideBottomBar)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        timerViewmodel.setEvent(TimerContract.TimerEvent.Initialize)
    }

    when (success) {
        true -> {
            TimerScreen(
                timerState = timerState,
                sharedState = sharedState,
                modifier = modifier,
                onBreadIconClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnBreadIconClick) },
                onResetBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnResetBtnClick) },
                onRestartSheetApproveBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnRestartSheetApproveBtnClick) },
                onRestartSheetDismissBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnRestartSheetDismissBtnClick) },
                onRestartBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnRestartBtnClick) },
                onStartBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnStartBtnClick) },
                onStopBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnStopBtnClick) },
                onTimeOptionToggleClick = { timeOption -> timerViewmodel.setEvent(TimerContract.TimerEvent.OnTimeOptionToggleClick(timeOption)) },
                onBreadSelectionSheetClick = { breadId ->
                    sharedViewModel.setEvent(SharedContract.SharedEvent.OnClickBread(breadId = breadId))
                    timerViewmodel.setEvent(TimerContract.TimerEvent.OnBreadSelectionSheetClick)
                },
                onBreadSelectionSheetDismissRequest = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnBreadSelectionSheetDismissRequest) },
                onCompleteSheetCheckBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnCompleteSheetCheckBtnClick) },
                onCompleteSheetRestartBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnCompleteSheetRestartBtnClick) },
                onCompleteSheetDismissRequest = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnCompleteSheetDismissRequest) },
                onResetSheetApproveBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnResetSheetApproveBtnClick) },
                onResetSheetDismissBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnResetSheetDismissBtnClick) },
            )
        }

        false -> {
            Column(
                modifier =
                    modifier
                        .fillMaxSize()
                        .background(BbangZipTheme.brush.backgroundAccentGradient)
                        .windowInsetsPadding(WindowInsets.systemBars),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
