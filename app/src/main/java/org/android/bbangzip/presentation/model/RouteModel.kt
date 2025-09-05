package org.android.bbangzip.presentation.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route

@Serializable
sealed interface BottomNavigationRoute : Route {
    companion object {
        const val TIMER = "Timer"
        const val TODO = "Todo"
        const val FRIEND = "Friend"
        const val MY = "My"

        fun BottomNavigationRoute.routeName(): String = when (this) {
            is BottomNavigationRoute.Timer  -> BottomNavigationRoute.TIMER
            is BottomNavigationRoute.Todo   -> BottomNavigationRoute.TODO
            is BottomNavigationRoute.Friend -> BottomNavigationRoute.FRIEND
            is BottomNavigationRoute.My     -> BottomNavigationRoute.MY
        }
    }

    @Serializable
    data class Timer(
        val shouldRestart: Boolean = false
    ) : BottomNavigationRoute

    @Serializable
    data object Todo : BottomNavigationRoute

    @Serializable
    data object Friend : BottomNavigationRoute

    @Serializable
    data object My : BottomNavigationRoute
}
