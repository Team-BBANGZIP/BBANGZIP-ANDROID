package org.android.bbangzip.presentation.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashNavGraph(
    navigateToLogin: () -> Unit = {},
) {
    composable<SplashRoute> {
        SplashRoute(
            navigateToLogin = navigateToLogin,
        )
    }
}

@Composable
fun SplashRoute(
    navigateToLogin: () -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(1000L)
        navigateToLogin()
    }

    SplashScreen()
}

@Serializable
object SplashRoute
