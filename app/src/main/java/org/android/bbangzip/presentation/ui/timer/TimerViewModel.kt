package org.android.bbangzip.presentation.ui.timer

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.android.bbangzip.domain.usecase.CompleteTimerUseCase
import org.android.bbangzip.domain.usecase.FetchBreadListUseCase
import org.android.bbangzip.domain.usecase.UpdateTodayBreadCountUseCase
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.common.util.constant.TimerConstants
import org.android.bbangzip.presentation.ui.timer.contract.TimerContract
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerStatus
import org.android.bbangzip.presentation.ui.timer.lifecycle.TimerLifecycleManager
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimerViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val lifecycleManagerFactory: TimerLifecycleManager.Factory,
        private val completeTimerUseCase: CompleteTimerUseCase,
        private val fetchBreadListUseCase: FetchBreadListUseCase,
        private val updateTodayBreadCountUseCase: UpdateTodayBreadCountUseCase,
    ) : BaseViewModel<TimerContract.TimerEvent, TimerContract.TimerState, TimerContract.TimerReduce, TimerContract.TimerSideEffect>(
            savedStateHandle = savedStateHandle,
        ) {
        override fun createInitialState(savedState: Parcelable?): TimerContract.TimerState {
            return savedState as? TimerContract.TimerState ?: TimerContract.TimerState()
        }

        private var timerJob: Job? = null
        private lateinit var lifecycleManager: TimerLifecycleManager
        private var appExitCheckJob: Job? = null

        init {
            setEvent(TimerContract.TimerEvent.Initialize)
            setupLifecycleManager()
        }

        private fun setupLifecycleManager() {
            lifecycleManager =
                lifecycleManagerFactory.create { event ->
                    setEvent(event)
                }
        }

        override fun handleEvent(event: TimerContract.TimerEvent) {
            when (event) {
                is TimerContract.TimerEvent.Initialize ->
                    launch {
                        updateState(TimerContract.TimerReduce.UpdateTodayBreadCount(5))
                    }

                // Start
                is TimerContract.TimerEvent.OnStartBtnClick -> {
                    if (currentUiState.timerStatus == TimerStatus.Idle) {
                        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                        startTimer(currentUiState.totalTime)
                    } else if (currentUiState.timerStatus == TimerStatus.Paused) {
                        resumeTimer()
                        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                    }
                }

                // Reset
                is TimerContract.TimerEvent.OnResetBtnClick -> {
                    stopTimer()
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
                    updateState(TimerContract.TimerReduce.UpdateResetSheetState(true))
                }

                is TimerContract.TimerEvent.OnResetSheetApproveBtnClick -> {
                    resetTimer()
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle))
                    updateState(TimerContract.TimerReduce.UpdateResetSheetState(false))
                }

                is TimerContract.TimerEvent.OnResetSheetDismissBtnClick -> {
                    updateState(TimerContract.TimerReduce.UpdateResetSheetState(false))
                }

                // Restart
                is TimerContract.TimerEvent.OnRestartBtnClick -> {
                    stopTimer()
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
                    updateState(TimerContract.TimerReduce.UpdateRestartSheetState(true))
                }

                is TimerContract.TimerEvent.OnRestartSheetApproveBtnClick -> {
                    resetTimer()
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                    startTimer(currentUiState.totalTime)
                    updateState(TimerContract.TimerReduce.UpdateRestartSheetState(false))
                }

                is TimerContract.TimerEvent.OnRestartSheetDismissBtnClick -> {
                    updateState(TimerContract.TimerReduce.UpdateRestartSheetState(false))
                }

                // Stop
                is TimerContract.TimerEvent.OnStopBtnClick -> {
                    stopTimer()
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
                }

                // Complete
                is TimerContract.TimerEvent.OnTimerCompleted -> {
                    stopTimer()
                    updateState(TimerContract.TimerReduce.UpdateRemainingTime(0L))
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Complete))
                    updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(true))
                    // Todo: 완료시 오늘 구운 빵 개수 증가 -> Index 개수로 판단
                }

                is TimerContract.TimerEvent.OnCompleteSheetCheckBtnClick -> {
                    updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false))
                    launch {
                        // 자연스러운 화면전환을 위해 추가
                        delay(200L)
                        setSideEffect(TimerContract.TimerSideEffect.NavigateToTimerTodo(currentUiState.selectedTimeOptionIndex))
                        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle))
                        resetTimer()
                    }
                }

                is TimerContract.TimerEvent.OnCompleteSheetRestartBtnClick -> {
                    updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false))
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                    restartTimer()
                }

                is TimerContract.TimerEvent.OnCompleteSheetDismissRequest -> {
                    resetTimer()
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle))
                    updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false))
                }

                is TimerContract.TimerEvent.OnTimeOptionToggleClick -> {
                    updateState(TimerContract.TimerReduce.UpdateSelectedTimeOptionIndex(event.selectedTimeOptionIndex))
                    getStartTimeForOption(event.selectedTimeOptionIndex)
                }

                is TimerContract.TimerEvent.OnBreadIconClick -> {
                    updateState(TimerContract.TimerReduce.UpdateBreadSelectionSheetState(true))
                }

                is TimerContract.TimerEvent.OnBreadSelectionSheetClick -> {
                    updateState(TimerContract.TimerReduce.UpdateBreadSelectionSheetState(false))
                }

                is TimerContract.TimerEvent.OnBreadSelectionSheetDismissRequest -> {
                    updateState(TimerContract.TimerReduce.UpdateBreadSelectionSheetState(false))
                }

                is TimerContract.TimerEvent.OnTimerTick -> {
                    val newRemainingTime = currentUiState.remainingTime - 1000L

                    if (newRemainingTime <= 0) {
                        updateState(TimerContract.TimerReduce.UpdateRemainingTime(0L))
                        setEvent(TimerContract.TimerEvent.OnTimerCompleted)
                    } else {
                        updateState(TimerContract.TimerReduce.UpdateRemainingTime(newRemainingTime))
                        updateBreadLevelByRemainingTime()
                    }
                }

                is TimerContract.TimerEvent.OnScreenTimeOut -> {
                    updateState(TimerContract.TimerReduce.UpdateIsScreenOn(false))
                }

                is TimerContract.TimerEvent.OnLockButtonPressed -> {
                    updateState(TimerContract.TimerReduce.UpdateIsScreenOn(false))
                    if (currentUiState.timerStatus == TimerStatus.Running) {
                        stopTimer()
                        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
                    }
                }

                is TimerContract.TimerEvent.OnScreenTurnedOn -> {
                    updateState(TimerContract.TimerReduce.UpdateIsScreenOn(true))
                }

                TimerContract.TimerEvent.OnAppBackground -> {
                    updateState(TimerContract.TimerReduce.UpdateIsAppActive(false))
                    updateState(TimerContract.TimerReduce.UpdateBackgroundStartTime(System.currentTimeMillis()))

                    if (currentUiState.timerStatus == TimerStatus.Running) {
                        stopTimer()
                        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))

                        scheduleIdleTransition()

                        Timber.tag("TimerViewModel").d("App exited - timer paused, 1min countdown started")
                    }
                }

                is TimerContract.TimerEvent.OnAppForeground -> {
                    updateState(TimerContract.TimerReduce.UpdateIsAppActive(true))
                    cancelIdleTransition()

                    if (event.exitDuration >= 60 * 1000L) {
                        Timber.tag("TimerViewModel").d("App returned after 1+ min - already idle")
                    } else {
                        Timber.tag("TimerViewModel").d("App returned within 1min - remains paused")
                    }
                }
            }
        }

        override fun reduceState(
            state: TimerContract.TimerState,
            reduce: TimerContract.TimerReduce,
        ): TimerContract.TimerState {
            return when (reduce) {
                is TimerContract.TimerReduce.UpdateTimerStatus -> state.copy(timerStatus = reduce.timerStatus)
                is TimerContract.TimerReduce.UpdateRemainingTime -> state.copy(remainingTime = reduce.remainingTime)
                is TimerContract.TimerReduce.UpdateBreadLevel -> state.copy(breadLevel = reduce.breadLevel)
                is TimerContract.TimerReduce.UpdateTodayBreadCount -> state.copy(todayBreadCount = reduce.todayBreadCount)
                is TimerContract.TimerReduce.UpdateSelectedTimeOptionIndex -> state.copy(selectedTimeOptionIndex = reduce.selectedTimeOptionIndex)
                is TimerContract.TimerReduce.UpdateBreadSelectionSheetState ->
                    state.copy(
                        isBreadSelectionSheetVisible = reduce.isBreadSelectionSheetVisible,
                    )

                is TimerContract.TimerReduce.UpdateCompleteSheetState ->
                    state.copy(
                        isCompleteSheetVisible = reduce.isCompleteSheetVisible,
                    )

                is TimerContract.TimerReduce.UpdateResetSheetState ->
                    state.copy(
                        isResetSheetVisible = reduce.isResetSheetVisible,
                    )

                is TimerContract.TimerReduce.UpdateRestartSheetState ->
                    state.copy(
                        isRestartSheetVisible = reduce.isRestartSheetVisible,
                    )

                is TimerContract.TimerReduce.UpdateTotalTime -> state.copy(totalTime = reduce.totalTime)

                is TimerContract.TimerReduce.UpdateBreadList -> state.copy(breadList = reduce.breadList)
                is TimerContract.TimerReduce.UpdateBackgroundStartTime -> state.copy(backgroundStartTime = reduce.time)
                is TimerContract.TimerReduce.UpdateIsAppActive -> state.copy(isAppActive = reduce.isActive)
                is TimerContract.TimerReduce.UpdateIsScreenOn -> state.copy(isScreenOn = reduce.isScreenOn)
            }
        }

        private fun getStartTimeForOption(index: Int) {
            when (index) {
                0 -> {
                    updateState(TimerContract.TimerReduce.UpdateTotalTime(TimerConstants.THIRTY_MINUTES))
                    updateState(TimerContract.TimerReduce.UpdateRemainingTime(TimerConstants.THIRTY_MINUTES))
                }

                1 -> {
                    updateState(TimerContract.TimerReduce.UpdateTotalTime(TimerConstants.SIXTY_MINUTES))
                    updateState(TimerContract.TimerReduce.UpdateRemainingTime(TimerConstants.SIXTY_MINUTES))
                }

                else -> throw IllegalArgumentException("Invalid time option index")
            }
        }

        private fun startTimer(duration: Long) {
            stopTimer()

            updateState(TimerContract.TimerReduce.UpdateRemainingTime(duration))

            timerJob =
                launch {
                    while (currentUiState.remainingTime > 0) {
                        delay(1000L)
                        setEvent(TimerContract.TimerEvent.OnTimerTick)
                    }
                }
        }

        private fun stopTimer() {
            timerJob?.cancel()
        }

        private fun resumeTimer() {
            startTimer(currentUiState.remainingTime)
        }

        private fun restartTimer() {
            stopTimer()

            launch {
                delay(1000L)
                startTimer(currentUiState.totalTime)
            }
        }

        private fun resetTimer() {
            stopTimer()

            updateState(TimerContract.TimerReduce.UpdateRemainingTime(currentUiState.totalTime))
        }

        private fun updateBreadLevelByRemainingTime() {
            updateState(
                TimerContract.TimerReduce.UpdateBreadLevel(
                    when (currentUiState.progress) {
                        in 0f..25f -> 1
                        in 25f..50f -> 2
                        in 50f..75f -> 3
                        in 75f..<100f -> 4
                        else -> 5
                    },
                ),
            )
        }

        private fun scheduleIdleTransition() {
            appExitCheckJob?.cancel()

            appExitCheckJob =
                launch {
                    delay(60 * 1000L)

                    if (!currentUiState.isAppActive && currentUiState.timerStatus == TimerStatus.Paused) {
                        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle))
                        resetTimer()
                        Timber.tag("TimerViewModel").d("Auto transition to idle after 1min exit")
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
    }
