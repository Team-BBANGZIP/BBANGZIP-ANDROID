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
                    updateState(TimerContract.TimerReduce.UpdateBreadImg(0)) // 초기 빵 이미지 리소스 설정
                }

            is TimerContract.TimerEvent.OnBreadIconClick -> {
                updateState(TimerContract.TimerReduce.UpdateBreadSelectionSheetState(true)) // 빵 선택 BottomSheet 표시
            }

            is TimerContract.TimerEvent.OnResetBtnClick -> TODO()

            is TimerContract.TimerEvent.OnRepeatSheetApproveBtnClick -> TODO()
            is TimerContract.TimerEvent.OnRepeatSheetDismissBtnClick -> TODO()
            is TimerContract.TimerEvent.OnRepeatBtnClick -> TODO()
            is TimerContract.TimerEvent.OnStartBtnClick -> TODO()
            is TimerContract.TimerEvent.OnStopBtnClick -> TODO()
            is TimerContract.TimerEvent.OnTimeOptionToggleClick -> TODO()
            is TimerContract.TimerEvent.OnBreadSelectionSheetClick -> TODO()
            is TimerContract.TimerEvent.OnCompleteSheetCheckBtnClick -> TODO()
            is TimerContract.TimerEvent.OnCompleteSheetRetryBtnClick -> TODO()
            is TimerContract.TimerEvent.OnCompleteSheetDismissRequest -> {
                updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false)) // 완료 BottomSheet 숨김
            }

            is TimerContract.TimerEvent.OnEndConfirmSheetApproveBtnClick -> TODO()
            is TimerContract.TimerEvent.OnEndConfirmSheetDismissBtnClick -> TODO()
        }
    }

    override fun reduceState(state: TimerContract.TimerState, reduce: TimerContract.TimerReduce): TimerContract.TimerState {
        return when (reduce) {
            is TimerContract.TimerReduce.UpdateTimerStatus -> state.copy(timerStatus = reduce.timerStatus)
            is TimerContract.TimerReduce.UpdateRemainingTime -> state.copy(remainingTime = reduce.remainingTime)
            is TimerContract.TimerReduce.UpdateBreadImg -> state.copy(breadImg = reduce.breadImg)
            is TimerContract.TimerReduce.UpdateTodayBreadCount -> state.copy(todayBreadCount = reduce.todayBreadCount)
            is TimerContract.TimerReduce.UpdateSelectedTimeOptionIndex -> state.copy(selectedTimeOptionIndex = reduce.selectedTimeOptionIndex)
            is TimerContract.TimerReduce.UpdateBreadSelectionSheetState -> state.copy(
                isBreadSelectionSheetVisible = reduce.isBreadSelectionSheetVisible
            )

            is TimerContract.TimerReduce.UpdateCompleteSheetState -> state.copy(
                isCompleteSheetVisible = reduce.isCompleteSheetVisible
            )

            is TimerContract.TimerReduce.UpdateResetConfirmSheetState -> state.copy(
                isResetConfirmSheetVisible = reduce.isResetConfirmSheetVisible
            )

            is TimerContract.TimerReduce.UpdateRepeatSheetState -> state.copy(
                isRepeatSheetVisible = reduce.isRepeatSheetVisible
            )
        }
    }

    private fun getStartTimeForOption(index: Int): Long {
        return when (index) {
            0 -> TimerDuration.THIRTY_MINUTES
            1 -> TimerDuration.SIXTY_MINUTES
            else -> throw IllegalArgumentException("Invalid time option index")
        }
    }

    //Timer 관련 로직
    private fun startTimer(duration: Long) {
        timerJob?.cancel()

        updateState(TimerContract.TimerReduce.UpdateRemainingTime(duration))
        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Running))

        timerJob = viewModelScope.launch {
            while (currentUiState.remainingTime > 0 && currentUiState.timerStatus == TimerStatus.Running) {
                // 타이머 로직 구현
                delay(1000L) // 1초마다 업데이트
                val newRemainingTime = currentUiState.remainingTime - 1000L

                if (newRemainingTime <= 0) {
                    updateState(TimerContract.TimerReduce.UpdateRemainingTime(0L))
                    updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Complete))
                    updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(true)) // 완료 BottomSheet 표시
                } else {
                    updateState(TimerContract.TimerReduce.UpdateRemainingTime(newRemainingTime))
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Paused))
    }

    private fun resumeTimer() {
        if (currentUiState.timerStatus == TimerStatus.Paused) {
            startTimer(currentUiState.remainingTime)
        }
    }

    private fun repeatTimer() {
        timerJob?.cancel()
        updateState(TimerContract.TimerReduce.UpdateRemainingTime(getStartTimeForOption(currentUiState.selectedTimeOptionIndex)))
        startTimer(currentUiState.remainingTime) // 현재 남은 시간을 그대로 사용하여 타이머 재시작
        updateState(TimerContract.TimerReduce.UpdateCompleteSheetState(false)) // 완료 BottomSheet 숨김
    }

    private fun resetTimer() {
        timerJob?.cancel()
        updateState(TimerContract.TimerReduce.UpdateRemainingTime(getStartTimeForOption(currentUiState.selectedTimeOptionIndex)))
        updateState(TimerContract.TimerReduce.UpdateTimerStatus(TimerStatus.Idle)) // 타이머 상태를 Idle로 변경
        updateState(TimerContract.TimerReduce.UpdateResetConfirmSheetState(false)) // 완료 BottomSheet 숨김
    }
}