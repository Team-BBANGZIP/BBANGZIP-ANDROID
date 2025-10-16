package org.android.bbangzip.presentation.ui.editcategory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryRoute

fun NavController.navigateToEditCategory(navOptions: NavOptions) {
    navigate(
        route = EditCategory,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.editCategoryNavGraph() {
    composable<EditCategory> {
        EditCategoryRoute()
    }
}
