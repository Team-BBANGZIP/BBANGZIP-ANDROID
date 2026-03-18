package org.android.bbangzip.presentation.ui.screensetting.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.android.bbangzip.presentation.ui.profileedit.ProfileEditRoute
import org.android.bbangzip.presentation.ui.screensetting.ScreenSettingRoute

fun NavController.navigateToScreenSetting() {
    navigate(
        route = ScreenSettingRoute
    )
}

fun NavGraphBuilder.screenSettingNavGraph(
    navigateToBack: () -> Unit
) {
    composable<ScreenSettingRoute> {
        ScreenSettingRoute(
            navigateToBack = navigateToBack
        )
    }
}

@Serializable
object ScreenSettingRoute