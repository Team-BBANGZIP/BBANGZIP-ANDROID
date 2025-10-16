package org.android.bbangzip.presentation.ui.addcategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract

class AddCategoryContract {
    @Parcelize
    data class AddCategoryState(
        val categoryNameInput: String = "",
        val selectedColorString: String = "RED1",
        val isDoneEnable: Boolean = false,
        val isColorPickerBottomSheetVisible: Boolean = false,
    ): Parcelable, BaseContract.State

    sealed interface AddCategoryEvent: BaseContract.Event {
        data object OnColorSettingRowActionIconClick: AddCategoryEvent
        data object OnColorPickerBottomSheetDismissRequest: AddCategoryEvent
        data object OnTopBarLeadingIconClick: AddCategoryEvent
        data object OnTopBarTrailingIconClick: AddCategoryEvent
        data class OnCategoryNameInputChange(val categoryNameInput: String): AddCategoryEvent
        data class OnColorItemClick(val colorString: String): AddCategoryEvent
    }

    sealed interface AddCategoryReduce: BaseContract.Reduce {
        data class UpdatedCategoryNameInput(val categoryNameInput: String): AddCategoryReduce
        data class UpdatedSelectedColorString(val selectedColorString: String): AddCategoryReduce
        data class UpdatedIsDoneEnable(val isDoneEnable: Boolean): AddCategoryReduce
        data class UpdatedIsColorPickerBottomSheetVisible(val isColorPickerBottomSheetVisible: Boolean): AddCategoryReduce
    }

    sealed interface AddCategoryEffect: BaseContract.SideEffect
}