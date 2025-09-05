package org.android.bbangzip.presentation.ui.timer.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoRoute

@Serializable
object TimerTodo

fun NavController.navigateTimerTodo() {
    navigate(
        route = TimerTodo,
    )
}

fun NavGraphBuilder.timerTodoNavGraph(
    navigateToTimer: () -> Unit,
) {
    composable<TimerTodo> {
        TimerTodoRoute(
             navigateToTimer = navigateToTimer,
         )
    }
}
