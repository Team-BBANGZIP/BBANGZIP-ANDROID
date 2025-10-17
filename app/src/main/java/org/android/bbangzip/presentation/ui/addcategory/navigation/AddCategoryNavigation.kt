package org.android.bbangzip.presentation.ui.addcategory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.ui.addcategory.AddCategoryRoute
import org.android.bbangzip.presentation.ui.managecategory.navigation.ManageCategory

fun NavController.navigateToAddCategory() {
    navigate(
        route = ManageCategory,
    )
}

fun NavGraphBuilder.addCategoryNavGraph() {
    composable<AddCategory> {
        AddCategoryRoute()
    }
}
