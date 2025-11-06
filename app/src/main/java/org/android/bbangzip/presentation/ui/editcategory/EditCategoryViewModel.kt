package org.android.bbangzip.presentation.ui.editcategory

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.CategoryRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryEvent
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryReduce
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryReduce.UpdateCategoryNameInput
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryReduce.UpdateEditCategoryState
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryReduce.UpdateIsCategoryStopped
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryReduce.UpdateIsColorPickerBottomSheetVisible
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryReduce.UpdateIsConfirmEnable
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryReduce.UpdateSelectedColorString
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategorySideEffect
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryState
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
                    UpdateEditCategoryState(
                        currentUiState.copy(
                            categoryId = event.category.id,
                            categoryName = event.category.name,
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
                updateState(UpdateCategoryNameInput(event.categoryNameInput))
                updateState(UpdateIsConfirmEnable(isValidConfirm(event.categoryNameInput)))
            }

            is EditCategoryEvent.OnColorItemClick -> {
                updateState(UpdateSelectedColorString(event.colorString))
                updateState(UpdateIsColorPickerBottomSheetVisible(false))
            }

            EditCategoryEvent.OnColorPickerBottomSheetDismissRequest -> {
                updateState(UpdateIsColorPickerBottomSheetVisible(false))
            }

            EditCategoryEvent.OnColorSettingRowActionIconClick -> {
                updateState(UpdateIsColorPickerBottomSheetVisible(true))
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
                updateState(
                    UpdateEditCategoryState(
                        currentUiState.copy(
                            isDeleteConfirmationBottomSheetVisible = true,
                        ),
                    ),
                )
            }

            EditCategoryEvent.OnStopRowSwitchClick -> {
                updateState(UpdateIsCategoryStopped(!currentUiState.isCategoryStopped))
            }

            EditCategoryEvent.OnDeleteCancleButtonClick -> {
                updateState(
                    UpdateEditCategoryState(
                        currentUiState.copy(
                            isDeleteConfirmationBottomSheetVisible = false,
                        ),
                    ),
                )
            }

            EditCategoryEvent.OnDeleteConfirmButtonClick -> {
                viewModelScope.launch {
                    categoryRepository.deleteCategory(currentUiState.categoryId.toLong())
                        .onSuccess {
                            setSideEffect(EditCategorySideEffect.PopBackStack)
                        }.onFailure {
                            // TODO 에러처리
                            setSideEffect(EditCategorySideEffect.PopBackStack)
                        }
                }
            }

            EditCategoryEvent.OnDeleteConfirmationBottomSheetDismissRequest -> {
                updateState(
                    UpdateEditCategoryState(
                        currentUiState.copy(
                            isDeleteConfirmationBottomSheetVisible = false,
                        ),
                    ),
                )
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
