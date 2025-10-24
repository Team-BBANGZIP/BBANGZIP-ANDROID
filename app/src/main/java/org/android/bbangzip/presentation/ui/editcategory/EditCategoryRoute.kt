package org.android.bbangzip.presentation.ui.editcategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.ui.editcategory.EditCategoryContract.EditCategoryEvent

@Composable
fun EditCategoryRoute(
    popBackStack: () -> Unit,
    category: Category,
    modifier: Modifier = Modifier,
    viewModel: EditCategoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(EditCategoryEvent.Initialize(category))
    }

    LaunchedEffect(viewModel.uiSideEffect) {
        viewModel.uiSideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is EditCategoryContract.EditCategorySideEffect.PopBackStack -> popBackStack()
            }
        }
    }

    EditCategoryScreen(
        categoryNameInput = uiState.categoryNameInput,
        categoryName = uiState.categoryName,
        isConfirmEnable = uiState.isConfirmEnable,
        isColorPickerBottomSheetVisible = uiState.isColorPickerBottomSheetVisible,
        selectedColorString = uiState.selectedColorString,
        isCategoryStopped = uiState.isCategoryStopped,
        isDeleteConfirmationBottomSheetVisible = uiState.isDeleteConfirmationBottomSheetVisible,
        onCategoryNameInputChange = {
            viewModel.setEvent(EditCategoryEvent.OnCategoryNameInputChange(it))
        },
        onBackIconClick = {
            viewModel.setEvent(EditCategoryEvent.OnBackIconClick)
        },
        onConfirmButtonClick = {
            viewModel.setEvent(EditCategoryEvent.OnConfirmButtonClick)
        },
        onColorSettingRowActionIconClick = {
            viewModel.setEvent(EditCategoryEvent.OnColorSettingRowActionIconClick)
        },
        onColorPickerBottomSheetDismissRequest = {
            viewModel.setEvent(EditCategoryEvent.OnColorPickerBottomSheetDismissRequest)
        },
        onColorItemClick = {
            viewModel.setEvent(EditCategoryEvent.OnColorItemClick(it))
        },
        onStopRowSwitchClick = {
            viewModel.setEvent(EditCategoryEvent.OnStopRowSwitchClick)
        },
        onDeleteButtonClick = {
            viewModel.setEvent(EditCategoryEvent.OnDeleteButtonClick)
        },
        onDeleteConfirmationBottomSheetDismissRequest = {
            viewModel.setEvent(EditCategoryEvent.OnDeleteConfirmationBottomSheetDismissRequest)
        },
        onDeleteConfirmationBottomSheetCancelButtonClick = {
            viewModel.setEvent(EditCategoryEvent.OnDeleteCancleButtonClick)
        },
        onDeleteConfirmationBottomSheetConfirmButtonClick = {
            viewModel.setEvent(EditCategoryEvent.OnDeleteConfirmButtonClick)
        },

    )
}
