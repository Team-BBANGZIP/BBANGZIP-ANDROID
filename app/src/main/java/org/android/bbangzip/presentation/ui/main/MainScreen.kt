package org.android.bbangzip.presentation.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import okhttp3.internal.toImmutableList
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import org.android.bbangzip.R
import org.android.bbangzip.presentation.ui.main.component.BottomNavigationBar
import org.android.bbangzip.presentation.ui.main.component.MainNavHost
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

@Composable
fun MainScreen(
    navigator: MainNavigator,
    sharedViewModel: SharedViewModel,
) {
    val context = LocalContext.current
    val backPressedTime = remember { mutableLongStateOf(0L) }

    val canPop = navigator.navHostController.previousBackStackEntry != null
    val exitToastMessage = stringResource(id = R.string.double_back_press_to_exit_toast_message)

    BackHandler(enabled = !canPop) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime.longValue < 2000L) {
            (context as? Activity)?.finish()
        } else {
            backPressedTime.longValue = currentTime
            Toast.makeText(context, exitToastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    MainScreenContent(
        navigator = navigator,
        sharedViewModel = sharedViewModel,
    )
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    sharedViewModel: SharedViewModel,
) {
    val sharedState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier =
            modifier
                .padding(WindowInsets.navigationBars.asPaddingValues()),
        content = { padding ->
            MainNavHost(
                navigator = navigator,
                sharedViewModel = sharedViewModel,
                padding = padding,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                isVisible = navigator.isBottomBarVisible() && sharedState.isBottomBarVisible,
                bottomNaviBarItems = BottomNavigationType.entries.toImmutableList(),
                currentNaviBarItemSelected = navigator.currentBottomNavigationBarItem,
                onBottomNaviBarItemSelected = { navigator.navigateBottomNavigation(it) },
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    BBANGZIPANDROIDTheme {
        MainScreen(navigator = rememberMainNavigator(), sharedViewModel = hiltViewModel())
    }
}
