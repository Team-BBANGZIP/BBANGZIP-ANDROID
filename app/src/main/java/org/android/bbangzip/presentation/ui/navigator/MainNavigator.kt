package org.android.bbangzip.presentation.ui.navigator

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import org.android.bbangzip.presentation.model.BottomNavigationRoute
import org.android.bbangzip.presentation.model.BottomNavigationRoute.Companion.routeName
import org.android.bbangzip.presentation.model.Route
import org.android.bbangzip.presentation.type.BottomNavigationType
import org.android.bbangzip.presentation.ui.friend.navigation.navigateToFriend
import org.android.bbangzip.presentation.ui.my.navigation.navigateToMy
import org.android.bbangzip.presentation.ui.timer.navigation.navigateToTimer
import org.android.bbangzip.presentation.ui.timer.todo.navigation.navigateTimerTodo
import org.android.bbangzip.presentation.ui.todo.navigation.navigateToTodo
import timber.log.Timber

class MainNavigator(
    val navHostController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination

    val startDestination = BottomNavigationRoute.Timer()

    val currentBottomNavigationBarItem: BottomNavigationType?
        @Composable get() =
            BottomNavigationType.entries.find { type ->
                currentDestination?.route?.contains(
                    "BottomNavigationRoute.${type.route.routeName()}"
                ) == true
            }


    @SuppressLint("RestrictedApi")
    fun navigateBottomNavigation(bottomNavigationType: BottomNavigationType) {
        Timber.d("[navigation] currentDestination -> ${navHostController.currentDestination}")
        navOptions {
            popUpTo(BottomNavigationRoute.Timer::class.qualifiedName.orEmpty()) {
                saveState = true
                Timber.d("[navigation] saveState -> $saveState")
            }
            launchSingleTop = true
            restoreState = true
            Timber.d("[navigation] restoreState -> $restoreState")
        }.let { navOptions ->
            when (bottomNavigationType) {
                BottomNavigationType.TIMER -> navigateToTimer(navOptions)
                BottomNavigationType.TODO -> navigateToTodo(navOptions)
                BottomNavigationType.FRIEND -> navigateToFriend(navOptions)
                BottomNavigationType.MY -> navigateToMy(navOptions)
            }
        }
    }

    private fun navigateToFriend(navOptions: NavOptions) {
        navHostController.navigateToFriend(navOptions)
    }

    private fun navigateToMy(navOptions: NavOptions) {
        navHostController.navigateToMy(navOptions)
    }

    private fun navigateToTimer(navOptions: NavOptions) {
        navHostController.navigateToTimer(navOptions)
    }

    private fun navigateToTodo(navOptions: NavOptions) {
        navHostController.navigateToTodo(navOptions)
    }

    fun navigateToTimerWithRestart() {
        navHostController.navigateToTimer(
            navOptions = navOptions {
                popUpTo(BottomNavigationRoute.Timer) {
                    saveState = false
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = false
            },
        )
    }

    fun navigateToTimerTodo() {
        navHostController.navigateTimerTodo()
    }

    fun popBackStack() {
        navHostController.popBackStack()
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean =
        navHostController.currentDestination?.route == T::class.qualifiedName

    @Composable
    fun isBottomBarVisible(): Boolean {
        val isVisibleByRoute = BottomNavigationType.entries.any { type ->
            currentDestination?.route?.contains(
                "BottomNavigationRoute.${type.route.routeName()}"
            ) == true
        }

        return isVisibleByRoute
    }
}

@Composable
fun rememberMainNavigator(navHostController: NavHostController = rememberNavController()): MainNavigator =
    remember(navHostController) {
        MainNavigator(navHostController = navHostController)
    }
