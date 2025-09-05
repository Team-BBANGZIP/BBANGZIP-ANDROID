package org.android.bbangzip.presentation.ui.timer.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimerTodoRoute(
    navigateToTimer: () -> Unit, modifier: Modifier = Modifier
) {
    TimerTodoScreen(
        modifier = modifier,
    )
}