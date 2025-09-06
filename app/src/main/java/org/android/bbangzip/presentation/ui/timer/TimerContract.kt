package org.android.bbangzip.presentation.ui.timer

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.model.BreadInfo
import org.android.bbangzip.presentation.model.TimerStatus
import org.android.bbangzip.presentation.util.base.BaseContract
import org.android.bbangzip.presentation.util.constant.TimerConstants
import org.android.bbangzip.presentation.util.extension.formatTime

class TimerContract {
    @Parcelize
    data class TimerState(
        val remainingTime: Long = TimerConstants.THIRTY_MINUTES,
        val totalTime: Long = TimerConstants.THIRTY_MINUTES,
        val pausedTime: Long = 0L,
        val timerStatus: TimerStatus = TimerStatus.Idle,
        val breadLevel: Int = 1,
        val todayBreadCount: Int = 6,
        val selectedTimeOptionIndex: Int = 0,
        val breadList: List<BreadInfo> =
            listOf(
                BreadInfo(1, "소금빵", false, 0),
                BreadInfo(2, "식빵", false, 5),
                BreadInfo(3, "바게트", true, 10),
                BreadInfo(4, "크루아상", true, 15),
                BreadInfo(5, "모닝빵", true, 20),
                BreadInfo(6, "바게트", true, 10),
                BreadInfo(7, "크루아상", true, 15),
                BreadInfo(8, "모닝빵", true, 20),
                BreadInfo(9, "모닝빵", true, 20),
            ),
        val isBreadSelectionSheetVisible: Boolean = false,
        val isRestartSheetVisible: Boolean = false,
        val isResetSheetVisible: Boolean = false,
        val isCompleteSheetVisible: Boolean = false,
        // Lifecycle State
        val isScreenOn: Boolean = true,
        val isAppActive: Boolean = true,
        val backgroundStartTime: Long = 0L,
    ) : BaseContract.State, Parcelable {
        val formattedTime: String get() = remainingTime.formatTime()
        val progress: Float get() = ((totalTime - remainingTime).toFloat() / totalTime.toFloat()) * 100f
        val breadImg: Int
            @DrawableRes get() =
                when (breadLevel) {
                    1 -> R.drawable.img_baking_bread_level1
                    2 -> R.drawable.img_baking_bread_level2
                    3 -> R.drawable.img_baking_bread_level3
                    4 -> R.drawable.img_baking_bread_level4
                    else -> R.drawable.img_salt_bread
                }

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

        data object OnCompleteSheetRestartBtnClick : TimerEvent

        data object OnCompleteSheetCheckBtnClick : TimerEvent

        data object OnCompleteSheetDismissRequest : TimerEvent

        data object OnTimerTick : TimerEvent

        data class OnTimeOptionToggleClick(
            val selectedTimeOptionIndex: Int,
        ) : TimerEvent

        data object OnBreadSelectionSheetClick : TimerEvent

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
            val breadList: List<BreadInfo>,
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
        data class NavigateToTimerTodo(val timeOptionIndex : Int) : TimerSideEffect
    }
}
