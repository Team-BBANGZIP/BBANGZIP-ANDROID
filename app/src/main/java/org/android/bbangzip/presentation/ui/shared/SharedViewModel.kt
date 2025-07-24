package org.android.bbangzip.presentation.ui.shared

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.ui.timer.TimerContract
import org.android.bbangzip.presentation.util.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel
@Inject
constructor(
savedStateHandle: SavedStateHandle
) : BaseViewModel<SharedContract.SharedEvent, SharedContract.SharedState, SharedContract.SharedReduce, SharedContract.SharedSideEffect>(
savedStateHandle = savedStateHandle
) {

    override fun createInitialState(savedState: Parcelable?): SharedContract.SharedState {
        return savedState as? SharedContract.SharedState ?: SharedContract.SharedState()    }

    override fun handleEvent(event: SharedContract.SharedEvent) {
        when (event) {
            is SharedContract.SharedEvent.OnClickBread -> {
                updateState(SharedContract.SharedReduce.SetBreadId(event.breadId))
            }
        }
    }

    override fun reduceState(state: SharedContract.SharedState, reduce: SharedContract.SharedReduce): SharedContract.SharedState {
        return when (reduce) {
            is SharedContract.SharedReduce.SetBreadId -> {
                state.copy(breadId = reduce.breadId)
            }
        }
    }
}