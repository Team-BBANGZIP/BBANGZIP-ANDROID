package org.android.bbangzip.presentation.ui.timer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.android.bbangzip.presentation.common.model.BottomNavigationRoute
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.presentation.ui.timer.TimerRoute
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoRoute


fun NavGraphBuilder.timerNavGraph(
    sharedViewModel: SharedViewModel,
    navigateToTimerTodo: (Int) -> Unit = {},
) {
    composable<BottomNavigationRoute.Timer> { backStackEntry ->
        val item = backStackEntry.toRoute<BottomNavigationRoute.Timer>()
        TimerRoute(
            sharedViewModel = sharedViewModel,
            navigateToTimerTodo = navigateToTimerTodo,
            shouldRestartTimer = item.shouldRestart,
        )
    }
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

fun NavController.navigateToTimer(
    navOptions: NavOptions,
    shouldRestart: Boolean = false,
) {
    navigate(
        route = BottomNavigationRoute.Timer(shouldRestart = shouldRestart),
        navOptions = navOptions,
    )
}

fun NavController.navigateTimerTodo(timeOptionIndex: Int) {
    navigate(
        route = TimerTodo(timeOptionIndex = timeOptionIndex),
    )
}
