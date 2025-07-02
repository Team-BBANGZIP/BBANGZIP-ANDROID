package org.android.bbangzip.presentation.ui.timer

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.model.TimerStatus
import org.android.bbangzip.presentation.util.base.BaseContract

class TimerContract {
    @Parcelize
    data class TimerState(
        val remainingTime: Long = 0L, // 타이머 시간
        val timerStatus: TimerStatus = TimerStatus.Idle,
        @DrawableRes val breadImg: Int = 0,  // 빵 이미지 리소스
        val todayBreadCount: Int = 0,// 오늘 구운 빵의 개수ㅡ
        val selectedTimeOptionIndex: Int = 0, // 타이머 시간변경 토글 인덱스,
        val isBreadSelectionSheetVisible: Boolean = false, // 빵 이미지 선택 BottomSheet 가시성
        val isRepeatSheetVisible: Boolean = false, // 초기화 버튼 클릭시 BottomSheet 가시성
        val isResetConfirmSheetVisible: Boolean = false, // 종료 버튼 클릭시 정말 끝내시겠습니까? BottomSheet 가시성
        val isCompleteSheetVisible: Boolean = false,// 타이머 완료시 BottomSheet 가시성
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface TimerEvent : BaseContract.Event {
        data object Initialize : TimerEvent
        data object OnStartBtnClick : TimerEvent
        data object OnStopBtnClick : TimerEvent
        data object OnRepeatBtnClick : TimerEvent
        data object OnResetBtnClick : TimerEvent
        data object OnBreadIconClick : TimerEvent
        data object OnTimeOptionToggleClick : TimerEvent
        data class OnBreadSelectionSheetClick(
            val breadId: String,
        ) : TimerEvent
        data object OnRepeatSheetDismissBtnClick : TimerEvent
        data object OnRepeatSheetApproveBtnClick : TimerEvent
        data object OnEndConfirmSheetDismissBtnClick : TimerEvent
        data object OnEndConfirmSheetApproveBtnClick : TimerEvent
        data object OnCompleteSheetRetryBtnClick : TimerEvent
        data object OnCompleteSheetCheckBtnClick : TimerEvent
        data object OnCompleteSheetDismissRequest : TimerEvent


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

        data class UpdateRepeatSheetState(
            val isRepeatSheetVisible: Boolean
        ) : TimerReduce

        data class UpdateResetConfirmSheetState(
            val isResetConfirmSheetVisible: Boolean
        ) : TimerReduce

        data class UpdateCompleteSheetState(
            val isCompleteSheetVisible: Boolean
        ) : TimerReduce
    }

    sealed interface TimerSideEffect : BaseContract.SideEffect {
        data object NavigateToCompleteTask : TimerSideEffect
    }
}