package org.android.bbangzip.presentation.ui.editcategory

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.CategoryRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.*
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val categoryRepository: CategoryRepository,
    ) : BaseViewModel<EditCategoryEvent, EditCategoryState, EditCategoryReduce, EditCategorySideEffect>(savedStateHandle) {
        override fun createInitialState(savedState: Parcelable?): EditCategoryState {
            return savedState as? EditCategoryState ?: EditCategoryState()
        }

        override fun handleEvent(event: EditCategoryEvent) {
            when (event) {
                is EditCategoryEvent.Initialize -> {
                    updateState(
                        EditCategoryReduce.UpdateEditCategoryState(
                            currentUiState.copy(
                                categoryId = event.category.id,
                                categoryNameInput = event.category.name,
                                selectedColorString = event.category.color,
                                isConfirmEnable = true,
                                isCategoryStopped = event.category.isStopped,
                            ),
                        ),
                    )
                }
                EditCategoryEvent.OnBackIconClick -> {
                    setSideEffect(EditCategorySideEffect.PopBackStack)
                }
                is EditCategoryEvent.OnCategoryNameInputChange -> {
                    updateState(EditCategoryReduce.UpdateCategoryNameInput(event.categoryNameInput))
                    updateState(EditCategoryReduce.UpdateIsConfirmEnable(isValidConfirm(event.categoryNameInput)))
                }
                is EditCategoryEvent.OnColorItemClick -> {
                    updateState(EditCategoryReduce.UpdateSelectedColorString(event.colorString))
                    updateState(EditCategoryReduce.UpdateIsColorPickerBottomSheetVisible(false))
                }
                EditCategoryEvent.OnColorPickerBottomSheetDismissRequest -> {
                    updateState(EditCategoryReduce.UpdateIsColorPickerBottomSheetVisible(false))
                }
                EditCategoryEvent.OnColorSettingRowActionIconClick -> {
                    updateState(EditCategoryReduce.UpdateIsColorPickerBottomSheetVisible(true))
                }
                EditCategoryEvent.OnConfirmButtonClick -> {
                    viewModelScope.launch {
                        categoryRepository.modifyCategory(
                            categoryId = currentUiState.categoryId.toLong(),
                            name = currentUiState.categoryNameInput,
                            color = currentUiState.selectedColorString,
                            isStopped = currentUiState.isCategoryStopped,
                        ).onSuccess {
                            setSideEffect(EditCategorySideEffect.PopBackStack)
                        }.onFailure {
                            // TODO 에러처리
                            setSideEffect(EditCategorySideEffect.PopBackStack)
                        }
                    }
                }
                EditCategoryEvent.OnDeleteButtonClick -> {
                    // 삭제 api
                }
                EditCategoryEvent.OnStopRowSwitchClick -> {
                    updateState(EditCategoryReduce.UpdateIsCategoryStopped(!currentUiState.isCategoryStopped))
                }
            }
        }

        override fun reduceState(
            state: EditCategoryState,
            reduce: EditCategoryReduce,
        ): EditCategoryState {
            when (reduce) {
                is EditCategoryReduce.UpdateEditCategoryState -> {
                    return reduce.editCategoryState
                }
                is EditCategoryReduce.UpdateCategoryNameInput -> {
                    return state.copy(categoryNameInput = reduce.categoryNameInput)
                }
                is EditCategoryReduce.UpdateIsCategoryStopped -> {
                    return state.copy(isCategoryStopped = reduce.isCategoryStopped)
                }
                is EditCategoryReduce.UpdateIsColorPickerBottomSheetVisible -> {
                    return state.copy(isColorPickerBottomSheetVisible = reduce.isColorPickerBottomSheetVisible)
                }
                is EditCategoryReduce.UpdateIsConfirmEnable -> {
                    return state.copy(isConfirmEnable = reduce.isConfirmEnable)
                }
                is EditCategoryReduce.UpdateSelectedColorString -> {
                    return state.copy(selectedColorString = reduce.selectedColorString)
                }
            }
        }

        private fun isValidConfirm(categoryNameInput: String): Boolean {
            return categoryNameInput.isNotEmpty()
        }
    }
