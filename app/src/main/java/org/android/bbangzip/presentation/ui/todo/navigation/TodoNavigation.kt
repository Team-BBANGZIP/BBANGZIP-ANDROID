package org.android.bbangzip.presentation.ui.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.model.BottomNavigationRoute
import org.android.bbangzip.presentation.ui.friend.FriendRoute
import org.android.bbangzip.presentation.ui.todo.TodoRoute

fun NavController.navigateToTodo(navOptions: NavOptions) {
    navigate(
        route = BottomNavigationRoute.Todo,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.todoNavGraph() {
    composable<BottomNavigationRoute.Todo> {
        TodoRoute()
    }
}