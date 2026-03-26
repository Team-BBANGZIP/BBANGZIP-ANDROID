package org.android.bbangzip.presentation.ui.profileedit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.android.bbangzip.presentation.ui.profileedit.ProfileEditRoute

fun NavController.navigateToProfileEdit() {
    navigate(
        route = ProfileEditRoute,
    )
}

fun NavGraphBuilder.profileEditNavGraph(
    navigateToBack: () -> Unit,
) {
    composable<ProfileEditRoute> {
        ProfileEditRoute(
            navigateToBack = navigateToBack,
        )
    }
}

@Serializable
object ProfileEditRoute
