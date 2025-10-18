package org.android.bbangzip.presentation.ui.managecategory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryRoute

fun NavController.navigateToManageCategory() {
    navigate(
        route = ManageCategory,
    )
}

fun NavGraphBuilder.manageCategoryNavGraph(
    navigateToAddCategory: () -> Unit,
    navigateToEditCategory: (Category) -> Unit,
    popBackStack: () -> Unit,
) {
    composable<ManageCategory> {
        ManageCategoryRoute(
            navigateToAddCategory = navigateToAddCategory,
            navigateToEditCategory = navigateToEditCategory,
            popBackStack = popBackStack,
        )
    }
}
