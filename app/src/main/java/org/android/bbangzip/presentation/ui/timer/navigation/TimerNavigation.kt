package org.android.bbangzip.presentation.ui.timer.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.model.BottomNavigationRoute
import org.android.bbangzip.presentation.ui.shared.Shared
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
    showBottomBar: () -> Unit = {},
    hideBottomBar: () -> Unit = {},
    navigateToCompleteTask: () -> Unit = {},
) {
    composable<BottomNavigationRoute.Timer> {
        TimerRoute(
            sharedViewModel = sharedViewModel,
            showBottomBar = showBottomBar,
            hideBottomBar = hideBottomBar,
            navigateToCompleteTask = navigateToCompleteTask,
        )
    }
}
