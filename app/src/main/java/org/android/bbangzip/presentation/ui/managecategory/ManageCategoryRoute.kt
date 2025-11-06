package org.android.bbangzip.presentation.ui.managecategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategoryEvent
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.ManageCategorySideEffect

@Composable
fun ManageCategoryRoute(
    popBackStack: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToEditCategory: (Category) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ManageCategoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(
            ManageCategoryEvent.Initialize,
        )
    }

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is ManageCategorySideEffect.PopBackStack -> popBackStack()
                is ManageCategorySideEffect.NavigateToAddCategory -> navigateToAddCategory()
                is ManageCategorySideEffect.NavigateToEditCategory -> navigateToEditCategory(sideEffect.category)
            }
        }
    }

    ManageCategoryScreen(
        categories = uiState.categories,
        modifier = modifier,
        onTopBarTrailingIconClick = {
            viewModel.setEvent(ManageCategoryEvent.OnTopBarTrailingIconClick)
        },
        onTopBarLeadingIconClick = {
            viewModel.setEvent(ManageCategoryEvent.OnTopBarLeadingIconClick)
        },
        onCategoryChipClick = {
            viewModel.setEvent(ManageCategoryEvent.OnCategoryChipClick(it))
        },
        onCategoryDragEnd = { from, to ->
            viewModel.setEvent(ManageCategoryEvent.OnCategoryChipDragEnd(from, to))
        },
    )
}
