package org.android.bbangzip.presentation.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashNavGraph(
    navigateToLogin: () -> Unit = {},
    navigateToTimer: () -> Unit = {},
) {
    composable<SplashRoute> {
        SplashRoute(
            navigateToLogin = navigateToLogin,
            navigateToTimer = navigateToTimer,
        )
    }
}

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToTimer: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(SplashContract.SplashEvent.Initialize)
    }

    LaunchedEffect(Unit) {
        viewModel.uiSideEffect.collect { effect ->
            when (effect) {
                SplashContract.SplashSideEffect.NavigateToLogin -> navigateToLogin()
                SplashContract.SplashSideEffect.NavigateToTimer -> navigateToTimer()
            }
        }
    }

    SplashScreen()
}

@Serializable
object SplashRoute
