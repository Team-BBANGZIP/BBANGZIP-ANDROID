package org.android.bbangzip.presentation.ui.timer.todo

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.common.model.ListItem
import org.android.bbangzip.presentation.common.model.Todo
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
import org.android.bbangzip.presentation.common.base.BaseViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TimerTodoViewModel
    @Inject
    constructor(
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
                        val exampleCategories = getExampleList()
                        updateCategoriesAndFlatList(exampleCategories)
                    }
                }

                is TimerTodoEvent.OnTodoCheckBoxClick -> {
                    val updatedCategories =
                        currentUiState.categories.map { category ->
                            if (category.categoryId == event.categoryId) {
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
                        onTodoAdd(event.category.categoryId, event.todoContent, event.startTime)
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

        private fun getExampleList(): List<Category> {
            return listOf(
                Category(
                    categoryId = 1,
                    categoryName = "제과제빵점",
                    categoryColor = "RED1",
                    todos =
                        listOf(
                            Todo(
                                todoId = 11,
                                content = "두줄 \n 두줄",
                                isCompleted = true,
                                startTime = LocalTime.of(11, 0),
                            ),
                            Todo(
                                todoId = 12,
                                content = "제과제빵점_한줄_실패",
                                isCompleted = false,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 13,
                                content = "제과제빵점_한줄_완료",
                                isCompleted = true,
                                startTime = null,
                            ),
                        ),
                ),
                Category(
                    categoryId = 2,
                    categoryName = "경제학개론",
                    categoryColor = "YELLOW1",
                    todos =
                        listOf(
                            Todo(
                                todoId = 21,
                                content = "경제학개론_한줄_완료",
                                isCompleted = true,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 22,
                                content = "경제학개론 \n 두줄_실패",
                                isCompleted = false,
                                startTime = LocalTime.of(11, 0),
                            ),
                        ),
                ),
                Category(
                    categoryId = 3,
                    categoryName = "운동",
                    categoryColor = "GREEN1",
                    todos =
                        listOf(
                            Todo(
                                todoId = 31,
                                content = "헬스장 가기",
                                isCompleted = false,
                                startTime = LocalTime.of(18, 0),
                            ),
                            Todo(
                                todoId = 32,
                                content = "저녁 유산소 30분",
                                isCompleted = true,
                                startTime = LocalTime.of(19, 30),
                            ),
                        ),
                ),
                Category(
                    categoryId = 4,
                    categoryName = "스터디",
                    categoryColor = "BLUE1",
                    todos =
                        listOf(
                            Todo(
                                todoId = 41,
                                content = "알고리즘 문제 풀이",
                                isCompleted = true,
                                startTime = LocalTime.of(20, 0),
                            ),
                            Todo(
                                todoId = 42,
                                content = "코틀린 스터디 준비",
                                isCompleted = false,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 43,
                                content = "CS 스터디 복습",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 0),
                            ),
                        ),
                ),
                Category(
                    categoryId = 5,
                    categoryName = "개인 프로젝트",
                    categoryColor = "PURPLE1",
                    todos =
                        listOf(
                            Todo(
                                todoId = 51,
                                content = "UI 디자인 검토",
                                isCompleted = false,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 52,
                                content = "백엔드 API 연동",
                                isCompleted = false,
                                startTime = LocalTime.of(14, 0),
                            ),
                        ),
                ),
                Category(
                    categoryId = 6,
                    categoryName = "새로운 카테고리",
                    categoryColor = "RED2",
                    todos =
                        listOf(
                            Todo(
                                todoId = 61,
                                content = "새로운 할 일 1",
                                isCompleted = false,
                                startTime = LocalTime.of(9, 0),
                            ),
                            Todo(
                                todoId = 62,
                                content = "새로운 할 일 2",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 30),
                            ),
                        ),
                ),
                Category(
                    categoryId = 7,
                    categoryName = "영화",
                    categoryColor = "YELLOW2",
                    todos =
                        listOf(
                            Todo(
                                todoId = 71,
                                content = "귀멸의 칼날",
                                isCompleted = false,
                                startTime = LocalTime.of(9, 0),
                            ),
                            Todo(
                                todoId = 72,
                                content = "좀비딸",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 30),
                            ),
                            Todo(
                                todoId = 73,
                                content = "F1",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 30),
                            ),
                        ),
                ),
            )
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
                    if (category.categoryId == categoryId) {
                        category.copy(todos = category.todos + newTodo)
                    } else {
                        category
                    }
                }
            updateCategoriesAndFlatList(updatedCategories)
        }

        private fun postCheckedTodo() {}
    }
