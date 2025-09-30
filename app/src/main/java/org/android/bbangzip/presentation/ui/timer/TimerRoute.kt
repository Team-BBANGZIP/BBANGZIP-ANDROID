package org.android.bbangzip.presentation.ui.timer

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.ui.shared.SharedContract
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.presentation.ui.timer.contract.TimerContract
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerStatus

@Composable
fun TimerRoute(
    sharedViewModel: SharedViewModel,
    navigateToTimerTodo: (timeOptionIndex: Int) -> Unit,
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
                is TimerContract.TimerSideEffect.NavigateToTimerTodo -> navigateToTimerTodo(effect.timeOptionIndex)
            }
        }
    }

    LaunchedEffect(shouldRestartTimer) {
        if (shouldRestartTimer) {
            timerViewmodel.setEvent(TimerContract.TimerEvent.OnRestartSheetApproveBtnClick)
        }
    }

    LaunchedEffect(timerState.timerStatus) {
        if (timerState.timerStatus == TimerStatus.Idle) {
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
            CircularProgressIndicator()
        }
    }
}
