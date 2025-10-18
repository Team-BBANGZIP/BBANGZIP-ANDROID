package org.android.bbangzip.presentation.ui.managecategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.android.bbangzip.presentation.ui.managecategory.ManageCategoryContract.*

@Composable
fun ManageCategoryRoute(
    modifier: Modifier = Modifier,
    viewModel: ManageCategoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            viewModel.setEvent(ManageCategoryEvent.OnCategoryChipClick)
        },
        onCategoryDragEnd = { from, to ->
            viewModel.setEvent(ManageCategoryEvent.OnCategoryChipDragEnd(from, to))
        },
    )
}
