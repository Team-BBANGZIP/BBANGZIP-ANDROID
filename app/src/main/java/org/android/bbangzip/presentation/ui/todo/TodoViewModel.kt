package org.android.bbangzip.presentation.ui.todo

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
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoEvent
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateCategories
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateCategoriesAndFlatList
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateFlatList
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateIsMenuOpen
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateSelectedDate
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoSideEffect
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoState
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val todoRepository: TodoRepository,
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
                    getTodoList()
                }

                is TodoEvent.OnTodoCheckBoxClick -> {
                    patchTodoCompletion(
                        categoryId = event.categoryId,
                        todoId = event.todoId,
                        isChecked = event.isChecked
                    )
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
//                    viewModelScope.launch{
//                        todoRepository.patchTodoOrder(
//                            todoId = TODO(),
//                            originCategoryId = TODO(),
//                            targetCategoryId = TODO(),
//                            targetCategoryColor = TODO(),
//                            todoOrderList = TODO()
//                        )
//                    }
                }

                is TodoEvent.OnMenuClick -> {
                    updateState(UpdateIsMenuOpen(isMenuOpen = !currentUiState.isMenuOpen))
                }

                is TodoEvent.OnAddTodoDone -> {
                    if (event.todoContent.isNotBlank() && event.category != null) {
                        onTodoAdd(event.category.id, event.todoContent, event.startTime)
                        updateState(TodoReduce.ClearAddTodoState)
                        updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(false))
                    }
                }

                is TodoEvent.OnTimeConfirmButtonClick -> {
                    updateState(TodoReduce.UpdateSelectedStartTime(event.startTime))
                    updateState(TodoReduce.UpdateIsTimePickerBottomSheetVisible(false))
                    updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(true))
                }

                TodoEvent.OnTimePickerBottomSheetDismissRequest -> {
                    updateState(TodoReduce.UpdateIsTimePickerBottomSheetVisible(false))
                    updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(true))
                }
                TodoEvent.OnAddTodoBottomSheetDismissRequest -> {
                    if (currentUiState.selectedCategory != null && currentUiState.todoText.isNotBlank()) {
                        onTodoAdd(categoryId = currentUiState.selectedCategory!!.id, todoContent = currentUiState.todoText, startTime = currentUiState.selectedStartTime)
                    }
                    updateState(TodoReduce.ClearAddTodoState)
                    updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(false))
                }

                TodoEvent.OnTimePickerBottomSheetShowRequest -> {
                    updateState(TodoReduce.UpdateIsTimePickerBottomSheetVisible(true))
                    updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(false))
                }

                is TodoEvent.OnTodoTextChange -> {
                    updateState(TodoReduce.UpdateTodoText(event.todoText))
                }
                is TodoEvent.OnCategoryChipClick -> {
                    updateState(TodoReduce.UpdateSelectedCategory(event.category))
                    updateState(TodoReduce.UpdateIsAddTodoBottomSheetVisible(true))
                }
                TodoEvent.OnAddCategoryClick -> {
                    updateState(UpdateIsMenuOpen(false))
                    setSideEffect(TodoSideEffect.NavigateToAddCategory)
                }
                is TodoEvent.OnDateSelect -> {
                    updateState(UpdateSelectedDate(event.date))
                    getTodoList(event.date)
                }
                TodoEvent.OnManageCategoryClick -> {
                    updateState(UpdateIsMenuOpen(false))
                    setSideEffect(TodoSideEffect.NavigateToManageCategory)
                }
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

    private fun patchTodoCompletion(
        categoryId: Int,
        todoId: Int,
        isChecked: Boolean,
    ) {
        toggleCheckBox(
            categoryList = currentUiState.categories,
            categoryId = categoryId,
            todoId = todoId,
            isChecked = isChecked
        )
        viewModelScope.launch {
            todoRepository
                .toggleTodoCompletion(todoId = todoId.toLong(), isCompleted = isChecked)
                .onSuccess { data ->
                }.onFailure {
                    Timber.d("투두 체크 변경 실패")
                    toggleCheckBox(
                        categoryList = currentUiState.categories,
                        categoryId = categoryId,
                        todoId = todoId,
                        isChecked = !isChecked
                    )
                }
        }
    }

    private fun toggleCheckBox(categoryList: List<Category>, categoryId: Int, todoId: Int, isChecked: Boolean) {
        val updatedCategories =
            categoryList.map { category ->
                if (category.id == categoryId) {
                    category.copy(
                        todos =
                            category.todos.map { todo ->
                                if (todo.todoId == todoId) {
                                    todo.copy(isCompleted = isChecked)
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

    private fun getTodoList(
        date: LocalDate = currentUiState.selectedDate,
    ) {
        viewModelScope.launch {
            todoRepository
                .getTodoList(
                    date = date.toYyyyMmDdString()
                )
                .onSuccess { data ->
                    val categoryList = data.categories.map { category ->
                        Category(
                            id = category.categoryId,
                            name = category.categoryName,
                            color = category.categoryColor,
                            todos = category.todos.map { todo ->
                                Todo(
                                    todoId = todo.todoId,
                                    content = todo.content,
                                    isCompleted = todo.isCompleted,
                                    startTime = todo.startTime
                                )
                            }
                        )
                    }

                    updateState(
                        TodoReduce.UpdateTodoState(
                            currentUiState.copy(
                                categories = categoryList,
                                flatList = categoryList.toFlatList(),
                                confirmedCommitmentMessage = data.commitmentMessage
                            )
                        )
                    )
                }.onFailure { throwable ->
                    Timber.d("TimerViewmodel 초기화 실패 $throwable")
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

                is TodoReduce.UpdateTodoState -> {
                    reduce.todoState
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

                            if (item.category.id != correctCategory.id) {
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
    }
