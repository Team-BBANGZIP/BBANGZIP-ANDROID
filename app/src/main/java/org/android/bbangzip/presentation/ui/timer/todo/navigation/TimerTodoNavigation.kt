package org.android.bbangzip.presentation.ui.timer.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoRoute

@Serializable
data class TimerTodo(val timeOptionIndex: Int = 0)

fun NavController.navigateTimerTodo(timeOptionIndex: Int) {
    navigate(
        route = TimerTodo(timeOptionIndex = timeOptionIndex),
    )
}

fun NavGraphBuilder.timerTodoNavGraph(
    navigateToTimer: (Boolean) -> Unit,
    navigateToBack: () -> Unit,
) {
    composable<TimerTodo> { backStackEntry ->
        val timeOptionIndex = backStackEntry.toRoute<TimerTodo>().timeOptionIndex
        TimerTodoRoute(
            navigateToTimer = navigateToTimer,
            navigateToBack = navigateToBack,
            timeOptionIndex = timeOptionIndex,
        )
    }
}
