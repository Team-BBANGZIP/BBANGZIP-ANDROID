package org.android.bbangzip.presentation.ui.timer.todo

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TimerTodoRoute(
    navigateToTimer: (shouldRestart : Boolean) -> Unit,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimerTodoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val success by viewModel.success.collectAsStateWithLifecycle(initialValue = true)

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { effect ->
            when (effect) {
                is TimerTodoContract.TimerTodoSideEffect.NavigateToBack -> navigateToBack()
                is TimerTodoContract.TimerTodoSideEffect.NavigateToTimer -> navigateToTimer(effect.shouldRestart)
            }
        }
    }

    when(success) {
        true -> TimerTodoScreen(
            modifier = modifier,
        )
        false -> {
            CircularProgressIndicator()
        }
    }

}