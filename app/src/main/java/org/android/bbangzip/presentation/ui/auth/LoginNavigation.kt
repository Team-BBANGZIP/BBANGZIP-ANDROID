package org.android.bbangzip.presentation.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin() {
    navigate(
        route = LoginRoute,
    ) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.loginNavGraph(
    navigateToTimer: () -> Unit = {},
    navigateToOnboarding: () -> Unit = {},
) {
    composable<LoginRoute> {
        LoginRoute(
            navigateToTimer = navigateToTimer,
            navigateToOnboarding = navigateToOnboarding,
        )
    }
}

@Serializable
object LoginRoute
