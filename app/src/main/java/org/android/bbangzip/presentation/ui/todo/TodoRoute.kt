package org.android.bbangzip.presentation.ui.todo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.ui.todo.TodoContract.*

@Composable
fun TodoRoute(
    navigateToManageCategory: () -> Unit,
    navigateToAddCategory: () -> Unit,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                is TodoSideEffect.NavigateToManageCategory -> navigateToManageCategory()
                is TodoSideEffect.NavigateToAddCategory -> navigateToAddCategory()
            }
        }
    }

    TodoScreen(
        modifier = modifier.padding(bottom = padding.calculateBottomPadding()),
        flatList = uiState.flatList,
        totalTodoCount = uiState.totalTodoCount,
        completedTodoCount = uiState.completedTodoCount,
        isMenuOpen = uiState.isMenuOpen,
        textFieldCommitmentMessage = uiState.textFieldCommitmentMessage,
        confirmedCommitmentMessage = uiState.confirmedCommitmentMessage,
        isCommitmentBottomSheetVisible = uiState.isCommitmentBottomSheetVisible,
        isAddTodoBottomSheetVisible = uiState.isAddTodoBottomSheetVisible,
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
        onAddTodoDone = { category, todo, todoDate ->
            viewModel.setEvent(TodoEvent.OnAddTodoDone(category, todo, todoDate))
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
        onTimePickerBottomSheetShowRequest = {
            viewModel.setEvent(TodoEvent.OnTimePickerBottomSheetShowRequest)
        },
        onTodoTextChange = { todoText ->
            viewModel.setEvent(TodoEvent.OnTodoTextChange(todoText))
        },
        onCategoryChipClick = { category ->
            viewModel.setEvent(TodoEvent.OnCategoryChipClick(category))
        },
        onCommitmentAreaClick = {
            viewModel.setEvent(TodoEvent.OnCommitmentAreaClick)
        },
        onCommitmentDone = {
            viewModel.setEvent(TodoEvent.OnCommitmentDone)
        },
        onTextFieldCommitmentMessageChange = { text ->
            viewModel.setEvent(TodoEvent.OnTextFieldCommitmentMessageChange(text))
        },
        onCommitmentBottomSheetDismissRequest = {
            viewModel.setEvent(TodoEvent.OnCommitmentBottomSheetDismissRequest)
        },
        onAddCategoryClick = {
            viewModel.setEvent(TodoEvent.OnAddCategoryClick)
        },
        onManageCategoryClick = {
            viewModel.setEvent(TodoEvent.OnManageCategoryClick)
        },
    )
}
