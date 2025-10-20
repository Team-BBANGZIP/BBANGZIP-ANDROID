package org.android.bbangzip.presentation.ui.addcategory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.android.bbangzip.presentation.ui.addcategory.AddCategoryRoute

fun NavController.navigateToAddCategory() {
    navigate(
        route = AddCategory,
    )
}

fun NavGraphBuilder.addCategoryNavGraph(
    popBackStack: () -> Unit,
) {
    composable<AddCategory> {
        AddCategoryRoute(
            popBackStack = popBackStack,
        )
    }
}
