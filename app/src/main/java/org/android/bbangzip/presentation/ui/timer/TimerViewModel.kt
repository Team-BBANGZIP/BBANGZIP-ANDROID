package org.android.bbangzip.presentation.ui.timer

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.android.bbangzip.presentation.model.TimerStatus
import org.android.bbangzip.presentation.util.base.BaseViewModel
import org.android.bbangzip.presentation.util.constant.TimerDuration
import javax.inject.Inject

@HiltViewModel
class TimerViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<TimerContract.TimerEvent, TimerContract.TimerState, TimerContract.TimerReduce, TimerContract.TimerSideEffect>(
    savedStateHandle = savedStateHandle,
) {
    override fun createInitialState(savedState: Parcelable?): TimerContract.TimerState {
        return savedState as? TimerContract.TimerState ?: TimerContract.TimerState()
    }

    init {
        setEvent(TimerContract.TimerEvent.Initialize)
    }

    private var timerJob: Job? = null

    override fun handleEvent(event: TimerContract.TimerEvent) {
        when (event) {
            is TimerContract.TimerEvent.Initialize ->
                launch {
                    updateState(TimerContract.TimerReduce.UpdateTodayBreadCount(5)) // 초기 빵 이미지 리소스 설정
                }

            //Start
            is TimerContract.TimerEvent.OnStartBtnClick -> {
                if (currentUiState.timerStatus == TimerStatus.Idle) {
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                    startTimer(currentUiState.remainingTime)
                    setSideEffect(TimerContract.TimerSideEffect.HideBottomBar)
                } else if (currentUiState.timerStatus == TimerStatus.Paused) {
                    resumeTimer()
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                }
            }

            //Reset
            is TimerContract.TimerEvent.OnResetBtnClick -> {
                stopTimer() // 타이머 정지
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
                updateState(TimerContract.TimerReduce.UpdateResetSheetState(true))
            }

            is TimerContract.TimerEvent.OnResetSheetApproveBtnClick -> {
                resetTimer() // 타이머 리셋
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle))
                setSideEffect(TimerContract.TimerSideEffect.ShowBottomBar)
                updateState(TimerContract.TimerReduce.UpdateResetSheetState(false))
            }

            is TimerContract.TimerEvent.OnResetSheetDismissBtnClick -> {
                updateState(TimerContract.TimerReduce.UpdateResetSheetState(false))
            }

            //Restart
            is TimerContract.TimerEvent.OnRestartBtnClick -> {
                stopTimer() // 타이머 정지
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
                updateState(TimerContract.TimerReduce.UpdateRestartSheetState(true))
            }

            is TimerContract.TimerEvent.OnRestartSheetApproveBtnClick -> {
                resetTimer() // 타이머 리셋
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                startTimer(currentUiState.totalTime)
                updateState(TimerContract.TimerReduce.UpdateRestartSheetState(false)) // 리셋 BottomSheet 숨김

            }

            is TimerContract.TimerEvent.OnRestartSheetDismissBtnClick -> {
                updateState(TimerContract.TimerReduce.UpdateRestartSheetState(false)) // 리셋 BottomSheet 숨김
            }

            //Stop
            is TimerContract.TimerEvent.OnStopBtnClick -> {
                stopTimer()
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
            }

            //Complete
            is TimerContract.TimerEvent.OnTimerCompleted -> {
                updateState(TimerContract.TimerReduce.UpdateRemainingTime(0L))
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Complete)) // 타이머 완료 상태로 변경
                updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(true)) // 완료 BottomSheet 표시
                //Todo: 완료시 오늘 구운 빵 개수 증가 -> Index 개수로 판단
            }

            is TimerContract.TimerEvent.OnCompleteSheetCheckBtnClick -> {
                resetTimer()
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle))
                updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false))

//              TODO : setSideEffect(TimerContract.TimerSideEffect.NavigateToCompleteTask) // Task 스크린 만들기
            }

            is TimerContract.TimerEvent.OnCompleteSheetRestartBtnClick -> {
                restartTimer() // 타이머 재시작
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))
                updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false)) // 완료 BottomSheet 숨김
            }

            is TimerContract.TimerEvent.OnCompleteSheetDismissRequest -> {
                resetTimer()
                updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle))
                updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false))
                setSideEffect(TimerContract.TimerSideEffect.ShowBottomBar)
            }

            is TimerContract.TimerEvent.OnTimeOptionToggleClick -> {
                updateState(TimerContract.TimerReduce.UpdateSelectedTimeOptionIndex(event.selectedTimeOptionIndex))
                getStartTimeForOption(event.selectedTimeOptionIndex)
            }

            is TimerContract.TimerEvent.OnBreadIconClick -> {
                updateState(TimerContract.TimerReduce.UpdateBreadSelectionSheetState(true)) // 빵 선택 BottomSheet 표시
            }

            is TimerContract.TimerEvent.OnBreadSelectionSheetClick -> {
                updateState(TimerContract.TimerReduce.UpdateBreadSelectionSheetState(false))
            }

            is TimerContract.TimerEvent.OnBreadSelectionSheetDismissRequest -> {
                updateState(TimerContract.TimerReduce.UpdateBreadSelectionSheetState(false)) // 빵 선택 BottomSheet 숨김
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
        }
    }

    override fun reduceState(state: TimerContract.TimerState, reduce: TimerContract.TimerReduce): TimerContract.TimerState {
        return when (reduce) {
            is TimerContract.TimerReduce.UpdateTimerStatus -> state.copy(timerStatus = reduce.timerStatus)
            is TimerContract.TimerReduce.UpdateRemainingTime -> state.copy(remainingTime = reduce.remainingTime)
            is TimerContract.TimerReduce.UpdateBreadLevel -> state.copy(breadLevel = reduce.breadLevel)
            is TimerContract.TimerReduce.UpdateTodayBreadCount -> state.copy(todayBreadCount = reduce.todayBreadCount)
            is TimerContract.TimerReduce.UpdateSelectedTimeOptionIndex -> state.copy(selectedTimeOptionIndex = reduce.selectedTimeOptionIndex)
            is TimerContract.TimerReduce.UpdateBreadSelectionSheetState -> state.copy(
                isBreadSelectionSheetVisible = reduce.isBreadSelectionSheetVisible
            )

            is TimerContract.TimerReduce.UpdateCompleteSheetState -> state.copy(
                isCompleteSheetVisible = reduce.isCompleteSheetVisible
            )

            is TimerContract.TimerReduce.UpdateResetSheetState -> state.copy(
                isResetSheetVisible = reduce.isResetSheetVisible
            )

            is TimerContract.TimerReduce.UpdateRestartSheetState -> state.copy(
                isRestartSheetVisible = reduce.isRestartSheetVisible
            )

            is TimerContract.TimerReduce.UpdateTotalTime -> state.copy(totalTime = reduce.totalTime)

            is TimerContract.TimerReduce.UpdateBreadList -> state.copy(breadList = reduce.breadList)
        }
    }

    private fun getStartTimeForOption(index: Int) {
        when (index) {
            0 -> {
                updateState(TimerContract.TimerReduce.UpdateTotalTime(TimerDuration.THIRTY_MINUTES))
                updateState(TimerContract.TimerReduce.UpdateRemainingTime(TimerDuration.THIRTY_MINUTES))
            }

            1 -> {
                updateState(TimerContract.TimerReduce.UpdateTotalTime(TimerDuration.SIXTY_MINUTES))
                updateState(TimerContract.TimerReduce.UpdateRemainingTime(TimerDuration.SIXTY_MINUTES))

            }

            else -> throw IllegalArgumentException("Invalid time option index")
        }
    }

    private fun startTimer(duration: Long) {
        timerJob?.cancel()

        updateState(TimerContract.TimerReduce.UpdateRemainingTime(duration))

        timerJob = viewModelScope.launch {
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
        timerJob?.cancel()
        updateState(TimerContract.TimerReduce.UpdateRemainingTime(currentUiState.totalTime))
        startTimer(currentUiState.remainingTime) // 현재 남은 시간을 그대로 사용하여 타이머 재시작
    }

    private fun resetTimer() {
        timerJob?.cancel()
        updateState(TimerContract.TimerReduce.UpdateRemainingTime(currentUiState.totalTime))
    }


    private fun updateBreadLevelByRemainingTime(
    ) {
        updateState(
            TimerContract.TimerReduce.UpdateBreadLevel(
                when (currentUiState.progress) {
                    in 0f..25f -> 1
                    in 25f..50f -> 2
                    in 50f..75f -> 3
                    in 75f..<100f -> 4
                    else -> 5
                }
            )
        )
    }
}