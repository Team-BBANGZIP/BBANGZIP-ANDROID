package org.android.bbangzip.presentation.ui.addcategory

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.ui.addcategory.AddCategoryContract.*
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel
    @Inject
    constructor(savedStateHandle: SavedStateHandle) : BaseViewModel<AddCategoryEvent, AddCategoryState, AddCategoryReduce, AddCategorySideEffect>(savedStateHandle) {
        override fun createInitialState(savedState: Parcelable?): AddCategoryState {
            return savedState as? AddCategoryState ?: AddCategoryState()
        }

        override fun handleEvent(event: AddCategoryEvent) {
            when (event) {
                is AddCategoryEvent.OnCategoryNameInputChange -> {
                    updateState(AddCategoryReduce.UpdatedCategoryNameInput(event.categoryNameInput))
                    updateState(AddCategoryReduce.UpdatedIsDoneEnable(isValidToComplete(event.categoryNameInput)))
                }
                is AddCategoryEvent.OnColorItemClick -> {
                    updateState(AddCategoryReduce.UpdatedSelectedColorString(event.colorString))
                    updateState(AddCategoryReduce.UpdatedIsColorPickerBottomSheetVisible(false))
                }
                AddCategoryEvent.OnColorPickerBottomSheetDismissRequest -> {
                    updateState(AddCategoryReduce.UpdatedIsColorPickerBottomSheetVisible(false))
                }
                AddCategoryEvent.OnColorSettingRowActionIconClick -> {
                    updateState(AddCategoryReduce.UpdatedIsColorPickerBottomSheetVisible(true))
                }
                AddCategoryEvent.OnTopBarLeadingIconClick -> {
                    setSideEffect(AddCategorySideEffect.PopBackStack)
                }
                AddCategoryEvent.OnTopBarTrailingIconClick -> {
                    setSideEffect(AddCategorySideEffect.PopBackStack)
                    // 정보 저장 후 원래 화면
                }
            }
        }

        override fun reduceState(
            state: AddCategoryState,
            reduce: AddCategoryReduce,
        ): AddCategoryState {
            when (reduce) {
                is AddCategoryReduce.UpdatedCategoryNameInput -> {
                    return state.copy(categoryNameInput = reduce.categoryNameInput)
                }
                is AddCategoryReduce.UpdatedIsColorPickerBottomSheetVisible -> {
                    return state.copy(isColorPickerBottomSheetVisible = reduce.isColorPickerBottomSheetVisible)
                }
                is AddCategoryReduce.UpdatedIsDoneEnable -> {
                    return state.copy(isDoneEnable = reduce.isDoneEnable)
                }
                is AddCategoryReduce.UpdatedSelectedColorString -> {
                    return state.copy(selectedColorString = reduce.selectedColorString)
                }
            }
        }

        private fun isValidToComplete(categoryNameInput: String): Boolean {
            return categoryNameInput.isNotEmpty()
        }
    }
