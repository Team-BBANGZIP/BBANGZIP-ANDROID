package org.android.bbangzip.presentation.ui.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.android.bbangzip.presentation.ui.friend.navigation.friendNavGraph
import org.android.bbangzip.presentation.ui.main.MainNavigator
import org.android.bbangzip.presentation.ui.my.navigation.myNavGraph
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.presentation.ui.timer.navigation.timerNavGraph
import org.android.bbangzip.presentation.ui.timer.navigation.timerTodoNavGraph
import org.android.bbangzip.presentation.ui.todo.navigation.todoNavGraph

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    sharedViewModel: SharedViewModel,
    padding: PaddingValues,
) {
    NavHost(
        navController = navigator.navHostController,
        startDestination = navigator.startDestination,
    ) {
        timerNavGraph(
            sharedViewModel = sharedViewModel,
            navigateToTimerTodo = navigator::navigateToTimerTodo,
        )

        timerTodoNavGraph(
            navigateToTimer = navigator::navigateToTimerWithRestart,
            navigateToBack = navigator::popBackStack,
        )

        todoNavGraph(
            padding = padding,
        )

        myNavGraph()

        friendNavGraph()
    }
}