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
import org.android.bbangzip.presentation.model.Route
import org.android.bbangzip.presentation.type.BottomNavigationType
import org.android.bbangzip.presentation.ui.dummy.navigation.navigateDummy

class MainNavigator(
    val navHostController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination

    val startDestination = BottomNavigationRoute.Dummy

    val currentBottomNavigationBarItem: BottomNavigationType?
        @Composable get() =
            BottomNavigationType.find { mainBottomNavigationRoute ->
                currentDestination?.route == mainBottomNavigationRoute::class.qualifiedName
            }

    @SuppressLint("RestrictedApi")
    fun navigateBottomNavigation(bottomNavigationType: BottomNavigationType) {
        navOptions {
            popUpTo(BottomNavigationRoute.Dummy::class.qualifiedName.orEmpty()) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }.let { navOptions ->
            when (bottomNavigationType) {
                BottomNavigationType.DUMMY -> navigateToDummy(navOptions)
                else -> Unit
            }
        }
    }

    private fun navigateToDummy(navOptions: NavOptions) {
        navHostController.navigateDummy(navOptions)
    }

    private fun popBackStack() {
        navHostController.popBackStack()
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean =
        navHostController.currentDestination?.route == T::class.qualifiedName

    @Composable
    fun showBottomBar(): Boolean =
        BottomNavigationType.any {
            currentDestination?.route == it::class.qualifiedName
        }
}

@Composable
fun rememberMainNavigator(navHostController: NavHostController = rememberNavController()): MainNavigator =
    remember(navHostController) {
        MainNavigator(navHostController = navHostController)
    }
