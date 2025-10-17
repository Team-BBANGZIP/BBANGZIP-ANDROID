package org.android.bbangzip.presentation.ui.managecategory

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryEvent
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryReduce
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategorySideEffect
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryState
import javax.inject.Inject

@HiltViewModel
class ManageCategoryViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<ManageCategoryEvent, ManageCategoryState, ManageCategoryReduce, ManageCategorySideEffect>(savedStateHandle) {
        override fun createInitialState(savedState: Parcelable?): ManageCategoryState {
            return savedState as? ManageCategoryState ?: ManageCategoryState()
        }

        init {
            setEvent(ManageCategoryEvent.Initialize)
        }

        override fun handleEvent(event: ManageCategoryEvent) {
            when (event) {
                ManageCategoryEvent.Initialize -> {
                    // 서버에서 카테고리 리스트 받아옴
                }
                ManageCategoryEvent.OnCategoryChipClick -> {
                    // 카테고리 수정 화면으로 이동
                    setSideEffect(ManageCategorySideEffect.NavigateToEditCategory)
                }
                is ManageCategoryEvent.OnCategoryChipDragEnd -> {
                    val reorderedList = reorderList(event.from, event.to)
                    updateState(ManageCategoryReduce.UpdateCategories(categories = reorderedList))
                }
                ManageCategoryEvent.OnTopBarLeadingIconClick -> {
                    setSideEffect(ManageCategorySideEffect.PopBackStack)
                }
                ManageCategoryEvent.OnTopBarTrailingIconClick -> {
                    setSideEffect(ManageCategorySideEffect.NavigateToAddCategory)
                    // 카테고리 추가 화면으로 이동
                }
            }
        }

        override fun reduceState(
            state: ManageCategoryState,
            reduce: ManageCategoryReduce,
        ): ManageCategoryState {
            when (reduce) {
                is ManageCategoryReduce.UpdateCategories -> {
                    return state.copy(
                        categories = reduce.categories,
                    )
                }
            }
        }

        private fun reorderList(
            from: Int,
            to: Int,
        ): List<Category> {
            val reorderableList = currentUiState.categories.toMutableList()
            reorderableList.add(to, reorderableList.removeAt(from))
            return reorderableList.toList()
        }
    }
