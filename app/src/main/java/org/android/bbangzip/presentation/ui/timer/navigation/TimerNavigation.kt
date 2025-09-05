package org.android.bbangzip.presentation.ui.timer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.android.bbangzip.presentation.model.BottomNavigationRoute
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.presentation.ui.timer.TimerRoute

fun NavController.navigateToTimer(navOptions: NavOptions) {
    navigate(
        route = BottomNavigationRoute.Timer,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.timerNavGraph(
    sharedViewModel: SharedViewModel,
    navigateToTimerTodo: () -> Unit = {},
) {
    composable<BottomNavigationRoute.Timer> { backStackEntry ->
        val item = backStackEntry.toRoute<BottomNavigationRoute.Timer>()
        TimerRoute(
            sharedViewModel = sharedViewModel,
            navigateToTimerTodo = navigateToTimerTodo,
            shouldRestartTimer = item.shouldRestart
        )
    }
}
