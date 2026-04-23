package org.android.bbangzip.presentation.ui.todo

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.CommitmentRepository
import org.android.bbangzip.domain.repository.TodoRepository
import org.android.bbangzip.domain.repository.UserDefaultRepository
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
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateIsAddTodoBottomSheetVisible
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateIsCommitmentBottomSheetVisible
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateIsMenuOpen
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateIsTimePickerBottomSheetVisible
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateSelectedDate
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateTodoState
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoReduce.UpdateTodoText
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoSideEffect
import org.android.bbangzip.presentation.ui.todo.TodoContract.TodoState
import org.android.bbangzip.presentation.common.util.extension.getBbangZipDate
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
        private val commitmentRepository: CommitmentRepository,
        private val userDefaultRepository: UserDefaultRepository,
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
                    val today = getBbangZipDate()
                    updateState(UpdateSelectedDate(today))
                    getTodoList(today)
                    viewModelScope.launch {
                        userDefaultRepository.userPreferenceFlow.collect { preferences ->
                            updateState(TodoReduce.UpdateIsSundayStart(isSundayStart = preferences.isSundayStart))
                        }
                    }
                }

                is TodoEvent.OnTodoCheckBoxClick -> {
                    patchTodoCompletion(
                        categoryId = event.categoryId,
                        todoId = event.todoId,
                        isChecked = event.isChecked,
                    )
                }

                is TodoEvent.OnListItemMove -> {
                    val from = event.from
                    val to = event.to

                    if (from == to) return

                    val currentFlatList = currentUiState.flatList.toMutableList()
                    val movedItem: ListItem.TodoItem = currentFlatList.removeAt(from) as ListItem.TodoItem
                    currentFlatList.add(to, movedItem)

                    synchronizeListState(currentFlatList)
                    val newCategories = reconstructCategoriesFromFlatList(currentFlatList)
                    val targetCategory =
                        currentFlatList.filterIsInstance<ListItem.TodoItem>().filter {
                            it.todo.todoId == movedItem.todo.todoId
                        }[0].category

                    updateState(UpdateCategoriesAndFlatList(newCategories, currentFlatList.toList()))
                    viewModelScope.launch {
                        todoRepository.reorderTodo(
                            todoId = movedItem.todo.todoId.toLong(),
                            originCategoryId = movedItem.category.id.toLong(),
                            targetCategoryId = targetCategory.id.toLong(),
                            targetCategoryColor = targetCategory.color,
                            todoOrderList = currentFlatList.filterIsInstance<ListItem.TodoItem>().map { it.todo.todoId.toLong() },
                        ).onSuccess {
                        }.onFailure {
                            Timber.d("투두 순서 변경 실패")
                        }
                    }
                }

                is TodoEvent.OnMenuClick -> {
                    updateState(UpdateIsMenuOpen(isMenuOpen = !currentUiState.isMenuOpen))
                }

                TodoEvent.OnAddTodoDone -> {
                    if (currentUiState.todoText.isNotBlank()) {
                        addTodo(
                            categoryId = currentUiState.selectedCategory!!.id,
                            todoContent = currentUiState.todoText,
                            targetDate = currentUiState.selectedDate,
                            startTime = currentUiState.selectedStartTime,
                        )
                    } else {
                        updateState(
                            UpdateTodoState(
                                currentUiState.copy(
                                    isAddTodoBottomSheetVisible = false,
                                    selectedCategory = null,
                                    selectedStartTime = null,
                                ),
                            ),
                        )
                    }
                }

                is TodoEvent.OnTimeConfirmButtonClick -> {
                    if (currentUiState.isEditMode) {
                        val prevStartTime = currentUiState.selectedStartTime
                        val selectedTodoId = currentUiState.selectedTodoItem!!.todo.todoId
                        viewModelScope.launch {
                            todoRepository.modifyTodoTime(
                                todoId = selectedTodoId.toLong(),
                                startTime = event.startTime,
                            ).onSuccess {
                                val updatedCategories =
                                    currentUiState.categories.map { category ->
                                        category.copy(
                                            todos =
                                                category.todos.map {
                                                    if (it.todoId == selectedTodoId) {
                                                        it.copy(startTime = event.startTime)
                                                    } else {
                                                        it
                                                    }
                                                },
                                        )
                                    }
                                updateState(
                                    UpdateTodoState(
                                        currentUiState.copy(
                                            selectedStartTime = event.startTime,
                                            categories = updatedCategories,
                                            flatList = updatedCategories.toFlatList(),
                                            isTimePickerBottomSheetVisible = false,
                                            isTodoSettingBottomSheetVisible = true,
                                            selectedTodoItem =
                                                currentUiState.selectedTodoItem!!.copy(
                                                    todo =
                                                        currentUiState.selectedTodoItem!!.todo.copy(
                                                            startTime = event.startTime,
                                                        ),
                                                ),
                                        ),
                                    ),
                                )
                            }.onFailure {
                                updateState(
                                    UpdateTodoState(
                                        currentUiState.copy(
                                            selectedStartTime = prevStartTime,
                                            isTimePickerBottomSheetVisible = false,
                                            isTodoSettingBottomSheetVisible = true,
                                        ),
                                    ),
                                )
                                Timber.d("투두 시간 변경 실패")
                            }
                        }
                    } else {
                        updateState(
                            UpdateTodoState(
                                currentUiState.copy(
                                    selectedStartTime = event.startTime,
                                    isTimePickerBottomSheetVisible = false,
                                    isAddTodoBottomSheetVisible = true,
                                ),
                            ),
                        )
                    }
                }

                TodoEvent.OnTimePickerBottomSheetDismissRequest -> {
                    if (currentUiState.isEditMode) {
                        updateState(
                            UpdateTodoState(
                                currentUiState.copy(
                                    isTimePickerBottomSheetVisible = false,
                                    isTodoSettingBottomSheetVisible = true,
                                ),
                            ),
                        )
                    } else {
                        updateState(
                            UpdateTodoState(
                                currentUiState.copy(
                                    isTimePickerBottomSheetVisible = false,
                                    isAddTodoBottomSheetVisible = true,
                                ),
                            ),
                        )
                    }
                }

                TodoEvent.OnTimePickerBottomSheetClearButtonClick -> {
                    if (currentUiState.isEditMode) {
                        val prevStartTime = currentUiState.selectedStartTime
                        val selectedTodoId = currentUiState.selectedTodoItem!!.todo.todoId
                        viewModelScope.launch {
                            todoRepository.modifyTodoTime(
                                todoId = selectedTodoId.toLong(),
                                startTime = null,
                            ).onSuccess {
                                val updatedCategories =
                                    currentUiState.categories.map { category ->
                                        category.copy(
                                            todos =
                                                category.todos.map {
                                                    if (it.todoId == selectedTodoId) {
                                                        it.copy(startTime = null)
                                                    } else {
                                                        it
                                                    }
                                                },
                                        )
                                    }
                                updateState(
                                    UpdateTodoState(
                                        currentUiState.copy(
                                            selectedStartTime = null,
                                            categories = updatedCategories,
                                            flatList = updatedCategories.toFlatList(),
                                            isTimePickerBottomSheetVisible = false,
                                            isTodoSettingBottomSheetVisible = true,
                                            selectedTodoItem =
                                                currentUiState.selectedTodoItem!!.copy(
                                                    todo =
                                                        currentUiState.selectedTodoItem!!.todo.copy(
                                                            startTime = null,
                                                        ),
                                                ),
                                        ),
                                    ),
                                )
                            }.onFailure {
                                updateState(
                                    UpdateTodoState(
                                        currentUiState.copy(
                                            selectedStartTime = prevStartTime,
                                            isTimePickerBottomSheetVisible = false,
                                            isTodoSettingBottomSheetVisible = true,
                                        ),
                                    ),
                                )
                            }
                        }
                    } else {
                        updateState(
                            UpdateTodoState(
                                currentUiState.copy(
                                    selectedStartTime = null,
                                    isAddTodoBottomSheetVisible = true,
                                    isTimePickerBottomSheetVisible = false,
                                ),
                            ),
                        )
                    }
                }

                TodoEvent.OnAddTodoBottomSheetDismissRequest -> {
                    if (currentUiState.todoText.isNotBlank()) {
                        addTodo(
                            categoryId = currentUiState.selectedCategory!!.id,
                            todoContent = currentUiState.todoText,
                            targetDate = currentUiState.selectedDate,
                            startTime = currentUiState.selectedStartTime,
                        )
                    } else {
                        updateState(
                            UpdateTodoState(
                                currentUiState.copy(
                                    isAddTodoBottomSheetVisible = false,
                                    selectedCategory = null,
                                    selectedStartTime = null,
                                ),
                            ),
                        )
                    }
                }

                TodoEvent.OnTimePickerBottomSheetShowRequest -> {
                    updateState(UpdateIsTimePickerBottomSheetVisible(true))
                    updateState(UpdateIsAddTodoBottomSheetVisible(false))
                }

                is TodoEvent.OnTodoTextChange -> {
                    updateState(UpdateTodoText(event.todoText))
                }

                is TodoEvent.OnCategoryChipClick -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                selectedCategory = event.category,
                                isAddTodoBottomSheetVisible = true,
                                isEditMode = false
                            ),
                        ),
                    )
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
                    updateState(UpdateIsCommitmentBottomSheetVisible(true))
                }

                TodoEvent.OnCommitmentDone -> {
                    val prevCommitment = currentUiState.commitmentMessage
                    updateState(TodoReduce.UpdateCommitmentMessage(commitmentMessage = currentUiState.commitmentMessage))
                    updateState(UpdateIsCommitmentBottomSheetVisible(false))
                    viewModelScope.launch {
                        commitmentRepository
                            .submitCommitmentMessage(commitmentMessage = currentUiState.commitmentMessage)
                            .onSuccess { data ->
                            }.onFailure {
                                Timber.d("다짐 메세지 작성 실패")
                                updateState(TodoReduce.UpdateCommitmentMessage(commitmentMessage = prevCommitment))
                            }
                    }
                }

                is TodoEvent.OnTextFieldCommitmentMessageChange -> {
                    updateState(TodoReduce.UpdateCommitmentMessage(commitmentMessage = event.text))
                }

                TodoEvent.OnCommitmentBottomSheetDismissRequest -> {
                    updateState(UpdateIsCommitmentBottomSheetVisible(false))
                }

                is TodoEvent.OnTodoItemMenuClick -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                selectedTodoItem = event.todoItem,
                                selectedStartTime = event.todoItem.todo.startTime,
                                isTodoSettingBottomSheetVisible = true,
                                isEditMode = true,
                            ),
                        ),
                    )
                }

                TodoEvent.OnTodoSettingBottomSheetDismissRequest -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                selectedTodoItem = null,
                                isTodoSettingBottomSheetVisible = false,
                            ),
                        ),
                    )
                }

                TodoEvent.OnCopyTodoClick -> {
                    viewModelScope.launch {
                        val selectedTodoId = currentUiState.selectedTodoItem!!.todo.todoId
                        todoRepository.copyTodo(
                            todoId = selectedTodoId.toLong(),
                        ).onSuccess { data ->
                            val newTodo =
                                Todo(
                                    todoId = data.todoId,
                                    content = data.content,
                                    isCompleted = data.isCompleted,
                                    startTime = data.startTime,
                                )
                            val updatedCategories =
                                currentUiState.categories.map { category ->
                                    val originTodoIndex = category.todos.indexOfFirst { it.todoId == selectedTodoId }

                                    if (originTodoIndex != -1) {
                                        val mutableTodos = category.todos.toMutableList()
                                        mutableTodos.add(originTodoIndex + 1, newTodo)
                                        category.copy(todos = mutableTodos)
                                    } else {
                                        category
                                    }
                                }

                            updateState(
                                UpdateTodoState(
                                    currentUiState.copy(
                                        isTodoSettingBottomSheetVisible = false,
                                        selectedTodoItem = null,
                                        categories = updatedCategories,
                                        flatList = updatedCategories.toFlatList(),
                                    ),
                                ),
                            )
                        }.onFailure {
                            Timber.d("복제 실패!")
                        }
                    }
                }

                TodoEvent.OnDeleteTodoButtonClick -> {
                    viewModelScope.launch {
                        val selectedTodoId = currentUiState.selectedTodoItem!!.todo.todoId
                        todoRepository.deleteTodo(
                            todoId = selectedTodoId.toLong(),
                        ).onSuccess {
                            val updatedCategories =
                                currentUiState.categories.map { category ->
                                    category.copy(
                                        todos = category.todos.filter { it.todoId != selectedTodoId },
                                    )
                                }
                            updateState(
                                UpdateTodoState(
                                    currentUiState.copy(
                                        selectedTodoItem = null,
                                        isTodoSettingBottomSheetVisible = false,
                                        categories = updatedCategories,
                                        flatList = updatedCategories.toFlatList(),
                                    ),
                                ),
                            )
                        }.onFailure {
                            Timber.d("삭제 실패")
                        }
                    }
                }

                TodoEvent.OnModifyTodoDateClick -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                isTodoSettingBottomSheetVisible = false,
                                isCalendarBottomSheetVisible = true,
                                isDateSavable = false,
                                selectedMonthlyCalendarDate = currentUiState.selectedDate,
                                isRepeat = false,
                            ),
                        ),
                    )
                }

                TodoEvent.OnModifyTodoStartTimeClick -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                selectedStartTime = null,
                                isTimePickerBottomSheetVisible = true,
                                isTodoSettingBottomSheetVisible = false,
                            ),
                        ),
                    )
                }

                TodoEvent.OnMoveTodoToTomorrowClick -> {
                    viewModelScope.launch {
                        val selectedTodoId = currentUiState.selectedTodoItem!!.todo.todoId
                        todoRepository.modifyTodoDate(
                            todoId = selectedTodoId.toLong(),
                            targetDate = null,
                        ).onSuccess {
                            val updatedCategories =
                                currentUiState.categories.map { category ->
                                    category.copy(
                                        todos = category.todos.filter { it.todoId != selectedTodoId },
                                    )
                                }

                            updateState(
                                UpdateTodoState(
                                    currentUiState.copy(
                                        selectedTodoItem = null,
                                        isTodoSettingBottomSheetVisible = false,
                                        categories = updatedCategories,
                                        flatList = updatedCategories.toFlatList(),
                                    ),
                                ),
                            )
                        }.onFailure {
                            Timber.d("미루기 실패")
                        }
                    }
                }

                TodoEvent.OnRepeatTodoClick -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                isTodoSettingBottomSheetVisible = false,
                                isCalendarBottomSheetVisible = true,
                                selectedMonthlyCalendarDate = currentUiState.selectedDate,
                                isRepeat = true,
                            ),
                        ),
                    )
                }

                TodoEvent.OnModifyTodoNameButtonClick -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                todoText = currentUiState.selectedTodoItem!!.todo.content,
                                isEditTodoNameBottomSheetVisible = true,
                                isTodoSettingBottomSheetVisible = false,
                            ),
                        ),
                    )
                }

                TodoEvent.OnEditTodoDone,
                TodoEvent.OnEditTodoNameBottomSheetDismissRequest,
                -> {
                    viewModelScope.launch {
                        val selectedTodoId = currentUiState.selectedTodoItem!!.todo.todoId
                        todoRepository.modifyTodoName(
                            todoId = selectedTodoId.toLong(),
                            content = currentUiState.todoText,
                        ).onSuccess {
                            val updatedCategories =
                                currentUiState.categories.map { category ->
                                    category.copy(
                                        todos =
                                            category.todos.map {
                                                if (it.todoId == selectedTodoId) {
                                                    it.copy(content = currentUiState.todoText)
                                                } else {
                                                    it
                                                }
                                            },
                                    )
                                }
                            updateState(
                                UpdateTodoState(
                                    currentUiState.copy(
                                        selectedTodoItem = null,
                                        selectedCategory = null,
                                        todoText = "",
                                        isEditTodoNameBottomSheetVisible = false,
                                        categories = updatedCategories,
                                        flatList = updatedCategories.toFlatList(),
                                    ),
                                ),
                            )
                        }.onFailure {
                            Timber.d("투두 이름 변경 실패")
                            updateState(
                                UpdateTodoState(
                                    currentUiState.copy(
                                        selectedTodoItem = null,
                                        selectedCategory = null,
                                        todoText = "",
                                        isEditTodoNameBottomSheetVisible = false,
                                    ),
                                ),
                            )
                        }
                    }
                }

                is TodoEvent.OnCalendarCellClick -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                selectedMonthlyCalendarDate = event.date,
                                isDateSavable = currentUiState.selectedDate != event.date,
                            ),
                        ),
                    )
                }

                TodoEvent.OnSaveDateClick -> {
                    viewModelScope.launch {
                        val selectedTodoId = currentUiState.selectedTodoItem!!.todo.todoId
                        if (currentUiState.isRepeat) {
                            todoRepository.repeatTodo(
                                todoId = selectedTodoId.toLong(),
                                targetDate = currentUiState.selectedMonthlyCalendarDate,
                            ).onSuccess {
                                updateState(
                                    UpdateTodoState(
                                        currentUiState.copy(
                                            isCalendarBottomSheetVisible = false,
                                            selectedTodoItem = null,
                                            selectedMonthlyCalendarDate = getBbangZipDate(),
                                        ),
                                    ),
                                )
                            }.onFailure {
                                Timber.d("다른날 또하기 실패")
                            }
                        } else {
                            todoRepository.modifyTodoDate(
                                todoId = selectedTodoId.toLong(),
                                targetDate = currentUiState.selectedMonthlyCalendarDate,
                            ).onSuccess {
                                val updatedCategories =
                                    currentUiState.categories.map { category ->
                                        category.copy(
                                            todos = category.todos.filter { it.todoId != selectedTodoId },
                                        )
                                    }

                                updateState(
                                    UpdateTodoState(
                                        currentUiState.copy(
                                            isCalendarBottomSheetVisible = false,
                                            selectedTodoItem = null,
                                            categories = updatedCategories,
                                            flatList = updatedCategories.toFlatList(),
                                            selectedMonthlyCalendarDate = getBbangZipDate(),
                                        ),
                                    ),
                                )
                            }.onFailure {
                                Timber.d("날짜 변경 실패")
                            }
                        }
                    }
                }

                TodoEvent.OnCalendarBottomSheetDismissRequest -> {
                    updateState(
                        UpdateTodoState(
                            currentUiState.copy(
                                isCalendarBottomSheetVisible = false,
                                selectedTodoItem = null,
                                selectedMonthlyCalendarDate = getBbangZipDate(),
                            ),
                        ),
                    )
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
                isChecked = isChecked,
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
                            isChecked = !isChecked,
                        )
                    }
            }
        }

        private fun toggleCheckBox(
            categoryList: List<Category>,
            categoryId: Int,
            todoId: Int,
            isChecked: Boolean,
        ) {
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
                        date = date.toYyyyMmDdString(),
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
                            TodoReduce.UpdateTodoListData(
                                categories = categoryList,
                                flatList = categoryList.toFlatList(),
                                commitmentMessage = data.commitmentMessage ?: "",
                            ),
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
                    Timber.tag("BbangZipWeeklyCalendar").d("reduce selectedDate: ${reduce.selectedDate}")
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

                is TodoReduce.UpdateCommitmentMessage -> {
                    state.copy(commitmentMessage = reduce.commitmentMessage)
                }

                is TodoReduce.UpdateTodoState -> {
                    return reduce.todoState
                }

                is TodoReduce.UpdateTodoListData -> {
                    state.copy(
                        categories = reduce.categories,
                        flatList = reduce.flatList,
                        commitmentMessage = reduce.commitmentMessage,
                    )
                }

                is TodoReduce.UpdateIsSundayStart -> {
                    state.copy(isSundayStart = reduce.isSundayStart)
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

        fun addTodo(
            categoryId: Int,
            todoContent: String,
            targetDate: LocalDate,
            startTime: LocalTime?,
        ) {
            viewModelScope.launch {
                todoRepository
                    .addTodo(
                        categoryId = categoryId.toLong(),
                        content = todoContent,
                        targetDate = targetDate,
                        startTime = startTime,
                    )
                    .onSuccess { data ->
                        val newTodo =
                            Todo(
                                todoId = data.todoId,
                                content = data.content,
                                isCompleted = data.isCompleted,
                                startTime = data.startTime,
                            )
                        val updatedCategories =
                            currentUiState.categories.map { category ->
                                if (category.id == categoryId) {
                                    category.copy(todos = category.todos + newTodo)
                                } else {
                                    category
                                }
                            }
                        updateState(
                            TodoReduce.UpdateTodoState(
                                currentUiState.copy(
                                    todoText = "",
                                    selectedCategory = null,
                                    selectedStartTime = null,
                                    isAddTodoBottomSheetVisible = false,
                                    categories = updatedCategories,
                                    flatList = updatedCategories.toFlatList(),
                                ),
                            ),
                        )
                        Timber.d("Update Todo 성공!")
                    }.onFailure {
                        Timber.d("Todo 생성 싪패!")
                    }
            }
        }
    }
