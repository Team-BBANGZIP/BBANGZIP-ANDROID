package org.android.bbangzip.presentation.ui.todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.android.bbangzip.presentation.ui.todo.TodoContract.*

@Composable
fun TodoRoute(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TodoScreen(
        modifier = modifier,
        flatList = uiState.flatList,
        motivationMessage = uiState.motivationMessage,
        totalTodoCount = uiState.totalTodoCount,
        completedTodoCount = uiState.completedTodoCount,
        isMenuOpen = uiState.isMenuOpen,
        isAddTodoBottomSheetVisible = uiState.isAddTodoBottomSheetVisible,
        isCommitmentBottomSheetVisible = uiState.isCommitmentBottomSheetVisible,
        isTimePickerBottomSheetVisible = uiState.isTimePickerBottomSheetVisible,
        todoText = uiState.todoText,
        selectedCategory = uiState.selectedCategory,
        selectedStartTime = uiState.selectedStartTime,
        onListItemMove = { fromIndex, toIndex ->
            viewModel.setEvent(TodoEvent.OnListItemMove(fromIndex, toIndex))
        },
        onTodoCheckBoxClick = { todoId, categoryId, isChecked ->
            viewModel.setEvent(TodoEvent.OnTodoCheckBoxClick(todoId, categoryId, isChecked))
        },
        onMenuClick = {
            viewModel.setEvent(TodoEvent.OnMenuClick)
        },
        onTodoAdd = { category, todo, todoDate ->
            viewModel.setEvent(TodoEvent.OnTodoAdd(category, todo, todoDate))
        },
        onTimeConfirmButtonClick = { startTime ->
            viewModel.setEvent(TodoEvent.OnTimeConfirmButtonClick(startTime))
        },
        onTimePickerBottomSheetDismissRequest = {
            viewModel.setEvent(TodoEvent.OnTimePickerBottomSheetDismissRequest)
        },
        onAddTodoBottomSheetDismissRequest = {
            viewModel.setEvent(TodoEvent.OnAddTodoBottomSheetDismissRequest)
        },
        onAddTodoBottomSheetShowRequest = {
            viewModel.setEvent(TodoEvent.OnAddTodoBottomSheetShowRequest)
        },
        onTimePickerBottomSheetShowRequest = {
            viewModel.setEvent(TodoEvent.OnTimePickerBottomSheetShowRequest)
        },
        onCategorySelect = { category ->
            viewModel.setEvent(TodoEvent.OnCategorySelect(category))
        },
        onTodoTextChange = { todoText ->
            viewModel.setEvent(TodoEvent.OnTodoTextChange(todoText))
        },
    )
}
