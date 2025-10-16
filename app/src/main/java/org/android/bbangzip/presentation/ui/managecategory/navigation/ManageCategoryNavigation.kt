package org.android.bbangzip.presentation.ui.managecategory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryRoute

fun NavController.navigateToManageCategory(navOptions: NavOptions) {
    navigate(
        route = ManageCategory,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.manageCategoryNavGraph() {
    composable<ManageCategory> {
        ManageCategoryRoute()
    }
}
