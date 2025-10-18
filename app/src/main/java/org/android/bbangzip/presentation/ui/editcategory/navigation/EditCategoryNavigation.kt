package org.android.bbangzip.presentation.ui.editcategory.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import kotlinx.serialization.json.Json
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryRoute

fun NavController.navigateToEditCategory() {
    navigate(
        route = EditCategory,
    )
}

fun NavGraphBuilder.editCategoryNavGraph(
    popBackStack: () -> Unit,
) {
    composable<EditCategory> {
        EditCategoryRoute(
            popBackStack = popBackStack,
        )
    }
}

val CategoryNavType =
    object : NavType<Category>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Category? {
            return bundle.getString(key)?.let { Json.decodeFromString(it)}
        }

        override fun parseValue(value: String): Category {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: Category) {
            bundle.putString(key, Json.encodeToString(Category.serializer(), value))
        }

        override fun serializeAsValue(value: Category): String {
            return Json.encodeToString(Category.serializer(), value)
        }
}