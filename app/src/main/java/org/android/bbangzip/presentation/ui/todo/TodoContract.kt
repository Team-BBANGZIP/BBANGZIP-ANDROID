package org.android.bbangzip.presentation.ui.todo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.ui.todo.model.ListItem
import org.android.bbangzip.presentation.util.base.BaseContract
import java.time.LocalDate

class TodoContract {
    @Parcelize
    data class TodoState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val categories: List<Category> = emptyList(),
        val flatList: List<ListItem> = emptyList(),
        val motivationMessage: String = "나만의 다짐을 적어보세요.",
        val selectedDate: LocalDate = LocalDate.now(),
        val isMenuOpen: Boolean = false,
        val isCommitmentBottomSheetVisible: Boolean = false,
        val isAddTodoBottomSheetVisible: Boolean = false,
        val isTimePickerBottomSheetVisible: Boolean = false,
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

        data object OnCategoryChipClick : TodoEvent

        data class OnTodoCheckBoxClick(val todoId: Int, val categoryId: Int, val isChecked: Boolean) : TodoEvent

        data object OnDateChanged : TodoEvent

        data object OnAddCategoryClick : TodoEvent

        data object OnManageCategoryClick : TodoEvent

        data class OnListItemMove(val from: Int, val to: Int) : TodoEvent
    }

    sealed interface TodoReduce : BaseContract.Reduce {
        data class UpdateLoading(val isLoading: Boolean) : TodoReduce

        data class UpdateMotivationMessage(val message: String) : TodoReduce

        data class UpdateCategories(val categories: List<Category>) : TodoReduce

        data class UpdateFlatList(val flatList: List<ListItem>) : TodoReduce

        data class UpdateError(val error: String?) : TodoReduce

        data class UpdateSelectedDate(val selectedDate: LocalDate) : TodoReduce

        data class UpdateIsMenuOpen(val isMenuOpen: Boolean) : TodoReduce

        data class UpdateState(val newState: TodoState) : TodoReduce
    }

    sealed interface TodoSideEffect : BaseContract.SideEffect
}
