package org.android.bbangzip.presentation.ui.todo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.ListItem
import org.android.bbangzip.presentation.util.base.BaseContract
import java.time.LocalDate
import java.time.LocalTime

class TodoContract {
    @Parcelize
    data class TodoState(
        val categories: List<Category> = emptyList(),
        val flatList: List<ListItem> = emptyList(),
        val motivationMessage: String = "나만의 다짐을 적어보세요.",
        val selectedDate: LocalDate = LocalDate.now(),
        val isMenuOpen: Boolean = false,
        val isCommitmentBottomSheetVisible: Boolean = false,
        val isAddTodoBottomSheetVisible: Boolean = false,
        val isTimePickerBottomSheetVisible: Boolean = false,
        val todoText: String = "",
        val selectedCategory: Category? = null,
        val selectedStartTime: LocalTime? = null,
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this

        val totalTodoCount: Int
            get() = categories.sumOf { it.todos.size }

        val completedTodoCount: Int
            get() = categories.flatMap { it.todos }.count { it.isCompleted }
    }

    sealed interface TodoEvent : BaseContract.Event {
        data object Initialize : TodoEvent

        data object OnCommitmentAreaClick : TodoEvent

        data object OnMenuClick : TodoEvent

        data class OnTodoCheckBoxClick(val todoId: Int, val categoryId: Int, val isChecked: Boolean) : TodoEvent

        data object OnDateChanged : TodoEvent

        data object OnAddCategoryClick : TodoEvent

        data object OnManageCategoryClick : TodoEvent

        data class OnListItemMove(val from: Int, val to: Int) : TodoEvent

        data class OnAddTodoDone(val category: Category?, val todoContent: String, val startTime: LocalTime?) : TodoEvent

        data class OnTimeConfirmButtonClick(val startTime: LocalTime?) : TodoEvent

        data object OnTimePickerBottomSheetDismissRequest : TodoEvent

        data object OnAddTodoBottomSheetDismissRequest : TodoEvent

        data object OnTimePickerBottomSheetShowRequest : TodoEvent

        data class OnTodoTextChange(val todoText: String) : TodoEvent

        data class OnCategoryChipClick(val category: Category) : TodoEvent
    }

    sealed interface TodoReduce : BaseContract.Reduce {
        data class UpdateMotivationMessage(val message: String) : TodoReduce

        data class UpdateCategories(val categories: List<Category>) : TodoReduce

        data class UpdateFlatList(val flatList: List<ListItem>) : TodoReduce

        data class UpdateCategoriesAndFlatList(
            val categories: List<Category>,
            val flatList: List<ListItem>,
        ) : TodoReduce

        data class UpdateSelectedDate(val selectedDate: LocalDate) : TodoReduce

        data class UpdateIsMenuOpen(val isMenuOpen: Boolean) : TodoReduce

        data class UpdateIsTimePickerBottomSheetVisible(val isVisible: Boolean) : TodoReduce

        data class UpdateIsCommitmentBottomSheetVisible(val isVisible: Boolean) : TodoReduce

        data class UpdateIsAddTodoBottomSheetVisible(val isVisible: Boolean) : TodoReduce

        data class UpdateTodoText(val todoText: String) : TodoReduce

        data class UpdateSelectedCategory(val category: Category?) : TodoReduce

        data class UpdateSelectedStartTime(val startTime: LocalTime?) : TodoReduce

        data object ClearAddTodoState : TodoReduce
    }

    sealed interface TodoSideEffect : BaseContract.SideEffect
}
