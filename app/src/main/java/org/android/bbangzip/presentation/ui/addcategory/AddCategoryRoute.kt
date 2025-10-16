package org.android.bbangzip.presentation.ui.addcategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.android.bbangzip.presentation.ui.addcategory.AddCategoryContract.*

@Composable
fun AddCategoryRoute(
    modifier: Modifier = Modifier,
    viewModel: AddCategoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
