package org.android.bbangzip.presentation.ui.my.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.model.BottomNavigationRoute
import org.android.bbangzip.presentation.ui.friend.FriendRoute
import org.android.bbangzip.presentation.ui.my.MyRoute

fun NavController.navigateToMy(navOptions: NavOptions) {
    navigate(
        route = BottomNavigationRoute.My,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.myNavGraph() {
    composable<BottomNavigationRoute.My> {
        MyRoute()
    }
}