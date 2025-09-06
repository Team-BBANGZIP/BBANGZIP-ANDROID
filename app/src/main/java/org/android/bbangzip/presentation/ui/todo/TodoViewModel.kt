package org.android.bbangzip.presentation.ui.todo

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.ListItem
import org.android.bbangzip.presentation.model.Todo
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoEvent
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateCategories
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateCategoriesAndFlatList
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateFlatList
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateIsMenuOpen
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateSelectedDate
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoSideEffect
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoState
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
                    launch {
                        val exampleCategories = getExampleList()
                        updateCategoriesAndFlatList(exampleCategories)
                    }
                }

                is TodoEvent.OnTodoCheckBoxClick -> {
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
                }

                is TodoEvent.OnListItemMove -> {
                    val from = event.from
                    val to = event.to

                    if (from == to) return

                    val currentFlatList = currentUiState.flatList.toMutableList()
                    val movedItem = currentFlatList.removeAt(from)
                    currentFlatList.add(to, movedItem)

                    synchronizeListState(currentFlatList)
                    val newCategories = reconstructCategoriesFromFlatList(currentFlatList)

                    updateState(UpdateCategoriesAndFlatList(newCategories, currentFlatList.toList()))
                }

                is TodoEvent.OnMenuClick -> {
                    updateState(UpdateIsMenuOpen(isMenuOpen = !currentUiState.isMenuOpen))
                }

                is TodoEvent.OnAddTodoDone -> {
                    if (event.todoContent.isNotBlank() && event.category != null) {
                        onTodoAdd(event.category.categoryId, event.todoContent, event.startTime)
                        updateState(TodoReduce.ClearAddTodoState)
                        updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(false))
                    }
                }

                is TodoEvent.OnTimeConfirmButtonClick -> {
                    updateState(TodoReduce.UpdateSelectedStartTime(event.startTime))
                    updateState(TodoReduce.UpdateIsTimePickerBottomSheetVisible(false))
                }

                TodoEvent.OnTimePickerBottomSheetDismissRequest -> {
                    updateState(TodoReduce.UpdateIsTimePickerBottomSheetVisible(false))
                }
                TodoEvent.OnAddTodoBottomSheetDismissRequest -> {
                    updateState(TodoReduce.ClearAddTodoState)
                    updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(false))
                }

                TodoEvent.OnTimePickerBottomSheetShowRequest -> {
                    updateState(TodoReduce.UpdateIsTimePickerBottomSheetVisible(true))
                }

                is TodoEvent.OnTodoTextChange -> {
                    updateState(TodoReduce.UpdateTodoText(event.todoText))
                }
                is TodoEvent.OnCategoryChipClick -> {
                    updateState(TodoReduce.UpdateSelectedCategory(event.category))
                    updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(true))
                }
                TodoEvent.OnAddCategoryClick -> TODO()
                TodoEvent.OnDateChanged -> TODO()
                TodoEvent.OnManageCategoryClick -> TODO()
                TodoEvent.OnCommitmentAreaClick -> {
                    updateState(TodoReduce.UpdateIsCommitmentBottomSheetVisible(true))
                }
                TodoEvent.OnCommitmentDone -> {
                    updateState(TodoReduce.UpdateConfirmedCommitmentMessage(commitmentMessage = currentUiState.textFieldCommitmentMessage))
                    updateState(TodoReduce.UpdateIsCommitmentBottomSheetVisible(false))
                }
                is TodoEvent.OnTextFieldCommitmentMessageChange -> {
                    updateState(TodoReduce.UpdateTextFieldCommitmentMessage(commitmentMessage = event.text))
                }
                TodoEvent.OnCommitmentBottomSheetDismissRequest -> {
                    updateState(TodoReduce.UpdateIsCommitmentBottomSheetVisible(false))
                }
            }
        }

        override fun reduceState(
            state: TodoState,
            reduce: TodoReduce,
        ): TodoState {
            return when (reduce) {
                is UpdateCategories -> {
                    state.copy(categories = reduce.categories)
                }
                is UpdateFlatList -> {
                    state.copy(flatList = reduce.flatList)
                }
                is UpdateCategoriesAndFlatList -> {
                    state.copy(categories = reduce.categories, flatList = reduce.flatList)
                }
                is UpdateIsMenuOpen -> {
                    state.copy(isMenuOpen = reduce.isMenuOpen)
                }
                is UpdateSelectedDate -> {
                    state.copy(selectedDate = reduce.selectedDate)
                }
                is TodoReduce.UpdateIsAddTodoBottomSheetVisible -> {
                    state.copy(isAddTodoBottomSheetVisible = reduce.isVisible)
                }
                is TodoReduce.UpdateIsTimePickerBottomSheetVisible -> {
                    state.copy(isTimePickerBottomSheetVisible = reduce.isVisible)
                }
                is TodoReduce.UpdateSelectedCategory -> {
                    state.copy(selectedCategory = reduce.category)
                }
                is TodoReduce.UpdateSelectedStartTime -> {
                    state.copy(selectedStartTime = reduce.startTime)
                }
                is TodoReduce.UpdateTodoText -> {
                    state.copy(todoText = reduce.todoText)
                }
                TodoReduce.ClearAddTodoState -> {
                    state.copy(
                        todoText = "",
                        selectedCategory = null,
                        selectedStartTime = null,
                    )
                }
                is TodoReduce.UpdateIsCommitmentBottomSheetVisible -> {
                    state.copy(isCommitmentBottomSheetVisible = reduce.isVisible)
                }
                is TodoReduce.UpdateTextFieldCommitmentMessage -> {
                    state.copy(textFieldCommitmentMessage = reduce.commitmentMessage)
                }
                is TodoReduce.UpdateConfirmedCommitmentMessage -> {
                    state.copy(confirmedCommitmentMessage = reduce.commitmentMessage)
                }
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

        private fun synchronizeListState(
            list: MutableList<ListItem>,
        ) {
            var currentCategory: ListItem.CategoryItem? = null

            for (i in list.indices) {
                when (val item = list[i]) {
                    is ListItem.CategoryItem -> {
                        currentCategory = item
                    }
                    is ListItem.TodoItem -> {
                        currentCategory?.let { catItem ->
                            val correctCategory = catItem.category
                            var needsUpdate = false
                            var updatedItem = item

                            if (item.category.categoryId != correctCategory.categoryId) {
                                updatedItem = updatedItem.copy(category = correctCategory)
                                needsUpdate = true
                            }

                            val isNowLast = (i == list.size - 1) || (list[i + 1] is ListItem.CategoryItem)
                            if (item.isLastInCategory != isNowLast) {
                                updatedItem = updatedItem.copy(isLastInCategory = isNowLast)
                                needsUpdate = true
                            }

                            if (needsUpdate) {
                                list[i] = updatedItem
                            }
                        }
                    }
                }
            }
        }

        private fun reconstructCategoriesFromFlatList(flatList: List<ListItem>): List<Category> {
            val newCategories = mutableListOf<Category>()
            var currentTodos = mutableListOf<Todo>()
            var currentCategory: Category? = null

            flatList.forEach { item ->
                when (item) {
                    is ListItem.CategoryItem -> {
                        currentCategory?.let {
                            newCategories.add(it.copy(todos = currentTodos.toList()))
                        }
                        currentCategory = item.category
                        currentTodos = mutableListOf()
                    }
                    is ListItem.TodoItem -> {
                        currentTodos.add(item.todo)
                    }
                }
            }

            currentCategory?.let {
                newCategories.add(it.copy(todos = currentTodos.toList()))
            }

            return newCategories
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
    }
