package org.android.bbangzip.presentation.ui.addcategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.ui.addcategory.AddCategoryContract.*

@Composable
fun AddCategoryRoute(
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddCategoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                AddCategorySideEffect.PopBackStack -> popBackStack()
            }
        }
    }

    AddCategoryScreen(
        categoryName = uiState.categoryNameInput,
        isDoneEnable = uiState.isDoneEnable,
        isColorPickerBottomSheetVisible = uiState.isColorPickerBottomSheetVisible,
        selectedColorString = uiState.selectedColorString,
        onCategoryNameChange = {
            viewModel.setEvent(AddCategoryEvent.OnCategoryNameInputChange(it))
        },
        onTopBarLeadingIconClick = {
            viewModel.setEvent(AddCategoryEvent.OnTopBarLeadingIconClick)
        },
        onTopBarTrailingIconClick = {
            viewModel.setEvent(AddCategoryEvent.OnTopBarTrailingIconClick)
        },
        onColorSettingRowActionIconClick = {
            viewModel.setEvent(AddCategoryEvent.OnColorSettingRowActionIconClick)
        },
        onColorPickerBottomSheetDismissRequest = {
            viewModel.setEvent(AddCategoryEvent.OnColorPickerBottomSheetDismissRequest)
        },
        onColorItemClick = {
            viewModel.setEvent(AddCategoryEvent.OnColorItemClick(it))
        },
    )
}
