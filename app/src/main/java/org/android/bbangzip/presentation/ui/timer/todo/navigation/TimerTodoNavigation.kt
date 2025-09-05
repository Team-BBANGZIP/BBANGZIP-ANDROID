package org.android.bbangzip.presentation.ui.timer.todo.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoRoute

@Serializable
object TimerTodo

fun NavController.navigateTimerTodo(navOptions: NavOptions) {
    navigate(
        route = TimerTodo,
        navOptions = navOptions,
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
