package org.android.bbangzip.presentation.ui.timer.todo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.ListItem
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoEvent
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce
import org.android.bbangzip.presentation.util.base.BaseContract
import java.time.LocalDate
import java.time.LocalTime

class TimerTodoContract {
    @Parcelize
    data class TimerTodoState(
        val categories: List<Category> = emptyList(),
        val flatList: List<ListItem> = emptyList(),
        val todayDate: LocalDate = LocalDate.now(),
        val isAddTodoBottomSheetVisible: Boolean = false,
        val isTimePickerBottomSheetVisible: Boolean = false,
        val todoText: String = "",
        val selectedCategory: Category? = null,
        val selectedStartTime: LocalTime? = null,
        ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface TimerTodoEvent : BaseContract.Event {
        data object Initialize : TimerTodoEvent
        data class OnTodoCheckBoxClick(val todoId: Int, val categoryId: Int, val isChecked: Boolean) : TimerTodoEvent
        data class OnCategoryChipClick(val category: Category) : TimerTodoEvent
        data class OnAddTodoDone(val category: Category?, val todoContent: String, val startTime: LocalTime?) : TimerTodoEvent
        data object OnAddTodoBottomSheetDismissRequest : TimerTodoEvent
        data class OnTodoTextChange(val todoText: String) : TimerTodoEvent
        data class OnTimeConfirmButtonClick(val startTime: LocalTime?) : TimerTodoEvent
        data object OnTimePickerBottomSheetDismissRequest : TimerTodoEvent
        data object OnTimePickerBottomSheetShowRequest : TimerTodoEvent
        data object OnRestartTimerBtnClick : TimerTodoEvent
        data object OnExitBtnClick : TimerTodoEvent
        data object OnBackIconClick : TimerTodoEvent
    }

    sealed interface TimerTodoReduce : BaseContract.Reduce {
        data class UpdateFlatList(val flatList: List<ListItem>) : TimerTodoReduce
        data class UpdateCategoriesAndFlatList(val categories: List<Category>, val flatList: List<ListItem>) : TimerTodoReduce
        data class UpdateAddTodoBottomSheetState(val isAddTodoBottomSheetVisible: Boolean) : TimerTodoReduce
        data class UpdateTimePickerBottomSheetState(val isTimePickerBottomSheetVisible: Boolean) : TimerTodoReduce
        data class UpdateTodoText(val todoText: String) : TimerTodoReduce
        data class UpdateSelectedStartTime(val startTime: LocalTime?) : TimerTodoReduce
        data class UpdateSelectedCategory(val category: Category?) : TimerTodoReduce
        data object ClearAddTodoState : TimerTodoReduce
    }

    sealed interface TimerTodoSideEffect : BaseContract.SideEffect {
        data class NavigateToTimer(val shouldRestart: Boolean) : TimerTodoSideEffect

        data object NavigateToBack : TimerTodoSideEffect
    }
}