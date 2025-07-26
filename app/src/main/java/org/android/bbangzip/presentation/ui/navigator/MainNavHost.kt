package org.android.bbangzip.presentation.ui.navigator

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import org.android.bbangzip.presentation.ui.friend.navigation.friendNavGraph
import org.android.bbangzip.presentation.ui.my.navigation.myNavGraph
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.presentation.ui.timer.navigation.timerNavGraph
import org.android.bbangzip.presentation.ui.todo.navigation.todoNavGraph

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
) {
    val sharedViewModel : SharedViewModel = hiltViewModel()
    NavHost(
        navController = navigator.navHostController,
        startDestination = navigator.startDestination,
    ) {
        timerNavGraph(
            sharedViewModel = sharedViewModel,
            showBottomBar = navigator::showBottomBar,
            hideBottomBar = navigator::hideBottomBar,
        )

        todoNavGraph()

        myNavGraph()

        friendNavGraph()
    }
}
