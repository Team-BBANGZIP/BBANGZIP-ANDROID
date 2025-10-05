package org.android.bbangzip.presentation.ui.timer

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    val dispatch =
        remember(timerViewmodel) {
            { event: TimerContract.TimerEvent -> timerViewmodel.setEvent(event) }
        }

    val sharedDispatch =
        remember(sharedViewModel) {
            { event: SharedContract.SharedEvent -> sharedViewModel.setEvent(event) }
        }

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
                onBreadIconClick = { dispatch(TimerContract.TimerEvent.OnBreadIconClick) },
                onResetBtnClick = { dispatch(TimerContract.TimerEvent.OnResetBtnClick) },
                onRestartSheetApproveBtnClick = { dispatch(TimerContract.TimerEvent.OnRestartSheetApproveBtnClick) },
                onRestartSheetDismissBtnClick = { dispatch(TimerContract.TimerEvent.OnRestartSheetDismissBtnClick) },
                onRestartBtnClick = { dispatch(TimerContract.TimerEvent.OnRestartBtnClick) },
                onStartBtnClick = { dispatch(TimerContract.TimerEvent.OnStartBtnClick) },
                onStopBtnClick = { dispatch(TimerContract.TimerEvent.OnStopBtnClick) },
                onTimeOptionToggleClick = { timeOption -> dispatch(TimerContract.TimerEvent.OnTimeOptionToggleClick(timeOption)) },
                onBreadSelectionSheetClick = { breadId ->
                    sharedDispatch(SharedContract.SharedEvent.OnClickBread(breadId))
                    dispatch(TimerContract.TimerEvent.OnBreadSelectionSheetClick)
                },
                onBreadSelectionSheetDismissRequest = { dispatch(TimerContract.TimerEvent.OnBreadSelectionSheetDismissRequest) },
                onCompleteSheetCheckBtnClick = { dispatch(TimerContract.TimerEvent.OnCompleteSheetCheckBtnClick) },
                onCompleteSheetRestartBtnClick = { dispatch(TimerContract.TimerEvent.OnCompleteSheetRestartBtnClick) },
                onCompleteSheetDismissRequest = { dispatch(TimerContract.TimerEvent.OnCompleteSheetDismissRequest) },
                onResetSheetApproveBtnClick = { dispatch(TimerContract.TimerEvent.OnResetSheetApproveBtnClick) },
                onResetSheetDismissBtnClick = { dispatch(TimerContract.TimerEvent.OnResetSheetDismissBtnClick) },
            )
        }

        false -> {
            CircularProgressIndicator()
        }
    }
}
