package org.android.bbangzip.presentation.ui.timer.contract

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.base.BaseContract
import org.android.bbangzip.presentation.common.util.extension.formatTime
import org.android.bbangzip.presentation.ui.timer.contract.model.BreadInfoUiState
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerBottomSheetVisibleState
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerSessionUiState
import org.android.bbangzip.presentation.ui.timer.contract.type.TimeOption
import org.android.bbangzip.presentation.ui.timer.util.TimerConstants

class TimerContract {
    //TODO 추후에 Room에 breadList 저장해놓기 SB
    @Immutable
    @Parcelize
    data class TimerState(
        val remainingTime: Long = TimerConstants.THIRTY_MINUTES,
        val timerOption: TimeOption = TimeOption.THIRTY,
        val timerSessionState: TimerSessionUiState = TimerSessionUiState.Ready,
        val bottomSheetState: TimerBottomSheetVisibleState = TimerBottomSheetVisibleState(),
        val breadList: ImmutableList<BreadInfoUiState> = emptyList<BreadInfoUiState>().toImmutableList(),
        val totalBreadCount: Int = 0,
        val todayBreadCount: Int = 0,
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

        @IgnoredOnParcel
        @get:DrawableRes
        val breadImg: Int
            get() =
                when {
                    progress >= 0.75f -> R.drawable.img_baking_bread_level4
                    progress >= 0.5f -> R.drawable.img_baking_bread_level3
                    progress >= 0.25f -> R.drawable.img_baking_bread_level2
                    else -> R.drawable.img_baking_bread_level1
                }

        @IgnoredOnParcel
        val formattedTime: String get() = remainingTime.formatTime()

        override fun toParcelable(): Parcelable = this
    }

    sealed interface TimerEvent : BaseContract.Event {
        data object Initialize : TimerEvent

        data object OnStartBtnClick : TimerEvent

        data object OnStopBtnClick : TimerEvent

        // Restart
        data object OnRestartBtnClick : TimerEvent

        data object OnRestartSheetDismissBtnClick : TimerEvent

        data object OnRestartSheetApproveBtnClick : TimerEvent

        // Reset
        data object OnResetBtnClick : TimerEvent

        data object OnResetSheetDismissBtnClick : TimerEvent

        data object OnResetSheetApproveBtnClick : TimerEvent

        // BreadSelection
        data object OnBreadIconClick : TimerEvent

        data object OnBreadSelectionSheetClick : TimerEvent

        data object OnBreadSelectionSheetDismissRequest : TimerEvent

        // complete
        data object OnCompleteSheetRestartBtnClick : TimerEvent

        data object OnCompleteSheetCheckBtnClick : TimerEvent

        data object OnCompleteSheetDismissRequest : TimerEvent

        data class OnTimeOptionToggleClick(
            val selectedTimeOptionIndex: Int,
        ) : TimerEvent
    }

    sealed interface TimerReduce : BaseContract.Reduce {
        data class UpdateTimerState(val timerState : TimerState) : TimerReduce

        data class UpdateRemainingTime(val remainingTime: Long) : TimerReduce

        data class UpdateTimerSessionState(val sessionState: TimerSessionUiState) : TimerReduce

        data class UpdateBottomSheetState(val bottomSheetState: TimerBottomSheetVisibleState) : TimerReduce

        data class UpdateTimeOption(val option: TimeOption) : TimerReduce

        data class UpdateTodayBreadCount(val breadCount: Int) : TimerReduce
    }

    sealed interface TimerSideEffect : BaseContract.SideEffect {
        data class NavigateToTimerTodo(val timeOptionIndex: Int) : TimerSideEffect
    }
}
