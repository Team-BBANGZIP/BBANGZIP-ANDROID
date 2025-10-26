package org.android.bbangzip.presentation.ui.managecategory

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.CategoryRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryEvent
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryReduce
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategorySideEffect
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManageCategoryViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val categoryRepository: CategoryRepository,
    ) : BaseViewModel<ManageCategoryEvent, ManageCategoryState, ManageCategoryReduce, ManageCategorySideEffect>(savedStateHandle) {
        override fun createInitialState(savedState: Parcelable?): ManageCategoryState {
            return savedState as? ManageCategoryState ?: ManageCategoryState()
        }

        override fun handleEvent(event: ManageCategoryEvent) {
            when (event) {
                ManageCategoryEvent.Initialize -> {
                    viewModelScope.launch {
                        categoryRepository.getCategories()
                            .onSuccess { data ->
                                updateState(
                                    ManageCategoryReduce.UpdateCategories(
                                        categories =
                                            data.map {
                                                Category(
                                                    id = it.categoryId,
                                                    name = it.categoryName,
                                                    color = it.categoryColor,
                                                    isStopped = it.isStopped,
                                                )
                                            },
                                    ),
                                )
                            }.onFailure {
                                // TODO: 에러 처리
                            }
                    }
                }
                is ManageCategoryEvent.OnCategoryChipClick -> {
                    setSideEffect(ManageCategorySideEffect.NavigateToEditCategory(event.category))
                }
                is ManageCategoryEvent.OnCategoryChipDragEnd -> {
                    val reorderedList = reorderList(event.from, event.to)
                    updateState(ManageCategoryReduce.UpdateCategories(categories = reorderedList))
                    viewModelScope.launch {
                        categoryRepository.reorderCategories(
                            categoryOrder = reorderedList.map { it.id.toLong() },
                        ).onSuccess {
                            Timber.d("reorderCategories 성공")
                        }.onFailure {
                            Timber.d("reorderCategories 실패")
                        }
                    }
                }
                ManageCategoryEvent.OnTopBarLeadingIconClick -> {
                    setSideEffect(ManageCategorySideEffect.PopBackStack)
                }
                ManageCategoryEvent.OnTopBarTrailingIconClick -> {
                    setSideEffect(ManageCategorySideEffect.NavigateToAddCategory)
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
