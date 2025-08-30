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
        onListItemMove = { fromIndex, toIndex ->
            viewModel.setEvent(TodoEvent.OnListItemMove(fromIndex, toIndex))
        },
        onTodoCheckBoxClick = { todoId, categoryId, isChecked ->
            viewModel.setEvent(TodoEvent.OnTodoCheckBoxClick(todoId, categoryId, isChecked))
        },
        onMenuClick = {
            viewModel.setEvent(TodoEvent.OnMenuClick)
        },
    )
}
