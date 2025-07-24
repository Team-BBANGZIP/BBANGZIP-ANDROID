package org.android.bbangzip.presentation.ui.timer

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.android.bbangzip.presentation.ui.shared.SharedViewModel

@Composable
fun TimerRoute(
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier,
    showBottomBar: () -> Unit = {},
    hideBottomBar: () -> Unit = {},
    navigateToCompleteTask: () -> Unit = {},
    timerViewmodel: TimerViewModel = hiltViewModel()
) {
    val timerState by timerViewmodel.uiState.collectAsStateWithLifecycle()
    val success by timerViewmodel.success.collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(timerViewmodel.uiSideEffect) {
        timerViewmodel.uiSideEffect.collect { effect ->
            when (effect) {
                TimerContract.TimerSideEffect.NavigateToCompleteTask -> navigateToCompleteTask()
                TimerContract.TimerSideEffect.ShowBottomBar -> showBottomBar()
                TimerContract.TimerSideEffect.HideBottomBar -> hideBottomBar()
            }
        }
    }

    when (success) {
        true -> {
            TimerScreen(
                timerState = timerState,
                modifier = modifier,
                onBreadIconClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnBreadIconClick) },
                onResetBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnResetBtnClick) },
                onRepeatSheetApproveBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnRepeatSheetApproveBtnClick) },
                onRepeatSheetDismissBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnRepeatSheetDismissBtnClick) },
                onRepeatBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnRepeatBtnClick) },
                onStartBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnStartBtnClick) },
                onStopBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnStopBtnClick) },
                onTimeOptionToggleClick = { timeOption -> timerViewmodel.setEvent(TimerContract.TimerEvent.OnTimeOptionToggleClick(timeOption)) },
                onBreadSelectionSheetClick = { breadType -> timerViewmodel.setEvent(TimerContract.TimerEvent.OnBreadSelectionSheetClick(breadType = breadType)) },
                onCompleteSheetCheckBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnCompleteSheetCheckBtnClick) },
                onCompleteSheetRetryBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnCompleteSheetRetryBtnClick) },
                onCompleteSheetDismissRequest = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnCompleteSheetDismissRequest) },
                onEndConfirmSheetApproveBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnEndConfirmSheetApproveBtnClick) },
                onEndConfirmSheetDismissBtnClick = { timerViewmodel.setEvent(TimerContract.TimerEvent.OnEndConfirmSheetDismissBtnClick) }

            )
        }

        false -> {
            CircularProgressIndicator()
        }
    }
}

