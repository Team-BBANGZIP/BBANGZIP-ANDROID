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
        categories = uiState.categories,
        motivationMessage = uiState.motivationMessage,
        totalTodoCount = uiState.totalTodoCount,
        completedTodoCount = uiState.completedTodoCount,
        onListChanged = { updatedCategories ->
            viewModel.setEvent(TodoEvent.OnCategoriesChanged(updatedCategories))
        },
        onTodoCheckBoxClick = { todoId, categoryId, isChecked ->
            viewModel.setEvent(TodoEvent.OnTodoCheckBoxClick(todoId, categoryId, isChecked))
        },
    )
}
