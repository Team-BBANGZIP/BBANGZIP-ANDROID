package org.android.bbangzip.presentation.ui.todo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.common.model.BottomNavigationRoute
import org.android.bbangzip.presentation.ui.todo.TodoRoute

fun NavController.navigateToTodo(navOptions: NavOptions) {
    navigate(
        route = BottomNavigationRoute.Todo,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.todoNavGraph(
    padding: PaddingValues,
) {
    composable<BottomNavigationRoute.Todo> {
        TodoRoute(
            padding = padding,
        )
    }
}
