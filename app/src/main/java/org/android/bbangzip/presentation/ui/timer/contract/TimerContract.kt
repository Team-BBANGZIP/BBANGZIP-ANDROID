package org.android.bbangzip.presentation.ui.timer.contract

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.base.BaseContract
import org.android.bbangzip.presentation.common.util.extension.formatTime
import org.android.bbangzip.presentation.ui.timer.contract.model.BreadInfoUiState
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerBottomSheetVisibleState
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerConstants
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerSessionState
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerStatus
import org.android.bbangzip.presentation.ui.timer.contract.type.TimeOption


//        val isScreenOn: Boolean = true,
//        val isAppActive: Boolean = true,
//        val backgroundStartTime: Long = 0L,
//        val pausedTime: Long = 0L, -> 4개는 ui와 상관없으므류  viewmodel에 따로 선언

// 공통 : remainingTime , <enum> timerOption(timeOptionIndex , totalTime)
// ready ->  ,todayBreadCount  , breadList
//running, paused , resume : breadLevel - >breadImg
// timeOptionIndex -> totalTime, 반환해야하는 bread 개수

class TimerContract {
    @Parcelize
    data class TimerState(
        val remainingTime: Long = TimerConstants.THIRTY_MINUTES,
        val timerOption: TimeOption = TimeOption.THIRTY,
        val timerSessionState: TimerSessionState,
        val bottomSheetState: TimerBottomSheetVisibleState,
    ) : BaseContract.State, Parcelable {
        @IgnoredOnParcel
        val progress: Float by lazy {
            val totalTime = timerOption.totalTime
            if (totalTime > 0 && totalTime - remainingTime > 0) {
                ((totalTime - remainingTime).toFloat() / totalTime.toFloat())
            } else {
                0f
            }
        }

        @get:DrawableRes
        val breadImg: Int?
            get() {
                val level = when (val session = timerSessionState) {
                    is TimerSessionState.Running -> session.breadLevel
                    is TimerSessionState.Paused -> session.breadLevel
                    is TimerSessionState.Complete -> 4
                    else -> null
                }

                return level?.let {
                    when (it) {
                        1 -> R.drawable.img_baking_bread_level1
                        2 -> R.drawable.img_baking_bread_level2
                        3 -> R.drawable.img_baking_bread_level3
                        4 -> R.drawable.img_baking_bread_level4
                        else -> R.drawable.img_salt_bread // 기본값 또는 예외 케이스
                    }
                }
            }

        val formattedTime: String get() = remainingTime.formatTime()

        override fun toParcelable(): Parcelable = this
    }

    sealed interface TimerEvent : BaseContract.Event {

        sealed interface BottomSheetVisibleEvent : TimerEvent

        sealed interface BottomSheetDismissRequestEvent : TimerEvent

        sealed interface BottomSheetApproveBtnClickEvent : TimerEvent

        sealed interface BottomSheetDismissBtnClickEvent : TimerEvent


        data object Initialize : TimerEvent

        data object OnStartBtnClick : TimerEvent

        data object OnStopBtnClick : TimerEvent

        //Restart
        data object OnRestartBtnClick : TimerEvent, BottomSheetVisibleEvent

        data object OnRestartSheetDismissBtnClick : BottomSheetDismissBtnClickEvent

        data object OnRestartSheetApproveBtnClick : BottomSheetApproveBtnClickEvent

        //Reset
        data object OnResetBtnClick : TimerEvent, BottomSheetVisibleEvent

        data object OnResetSheetDismissBtnClick : TimerEvent ,BottomSheetDismissBtnClickEvent

        data object OnResetSheetApproveBtnClick : TimerEvent, BottomSheetApproveBtnClickEvent

        //BreadSelection
        data object OnBreadIconClick : TimerEvent, BottomSheetVisibleEvent

        data object OnBreadSelectionSheetClick : TimerEvent

        data object OnBreadSelectionSheetDismissRequest : TimerEvent, BottomSheetDismissRequestEvent

        // complete
        data object OnTimerCompleted : TimerEvent, BottomSheetVisibleEvent

        data object OnCompleteSheetRestartBtnClick : TimerEvent

        data object OnCompleteSheetCheckBtnClick : TimerEvent

        data object OnCompleteSheetDismissRequest : TimerEvent, BottomSheetDismissRequestEvent

        data class OnTimeOptionToggleClick(
            val selectedTimeOptionIndex: Int,
        ) : TimerEvent

        // Lifecycle events
        data object OnScreenTimeOut : TimerEvent

        data object OnLockButtonPressed : TimerEvent

        data object OnScreenTurnedOn : TimerEvent

        data object OnAppBackground : TimerEvent

        data class OnAppForeground(val exitDuration: Long) : TimerEvent
    }

    sealed interface TimerReduce : BaseContract.Reduce {
        data class UpdateTimerStatus(
            val timerStatus: TimerStatus,
        ) : TimerReduce

        data class UpdateRemainingTime(
            val remainingTime: Long,
        ) : TimerReduce

        data class UpdateBreadLevel(
            val breadLevel: Int,
        ) : TimerReduce

        data class UpdateTodayBreadCount(
            val todayBreadCount: Int,
        ) : TimerReduce

        data class UpdateSelectedTimeOptionIndex(
            val selectedTimeOptionIndex: Int,
        ) : TimerReduce

        data class UpdateBreadSelectionSheetState(
            val isBreadSelectionSheetVisible: Boolean,
        ) : TimerReduce

        data class UpdateRestartSheetState(
            val isRestartSheetVisible: Boolean,
        ) : TimerReduce

        data class UpdateResetSheetState(
            val isResetSheetVisible: Boolean,
        ) : TimerReduce

        data class UpdateCompleteSheetState(
            val isCompleteSheetVisible: Boolean,
        ) : TimerReduce

        data class UpdateTotalTime(
            val totalTime: Long,
        ) : TimerReduce

        data class UpdateBreadList(
            val breadList: List<BreadInfoUiState>,
        ) : TimerReduce

        data class UpdateIsScreenOn(
            val isScreenOn: Boolean,
        ) : TimerReduce

        data class UpdateIsAppActive(
            val isActive: Boolean,
        ) : TimerReduce

        data class UpdateBackgroundStartTime(
            val time: Long,
        ) : TimerReduce
    }

    sealed interface TimerSideEffect : BaseContract.SideEffect {
        data class NavigateToTimerTodo(val timeOptionIndex: Int) : TimerSideEffect
    }
}
