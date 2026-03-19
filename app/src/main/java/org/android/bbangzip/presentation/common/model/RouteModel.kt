package org.android.bbangzip.presentation.common.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route

@Serializable
sealed interface BottomNavigationRoute : Route {
    companion object {
        const val TIMER = "Timer"
        const val TODO = "Todo"
        const val MY = "My"

        fun BottomNavigationRoute.routeName(): String =
            when (this) {
                is Timer -> TIMER
                is Todo -> TODO
                is My -> MY
            }
    }

    @Serializable
    data class Timer(
        val shouldRestart: Boolean = false,
    ) : BottomNavigationRoute

    @Serializable
    data object Todo : BottomNavigationRoute

    @Serializable
    data object My : BottomNavigationRoute
}
