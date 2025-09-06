package org.android.bbangzip.presentation.ui.timer.todo

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoContract.TimerTodoEvent
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoContract.TimerTodoSideEffect


@Composable
fun TimerTodoRoute(
    navigateToTimer: (shouldRestart: Boolean) -> Unit,
    navigateToBack: () -> Unit,
    timeOptionIndex: Int,
    modifier: Modifier = Modifier,
    viewModel: TimerTodoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val success by viewModel.success.collectAsStateWithLifecycle(initialValue = true)

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                is TimerTodoSideEffect.NavigateToBack -> navigateToBack()
                is TimerTodoSideEffect.NavigateToTimer -> navigateToTimer(effect.shouldRestart)
            }
        }
    }

    when (success) {
        true -> TimerTodoScreen(
            uiState = uiState,
            timeOptionIndex = timeOptionIndex,
            modifier = modifier,
            onBackIconClick = { viewModel.setEvent(TimerTodoEvent.OnBackIconClick) },
            onExitBtnClick = { viewModel.setEvent(TimerTodoEvent.OnExitBtnClick) },
            onRestartTimerBtnClick = { viewModel.setEvent(TimerTodoEvent.OnRestartTimerBtnClick) },
            onAddTodoIconClick = { viewModel.setEvent(TimerTodoEvent.OnAddTodoIconClick) },
            onTodoCheckBoxClick = { categoryId, todoId, isChecked ->
                viewModel.setEvent(
                    TimerTodoEvent.OnTodoCheckBoxClick(
                        categoryId = categoryId,
                        todoId = todoId,
                        isChecked = isChecked,
                    ),
                )
            },
        )

        false -> {
            CircularProgressIndicator()
        }
    }

}