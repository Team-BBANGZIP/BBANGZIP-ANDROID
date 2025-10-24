package org.android.bbangzip.presentation.ui.editcategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryEvent

class EditCategoryContract {
    @Parcelize
    data class EditCategoryState(
        val categoryId: Int = 0,
        val categoryName: String = "",
        val categoryNameInput: String = "",
        val isConfirmEnable: Boolean = false,
        val isColorPickerBottomSheetVisible: Boolean = false,
        val selectedColorString: String = "RED1",
        val isCategoryStopped: Boolean = false,
        val isDeleteConfirmationBottomSheetVisible: Boolean = false,
    ) : Parcelable, BaseContract.State

    sealed interface EditCategoryEvent : BaseContract.Event {
        data class Initialize(val category: Category) : EditCategoryEvent

        data object OnBackIconClick : EditCategoryEvent

        data object OnConfirmButtonClick : EditCategoryEvent

        data object OnDeleteButtonClick : EditCategoryEvent

        data object OnColorSettingRowActionIconClick : EditCategoryEvent

        data object OnColorPickerBottomSheetDismissRequest : EditCategoryEvent

        data object OnStopRowSwitchClick : EditCategoryEvent

        data class OnColorItemClick(val colorString: String) : EditCategoryEvent

        data class OnCategoryNameInputChange(val categoryNameInput: String) : EditCategoryEvent

        data object OnDeleteCancleButtonClick : EditCategoryEvent

        data object OnDeleteConfirmButtonClick : EditCategoryEvent

        data object OnDeleteConfirmationBottomSheetDismissRequest : EditCategoryEvent
    }

    sealed interface EditCategoryReduce : BaseContract.Reduce {
        data class UpdateEditCategoryState(val editCategoryState: EditCategoryState) : EditCategoryReduce

        data class UpdateCategoryNameInput(val categoryNameInput: String) : EditCategoryReduce

        data class UpdateIsConfirmEnable(val isConfirmEnable: Boolean) : EditCategoryReduce

        data class UpdateIsColorPickerBottomSheetVisible(val isColorPickerBottomSheetVisible: Boolean) : EditCategoryReduce

        data class UpdateSelectedColorString(val selectedColorString: String) : EditCategoryReduce

        data class UpdateIsCategoryStopped(val isCategoryStopped: Boolean) : EditCategoryReduce
    }

    sealed interface EditCategorySideEffect : BaseContract.SideEffect {
        data object PopBackStack : EditCategorySideEffect
    }
}
