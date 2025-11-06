package org.android.bbangzip.presentation.ui.shared

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.ui.shared.SharedContract.SharedEvent
import org.android.bbangzip.presentation.ui.shared.SharedContract.SharedReduce
import org.android.bbangzip.presentation.ui.shared.SharedContract.SharedSideEffect
import org.android.bbangzip.presentation.ui.shared.SharedContract.SharedState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SharedEvent, SharedState, SharedReduce, SharedSideEffect>(
    savedStateHandle = savedStateHandle,
) {
    override fun createInitialState(savedState: Parcelable?): SharedState {
        return savedState as? SharedState ?: SharedState()
    }

    override fun handleEvent(event: SharedEvent) {
        when (event) {
            is SharedEvent.OnClickBread -> {
                updateState(SharedReduce.SetBreadId(event.breadId))
            }

            SharedEvent.OnHideBottomBar -> {
                updateState(SharedReduce.UpdateBottomBarVisibility(false))
            }

            SharedEvent.OnShowBottomBar -> {
                updateState(SharedReduce.UpdateBottomBarVisibility(true))
            }
        }
    }

    override fun reduceState(
        state: SharedState,
        reduce: SharedReduce,
    ): SharedState {
        return when (reduce) {
            is SharedReduce.SetBreadId -> {
                state.copy(breadId = reduce.breadId)
            }

            is SharedReduce.UpdateBottomBarVisibility -> {
                state.copy(isBottomBarVisible = reduce.isVisible)
            }
        }
    }
}
