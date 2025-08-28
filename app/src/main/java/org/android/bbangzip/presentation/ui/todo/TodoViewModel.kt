package org.android.bbangzip.presentation.ui.todo

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.Todo
import org.android.bbangzip.presentation.ui.todo.TodoContract.*
import org.android.bbangzip.presentation.util.base.BaseViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<TodoEvent, TodoState, TodoReduce, TodoSideEffect>(savedStateHandle) {
        override fun createInitialState(savedState: Parcelable?): TodoState {
            return savedState as? TodoState ?: TodoState()
        }

        init {
            setEvent(TodoEvent.Initialize)
        }

        override fun handleEvent(event: TodoEvent) {
            when (event) {
                is TodoEvent.Initialize -> {
                    updateState(TodoReduce.UpdateLoading(isLoading = true))
                    viewModelScope.launch {
                        val exampleCategories = getExampleList()
                        updateState(TodoReduce.UpdateCategories(exampleCategories))
                        updateState(TodoReduce.UpdateLoading(isLoading = false))
                    }
                }

                is TodoEvent.OnCategoriesChanged -> {
                    updateState(TodoReduce.UpdateCategories(event.updatedCategories))
                }

                is TodoEvent.OnTodoCheckedChanged -> {
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
                    updateState(TodoReduce.UpdateCategories(updatedCategories))
                }
            }
        }

        override fun reduceState(
            state: TodoState,
            reduce: TodoReduce,
        ): TodoState {
            return when (reduce) {
                is TodoReduce.UpdateLoading -> state.copy(isLoading = reduce.isLoading)
                is TodoReduce.UpdateMotivationMessage ->
                    state.copy(
                        motivationMessage = reduce.message,
                    )
                is TodoReduce.UpdateCategories -> state.copy(categories = reduce.categories)
                is TodoReduce.UpdateError -> state.copy(error = reduce.error)
                is TodoReduce.UpdateState -> reduce.newState
            }
        }

        private fun getExampleList(): List<Category> {
            return listOf(
                Category(
                    categoryId = 1,
                    categoryName = "제과제빵점",
                    categoryColor = "Red",
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
                    categoryColor = "Blue",
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
                    categoryColor = "Green",
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
                    categoryColor = "Yellow",
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
                    categoryColor = "Red",
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
                    categoryColor = "Blue",
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
                    categoryColor = "Red",
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
    }
