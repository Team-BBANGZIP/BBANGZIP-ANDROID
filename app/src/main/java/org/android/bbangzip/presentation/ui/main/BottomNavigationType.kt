package org.android.bbangzip.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.model.BottomNavigationRoute
import org.android.bbangzip.presentation.common.model.Route

enum class BottomNavigationType(
    @DrawableRes val bottomNaviIcon: Int,
    @StringRes val bottomNaviTitle: Int,
    val route: BottomNavigationRoute,
) {
    TIMER(
        bottomNaviIcon = R.drawable.ic_timer_default_24,
        bottomNaviTitle = R.string.bottom_navi_timer_tab_title,
        route = BottomNavigationRoute.Timer(),
    ),
    TODO(
        bottomNaviIcon = R.drawable.ic_book_default_24,
        bottomNaviTitle = R.string.bottom_navi_todo_tab_title,
        route = BottomNavigationRoute.Todo,
    ),
    FRIEND(
        bottomNaviIcon = R.drawable.ic_bubble_default_24,
        bottomNaviTitle = R.string.bottom_navi_friend_tab_title,
        route = BottomNavigationRoute.Friend,
    ),
    MY(
        bottomNaviIcon = R.drawable.ic_person_default_24,
        bottomNaviTitle = R.string.bottom_navi_my_title,
        route = BottomNavigationRoute.My,
    ),
    ;

    companion object {
        @Composable
        fun find(predicate: @Composable (BottomNavigationRoute) -> Boolean): BottomNavigationType? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun any(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}