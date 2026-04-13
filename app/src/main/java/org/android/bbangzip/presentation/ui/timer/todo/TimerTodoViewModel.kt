package org.android.bbangzip.presentation.ui.timer.todo

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.TodoRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.common.model.ListItem
import org.android.bbangzip.presentation.common.model.Todo
import org.android.bbangzip.presentation.common.util.extension.toYyyyMmDdString
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoEvent
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.ClearAddTodoState
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.UpdateAddTodoBottomSheetState
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.UpdateCategoriesAndFlatList
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.UpdateFlatList
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.UpdateSelectedCategory
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.UpdateSelectedStartTime
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.UpdateTimePickerBottomSheetState
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoReduce.UpdateTodoText
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoSideEffect
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoSideEffect.NavigateToTimer
import org.android.bbangzip.presentation.ui.timer.todo.contract.TimerTodoContract.TimerTodoState
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TimerTodoViewModel
    @Inject
    constructor(
        private val todoRepository: TodoRepository,
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<TimerTodoEvent, TimerTodoState, TimerTodoReduce, TimerTodoSideEffect>(savedStateHandle) {
        override fun createInitialState(savedState: Parcelable?): TimerTodoState {
            return savedState as? TimerTodoState ?: TimerTodoState()
        }

        init {
            setEvent(TimerTodoEvent.Initialize)
        }

        override fun handleEvent(event: TimerTodoEvent) {
            when (event) {
                is TimerTodoEvent.Initialize -> {
                    launch {
                        getTodoList()
                    }
                }

                is TimerTodoEvent.OnTodoCheckBoxClick -> {
                    val updatedCategories =
                        currentUiState.categories.map { category ->
                            if (category.id == event.categoryId) {
                                category.copy(
                                    todos =
                                        category.todos.map { todo ->
                                            if (todo.todoId == event.todoId) {
                                                todo.copy(isCompleted = event.isChecked)
                                            } else {
                                                todo
                                            }
                                        },
                                )
                            } else {
                                category
                            }
                        }
                    updateCategoriesAndFlatList(updatedCategories)

                    postCheckedTodo()
                }

                is TimerTodoEvent.OnBackIconClick -> {
                    setSideEffect(TimerTodoSideEffect.NavigateToBack)
                }

                is TimerTodoEvent.OnExitBtnClick -> {
                    setSideEffect(NavigateToTimer(shouldRestart = false))
                }

                is TimerTodoEvent.OnRestartTimerBtnClick -> {
                    setSideEffect(NavigateToTimer(shouldRestart = true))
                }

                is TimerTodoEvent.OnCategoryChipClick -> {
                    updateState(UpdateSelectedCategory(event.category))
                    updateState(UpdateAddTodoBottomSheetState(isAddTodoBottomSheetVisible = true))
                }

                is TimerTodoEvent.OnAddTodoBottomSheetDismissRequest -> {
                    updateState(ClearAddTodoState)
                    updateState(UpdateAddTodoBottomSheetState(false))
                }

                is TimerTodoEvent.OnAddTodoDone -> {
                    if (event.todoContent.isNotBlank() && event.category != null) {
                        onTodoAdd(event.category.id, event.todoContent, event.startTime)
                        updateState(ClearAddTodoState)
                        updateState(UpdateAddTodoBottomSheetState(false))
                    }
                }

                is TimerTodoEvent.OnTimeConfirmButtonClick -> {
                    updateState(UpdateSelectedStartTime(event.startTime))
                    updateState(UpdateTimePickerBottomSheetState(false))
                }

                is TimerTodoEvent.OnTimePickerBottomSheetDismissRequest -> {
                    updateState(ClearAddTodoState)
                    updateState(UpdateTimePickerBottomSheetState(isTimePickerBottomSheetVisible = false))
                }

                is TimerTodoEvent.OnTimePickerBottomSheetShowRequest -> {
                    updateState(UpdateTimePickerBottomSheetState(isTimePickerBottomSheetVisible = true))
                }

                TimerTodoEvent.OnTimePickerBottomSheetClearButtonClick -> {
                    updateState(UpdateSelectedStartTime(null))
                    updateState(UpdateTimePickerBottomSheetState(isTimePickerBottomSheetVisible = false))
                }

                is TimerTodoEvent.OnTodoTextChange -> {
                    updateState(UpdateTodoText(event.todoText))
                }
            }
        }

        override fun reduceState(
            state: TimerTodoState,
            reduce: TimerTodoReduce,
        ): TimerTodoState {
            return when (reduce) {
                is TimerTodoReduce.UpdateTimerTodoState -> reduce.state
                is UpdateFlatList -> state.copy(flatList = reduce.flatList)
                is UpdateCategoriesAndFlatList -> state.copy(categories = reduce.categories, flatList = reduce.flatList)
                is UpdateAddTodoBottomSheetState -> state.copy(isAddTodoBottomSheetVisible = reduce.isAddTodoBottomSheetVisible)
                is UpdateTimePickerBottomSheetState -> state.copy(isTimePickerBottomSheetVisible = reduce.isTimePickerBottomSheetVisible)
                is ClearAddTodoState -> state.copy(todoText = "", selectedCategory = null, selectedStartTime = null)
                is UpdateSelectedStartTime -> state.copy(selectedStartTime = reduce.startTime)
                is UpdateTodoText -> state.copy(todoText = reduce.todoText)
                is UpdateSelectedCategory -> state.copy(selectedCategory = reduce.category)
            }
        }

        private fun updateCategoriesAndFlatList(categories: List<Category>) {
            val flatList = categories.toFlatList()
            updateState(UpdateCategoriesAndFlatList(categories, flatList))
        }

        private fun List<Category>.toFlatList(): List<ListItem> {
            return this.flatMap { category ->
                val categoryItem = ListItem.CategoryItem(category)
                val todoItems =
                    category.todos.mapIndexed { index, todo ->
                        ListItem.TodoItem(
                            todo = todo,
                            category = category,
                            isLastInCategory = index == category.todos.size - 1,
                        )
                    }
                listOf(categoryItem) + todoItems
            }
        }

    private fun getTodoList() {
        viewModelScope.launch {
            todoRepository
                .getTodoList(
                    date = LocalDate.now().toYyyyMmDdString(),
                )
                .onSuccess { data ->
                    val categoryList =
                        data.categories.map { category ->
                            Category(
                                id = category.categoryId,
                                name = category.categoryName,
                                color = category.categoryColor,
                                todos =
                                    category.todos.map { todo ->
                                        Todo(
                                            todoId = todo.todoId,
                                            content = todo.content,
                                            isCompleted = todo.isCompleted,
                                            startTime = todo.startTime,
                                        )
                                    },
                            )
                        }

                    updateState(
                        TimerTodoReduce.UpdateTimerTodoState(
                            currentUiState.copy(
                                categories = categoryList,
                                flatList = categoryList.toFlatList(),
                            )
                        ),
                    )
                }.onFailure { throwable ->
                    Timber.d("TimerTodoViewmodel 초기화 실패 $throwable")
                }
        }
    }


    fun onTodoAdd(
            categoryId: Int,
            todoContent: String,
            startTime: LocalTime?,
        ) {
            val newTodoId = (currentUiState.categories.flatMap { it.todos }.maxOfOrNull { it.todoId } ?: 0) + 1
            val newTodo =
                Todo(
                    todoId = newTodoId,
                    content = todoContent,
                    isCompleted = false,
                    startTime = startTime,
                )

            val updatedCategories =
                currentUiState.categories.map { category ->
                    if (category.id == categoryId) {
                        category.copy(todos = category.todos + newTodo)
                    } else {
                        category
                    }
                }
            updateCategoriesAndFlatList(updatedCategories)
        }

        private fun postCheckedTodo() {}
    }
