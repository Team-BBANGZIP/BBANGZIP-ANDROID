package org.android.bbangzip.presentation.ui.timer

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.remote.TimerRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.ui.timer.contract.TimerContract
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerBottomSheetVisibleState
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerSessionUiState
import org.android.bbangzip.presentation.ui.timer.contract.type.TimeOption
import org.android.bbangzip.presentation.ui.timer.lifecycle.TimerLifecycleManager
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimerViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val lifecycleManagerFactory: TimerLifecycleManager.Factory,
    private val timerRepository: TimerRepository,
) : BaseViewModel<TimerContract.TimerEvent, TimerContract.TimerState, TimerContract.TimerReduce, TimerContract.TimerSideEffect>(
    savedStateHandle = savedStateHandle,
) {
    override fun createInitialState(savedState: Parcelable?): TimerContract.TimerState {
        return savedState as? TimerContract.TimerState ?: TimerContract.TimerState()
    }

    private var isAppActive: Boolean = true
    private var backgroundStartTime: Long = 0L
    private var timerJob: Job? = null
    private var appExitCheckJob: Job? = null
    private lateinit var lifecycleManager: TimerLifecycleManager

    init {
        setEvent(TimerContract.TimerEvent.Initialize)
        setupLifecycleManager()
    }

    private fun setupLifecycleManager() {
        lifecycleManager = lifecycleManagerFactory.create(
            onScreenOn = ::onScreenTurnedOn,
            onScreenOffByTimeout = ::onScreenTimeOut,
            onScreenOffByLock = ::onLockButtonPressed,
            onAppForeground = ::onAppForeground,
            onAppBackground = ::onAppBackground
        )
    }

    override fun handleEvent(event: TimerContract.TimerEvent) {
        when (event) {
            is TimerContract.TimerEvent.Initialize ->
                launch {
                    updateTodayBreadCount()
                }

            // Start
            is TimerContract.TimerEvent.OnStartBtnClick -> handleStartOrResumeTimer()

            // Reset
            is TimerContract.TimerEvent.OnResetBtnClick -> {
                stopTimer()
                updateState(TimerContract.TimerReduce.UpdateTimerSessionState(TimerSessionUiState.Paused))
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(reset = true)))
            }

            is TimerContract.TimerEvent.OnResetSheetApproveBtnClick -> {
                resetTimer(moveToReady = true)
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(reset = false)))
            }

            is TimerContract.TimerEvent.OnResetSheetDismissBtnClick -> {
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(reset = false)))
            }

            // Restart
            is TimerContract.TimerEvent.OnRestartBtnClick -> {
                if (currentUiState.timerSessionState is TimerSessionUiState.Running) {
                    handleStopTimer()
                }
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(restart = true)))
            }

            is TimerContract.TimerEvent.OnRestartSheetApproveBtnClick -> {
                resetTimer()
                updateState(TimerContract.TimerReduce.UpdateTimerSessionState(TimerSessionUiState.Running))
                startTimer(currentUiState.timerOption.totalTime)
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(restart = false)))
            }

            is TimerContract.TimerEvent.OnRestartSheetDismissBtnClick -> {
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(restart = false)))
            }

            // Stop
            is TimerContract.TimerEvent.OnStopBtnClick -> handleStopTimer()

            // Complete
            is TimerContract.TimerEvent.OnCompleteSheetCheckBtnClick -> {
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(complete = false)))
                launch {
                    delay(200L)
                    setSideEffect(TimerContract.TimerSideEffect.NavigateToTimerTodo(currentUiState.timerOption.timeOptionIndex))
                    resetTimer(moveToReady = true)
                }
            }

            is TimerContract.TimerEvent.OnCompleteSheetRestartBtnClick -> {
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(complete = false)))
                restartTimer()
            }

            is TimerContract.TimerEvent.OnCompleteSheetDismissRequest -> {
                resetTimer(moveToReady = true)
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(complete = false)))
            }

            is TimerContract.TimerEvent.OnTimeOptionToggleClick -> handleTimeOptionChange(event.selectedTimeOptionIndex)

            is TimerContract.TimerEvent.OnBreadIconClick -> {
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(breadSelection = true)))
            }

            is TimerContract.TimerEvent.OnBreadSelectionSheetClick -> {
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(breadSelection = false)))
            }

            is TimerContract.TimerEvent.OnBreadSelectionSheetDismissRequest -> {
                updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(breadSelection = false)))
            }
        }
    }

    override fun reduceState(
        state: TimerContract.TimerState,
        reduce: TimerContract.TimerReduce,
    ): TimerContract.TimerState {
        return when (reduce) {
            is TimerContract.TimerReduce.UpdateRemainingTime -> state.copy(remainingTime = reduce.remainingTime)
            is TimerContract.TimerReduce.UpdateTimerSessionState -> state.copy(timerSessionState = reduce.sessionState)
            is TimerContract.TimerReduce.UpdateBottomSheetState -> state.copy(bottomSheetState = reduce.bottomSheetState)
            is TimerContract.TimerReduce.UpdateTimeOption -> state.copy(
                timerOption = reduce.option,
                remainingTime = reduce.option.totalTime
            )
            is TimerContract.TimerReduce.UpdateTodayBreadCount ->   state.copy(
                todayBreadCount = reduce.breadCount
            )
        }
    }

    private fun handleTimeOptionChange(index: Int) {
        val newOption = TimeOption.fromIndex(index)
        updateState(TimerContract.TimerReduce.UpdateTimeOption(newOption))
    }

    private fun handleStartOrResumeTimer() {
        val sessionState = currentUiState.timerSessionState
        if (sessionState is TimerSessionUiState.Ready || sessionState is TimerSessionUiState.Paused) {
            updateState(TimerContract.TimerReduce.UpdateTimerSessionState(TimerSessionUiState.Running))
            startTimer(currentUiState.remainingTime)
        }
    }


    private fun handleStopTimer() {
        stopTimer()
        updateState(TimerContract.TimerReduce.UpdateTimerSessionState(TimerSessionUiState.Paused))
    }

    private fun handleTimerTick() {
        val newTime = currentUiState.remainingTime - 1000L
        if (newTime <= 0) {
            handleTimerCompleted()
        } else {
            updateState(TimerContract.TimerReduce.UpdateRemainingTime(newTime))
        }
    }

    private fun handleTimerCompleted() {
        stopTimer()
        updateState(TimerContract.TimerReduce.UpdateRemainingTime(0L))
        updateState(TimerContract.TimerReduce.UpdateTimerSessionState(TimerSessionUiState.Complete))
        updateState(TimerContract.TimerReduce.UpdateBottomSheetState(TimerBottomSheetVisibleState(complete = true)))
        // Todo: 완료시 오늘 구운 빵 개수 증가 -> Index 개수로 판단
    }

    private fun startTimer(duration: Long) {
        stopTimer()
        updateState(TimerContract.TimerReduce.UpdateRemainingTime(duration))
        timerJob = viewModelScope.launch {
            while (isActive && currentUiState.remainingTime > 0) {
                delay(1000L)
                handleTimerTick()
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }


    private fun restartTimer() {
        stopTimer()

        launch {
            delay(1000L)
            startTimer(currentUiState.timerOption.totalTime)
        }
    }

    private fun resetTimer(moveToReady: Boolean = false) {
        stopTimer()
        updateState(TimerContract.TimerReduce.UpdateRemainingTime(currentUiState.timerOption.totalTime))
        if (moveToReady) {
            // TODO: todayBreadCount는 Repository에서 다시 가져와야 함
            updateState(TimerContract.TimerReduce.UpdateTimerSessionState(TimerSessionUiState.Ready()))
        }
    }


    private suspend fun updateTodayBreadCount() {
        timerRepository.fetchTodayBreadCount().onSuccess { data ->
            val breadCount = data
            val currentState = currentUiState.timerSessionState
            if (currentState is TimerSessionUiState.Ready)
                updateState(TimerContract.TimerReduce.UpdateTodayBreadCount(breadCount))
        }
    }

    private fun scheduleIdleTransition() {
        appExitCheckJob?.cancel()
        appExitCheckJob = viewModelScope.launch {
            delay(60 * 1000L)
            if (!isAppActive) {
                resetTimer(moveToReady = true)
            }
        }
    }

    private fun cancelIdleTransition() {
        appExitCheckJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        appExitCheckJob?.cancel()
        lifecycleManager.cleanup()
    }

    // --- 라이프사이클 이벤트 핸들러 ---
    // TimerLifecycleManager로부터 호출되는 공개 함수들
    fun onScreenTimeOut() {
        Timber.d("Lifecycle Event: Screen timed out")
    }

    fun onLockButtonPressed() {
        Timber.d("Lifecycle Event: Lock button pressed")
        if (currentUiState.timerSessionState is TimerSessionUiState.Running) {
            handleStopTimer()
        }
    }

    fun onScreenTurnedOn() {
        Timber.d("Lifecycle Event: Screen turned on")
    }

    fun onAppBackground() {
        Timber.d("Lifecycle Event: App went to background")
        isAppActive = false
        backgroundStartTime = System.currentTimeMillis()
        if (currentUiState.timerSessionState is TimerSessionUiState.Running) {
            handleStopTimer()
            scheduleIdleTransition()
        }
    }

    fun onAppForeground(exitDuration: Long) {
        Timber.d("Lifecycle Event: App came to foreground after ${exitDuration}ms")
        isAppActive = true
        cancelIdleTransition()

        if (exitDuration >= 60 * 1000L) {
            resetTimer(moveToReady = true)
        }
    }
}
