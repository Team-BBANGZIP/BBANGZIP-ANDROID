package org.android.bbangzip.presentation.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kotlin.reflect.KFunction1

fun NavController.navigateToLogin() {
    navigate(
        route = LoginRoute,
    ) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.loginNavGraph(
    navigateToTodo: KFunction1<NavOptions, Unit>,
    navigateToOnboarding: () -> Unit = {},
) {
    composable<LoginRoute> {
        LoginRoute(
            navigateToTodo = navigateToTodo,
            navigateToOnboarding = navigateToOnboarding,
        )
    }
}

@Serializable
object LoginRoute