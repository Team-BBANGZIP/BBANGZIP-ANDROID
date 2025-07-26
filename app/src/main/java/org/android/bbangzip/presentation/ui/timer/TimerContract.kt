package org.android.bbangzip.presentation.ui.timer

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.model.TimerStatus
import org.android.bbangzip.presentation.util.base.BaseContract
import org.android.bbangzip.presentation.util.extension.formatTime

class TimerContract {
    @Parcelize
    data class TimerState(
        val remainingTime: Long = 0L, // 타이머 시간
        val totalTime: Long = 30 * 60 * 1000L, // 타이머 총 시간
        val formattedTime: String = remainingTime.formatTime(), // 포맷된 타이머 시간
        val progress: Float = 0f, // 타이머 진행률
        val progressPercentage: Float = ((totalTime - remainingTime).toFloat() / totalTime.toFloat()) * 100f,
        val pausedTime: Long = 0L, // 일시정지된 시간
        val timerStatus: TimerStatus = TimerStatus.Idle,
        @DrawableRes val breadImg: Int = 0,  // 빵 이미지 리소스
        val todayBreadCount: Int = 0,// 오늘 구운 빵의 개수ㅡ
        val selectedTimeOptionIndex: Int = 0, // 타이머 시간변경 토글 인덱스,
        val isBreadSelectionSheetVisible: Boolean = false,
        val isRestartSheetVisible: Boolean = false,
        val isResetSheetVisible: Boolean = false,
        val isCompleteSheetVisible: Boolean = false,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface TimerEvent : BaseContract.Event {
        data object Initialize : TimerEvent
        data object OnStartBtnClick : TimerEvent
        data object OnStopBtnClick : TimerEvent
        data object OnRestartBtnClick : TimerEvent
        data object OnRestartSheetDismissBtnClick : TimerEvent
        data object OnRestartSheetApproveBtnClick : TimerEvent
        data object OnResetBtnClick : TimerEvent
        data object OnResetSheetDismissBtnClick : TimerEvent
        data object OnResetSheetApproveBtnClick : TimerEvent
        data object OnBreadIconClick : TimerEvent
        data object OnBreadSelectionSheetDismissRequest : TimerEvent
        data object OnTimerCompleted : TimerEvent
        data object OnCompleteSheetRetryBtnClick : TimerEvent
        data object OnCompleteSheetCheckBtnClick : TimerEvent
        data object OnCompleteSheetDismissRequest : TimerEvent
        data object OnTimerTick : TimerEvent
        data class OnTimeOptionToggleClick(
            val selectedTimeOptionIndex: Int
        ) : TimerEvent

        data class OnBreadSelectionSheetClick(
            val breadId: Int,
        ) : TimerEvent
    }

    sealed interface TimerReduce : BaseContract.Reduce {
        data class UpdateTimerStatus(
            val timerStatus: TimerStatus,
        ) : TimerReduce

        data class UpdateRemainingTime(
            val remainingTime: Long,
        ) : TimerReduce

        data class UpdateBreadImg(
            @DrawableRes val breadImg: Int,
        ) : TimerReduce

        data class UpdateTodayBreadCount(
            val todayBreadCount: Int,
        ) : TimerReduce

        data class UpdateSelectedTimeOptionIndex(
            val selectedTimeOptionIndex: Int
        ) : TimerReduce

        data class UpdateBreadSelectionSheetState(
            val isBreadSelectionSheetVisible: Boolean
        ) : TimerReduce

        data class UpdateRestartSheetState(
            val isRestartSheetVisible: Boolean
        ) : TimerReduce

        data class UpdateResetSheetState(
            val isResetSheetVisible: Boolean
        ) : TimerReduce

        data class UpdateCompleteSheetState(
            val isCompleteSheetVisible: Boolean
        ) : TimerReduce

        data class  UpdateTotalTime(
            val totalTime: Long
        ) : TimerReduce
    }

    sealed interface TimerSideEffect : BaseContract.SideEffect {
        data object NavigateToCompleteTask : TimerSideEffect
        data object ShowBottomBar : TimerSideEffect
        data object HideBottomBar : TimerSideEffect
    }
}