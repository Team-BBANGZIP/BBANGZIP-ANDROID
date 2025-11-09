package org.android.bbangzip.presentation.ui.profileedit

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileEditContract.ProfileEditEvent, ProfileEditContract.ProfileEditState, ProfileEditContract.ProfileEditReduce, ProfileEditContract.ProfileEditSideEffect>(
    savedStateHandle = savedStateHandle
) {
    override fun createInitialState(savedState: Parcelable?): ProfileEditContract.ProfileEditState {
        return savedState as? ProfileEditContract.ProfileEditState ?: ProfileEditContract.ProfileEditState()
    }

    override fun handleEvent(event: ProfileEditContract.ProfileEditEvent) {
        when (event) {

            else -> {}
        }
    }

    override fun reduceState(
        state: ProfileEditContract.ProfileEditState,
        reduce: ProfileEditContract.ProfileEditReduce
    ): ProfileEditContract.ProfileEditState {
        return when (reduce) {
            is ProfileEditContract.ProfileEditReduce.UpdateState -> reduce.state
        }
    }
}