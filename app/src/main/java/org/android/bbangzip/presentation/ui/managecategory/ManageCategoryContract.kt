package org.android.bbangzip.presentation.ui.managecategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract
import org.android.bbangzip.presentation.common.model.Category

class ManageCategoryContract {
    @Parcelize
    data class ManageCategoryState(
        val categories: List<Category> = emptyList()
    ) : Parcelable, BaseContract.State

    sealed interface ManageCategoryEvent : BaseContract.Event {
        data object Initialize : ManageCategoryEvent

        data class OnCategoryChipClick(val category: Category) : ManageCategoryEvent

        data object OnTopBarTrailingIconClick : ManageCategoryEvent

        data object OnTopBarLeadingIconClick : ManageCategoryEvent

        data class OnCategoryChipDragEnd(val from: Int, val to: Int) : ManageCategoryEvent
    }

    sealed interface ManageCategoryReduce : BaseContract.Reduce {
        data class UpdateCategories(val categories: List<Category>) : ManageCategoryReduce
    }

    sealed interface ManageCategorySideEffect : BaseContract.SideEffect {
        data object PopBackStack : ManageCategorySideEffect

        data object NavigateToAddCategory : ManageCategorySideEffect

        data class NavigateToEditCategory(val category: Category) : ManageCategorySideEffect
    }
}
