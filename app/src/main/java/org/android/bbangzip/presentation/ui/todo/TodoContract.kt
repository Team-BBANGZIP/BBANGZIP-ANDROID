package org.android.bbangzip.presentation.ui.todo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.Todo
import org.android.bbangzip.presentation.util.base.BaseContract

class TodoContract {
    @Parcelize
    data class TodoState(
        val isLoading: Boolean = false,
        val motivationMessage: String = "나만의 다짐을 적어보세요.",
        val categories: List<Category> = emptyList(),
        val error: String? = null
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this

        val totalTodoCount: Int
            get() = categories.sumOf { it.todos.size }

        val completedTodoCount: Int
            get() = categories.flatMap { it.todos }.count { it.isCompleted }
    }

    sealed interface TodoEvent : BaseContract.Event {
        data object Initialize : TodoEvent
        data class OnCategoriesChanged(val updatedCategories: List<Category>) : TodoEvent
        data class OnTodoCheckedChanged(val todoId: Int, val categoryId: Int, val isChecked: Boolean) : TodoEvent
    }

    sealed interface TodoReduce : BaseContract.Reduce {
        data class UpdateLoading(val isLoading: Boolean) : TodoReduce
        data class UpdateMotivationMessage(val message: String) : TodoReduce
        data class UpdateCategories(val categories: List<Category>) : TodoReduce
        data class UpdateError(val error: String?) : TodoReduce

        data class UpdateState(val newState: TodoState) : TodoReduce
    }

    sealed interface TodoSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(val message: String) : TodoSideEffect
    }
}
