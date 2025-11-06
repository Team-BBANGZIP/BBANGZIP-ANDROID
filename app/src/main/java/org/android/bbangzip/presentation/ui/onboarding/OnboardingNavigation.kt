package org.android.bbangzip.presentation.ui.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavController.navigateToOnboarding() {
    navigate(
        route = OnboardingRoute,
    ) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.onboardingNavGraph(
    navigateToTodo: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
) {
    composable<OnboardingRoute> {
        OnboardingRoute(
            navigateToTodo = navigateToTodo,
            navigateToLogin = navigateToLogin,
        )
    }
}

@Serializable
object OnboardingRoute
