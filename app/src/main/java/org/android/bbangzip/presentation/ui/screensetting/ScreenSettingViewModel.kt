package org.android.bbangzip.presentation.ui.screensetting

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ScreenSettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ScreenSettingContract.ScreenSettingEvent, ScreenSettingContract.ScreenSettingState, ScreenSettingContract.ScreenSettingReduce, ScreenSettingContract.ScreenSettingSideEffect>(
    savedStateHandle = savedStateHandle,
) {
    override fun createInitialState(savedState: Parcelable?): ScreenSettingContract.ScreenSettingState {
        return savedState as? ScreenSettingContract.ScreenSettingState
            ?: ScreenSettingContract.ScreenSettingState()
    }

    override fun handleEvent(event: ScreenSettingContract.ScreenSettingEvent) {
        when (event) {
            ScreenSettingContract.ScreenSettingEvent.Initialize -> {
                // 초기 데이터 로드 (필요 시)
            }

            ScreenSettingContract.ScreenSettingEvent.OnClickBack -> {
                setSideEffect(ScreenSettingContract.ScreenSettingSideEffect.NavigateBack)
            }

            ScreenSettingContract.ScreenSettingEvent.OnToggleSundayStart -> {
                val currentState = uiState.value
                updateState(
                    ScreenSettingContract.ScreenSettingReduce.UpdateSundayStartEnabled(
                        isEnabled = !currentState.isSundayStartEnabled
                    )
                )
            }
        }
    }

    override fun reduceState(
        state: ScreenSettingContract.ScreenSettingState,
        reduce: ScreenSettingContract.ScreenSettingReduce,
    ): ScreenSettingContract.ScreenSettingState {
        return when (reduce) {
            is ScreenSettingContract.ScreenSettingReduce.UpdateSundayStartEnabled ->
                state.copy(isSundayStartEnabled = reduce.isEnabled)
        }
    }
}
