package org.android.bbangzip.presentation.ui.timer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.model.BottomNavigationRoute
import org.android.bbangzip.presentation.ui.timer.TimerRoute

fun NavController.navigateToTimer(navOptions: NavOptions) {
    navigate(
        route = BottomNavigationRoute.Timer,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.timerNavGraph() {
    composable<BottomNavigationRoute.Timer> {
        TimerRoute()
    }
}
