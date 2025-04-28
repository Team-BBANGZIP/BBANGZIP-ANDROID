package org.android.bbangzip.presentation.ui.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import org.android.bbangzip.presentation.ui.dummy.navigation.dummyNavGraph

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues
) {
    Box(
        modifier =
            modifier
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize()
                .background(Color.White),
    ) {
        NavHost(
            navController = navigator.navHostController,
            startDestination = navigator.startDestination
        ) {
            dummyNavGraph()
        }
    }
}
