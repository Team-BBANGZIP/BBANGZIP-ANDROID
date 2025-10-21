package org.android.bbangzip.presentation.ui.addcategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.CategoryColorPickerBottomSheet
import org.android.bbangzip.presentation.common.component.button.ColorSettingButton
import org.android.bbangzip.presentation.common.component.row.InteractionRow
import org.android.bbangzip.presentation.common.component.textfield.BbangZipUnderLinedTextField
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.presentation.ui.addcategory.type.AddCategoryActionType
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun AddCategoryScreen(
    categoryName: String = "",
    isDoneEnable: Boolean = false,
    isColorPickerBottomSheetVisible: Boolean = false,
    selectedColorString: String = "RED1",
    onCategoryNameChange: (String) -> Unit = {},
    onTopBarLeadingIconClick: () -> Unit = {},
    onTopBarTrailingIconClick: () -> Unit = {},
    onColorSettingRowActionIconClick: () -> Unit = {},
    onColorPickerBottomSheetDismissRequest: () -> Unit = {},
    onColorItemClick: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(BbangZipTheme.color.staticWhite_FFFFFF)
                .statusBarsPadding(),
    ) {
        AddCategoryHeader(
            isDoneEnable = isDoneEnable,
            onBackButtonClick = onTopBarLeadingIconClick,
            onDoneButtonClick = onTopBarTrailingIconClick,
        )

        Gap(height = 32.dp)

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            BbangZipUnderLinedTextField(
                value = categoryName,
                onValueChange = onCategoryNameChange,
                focusRequester = focusRequester,
                focusManager = focusManager,
                placeholder = R.string.add_category_text_field_placeholder,
            )

            Gap(height = 12.dp)

            AddCategoryActionType.entries.forEach { actionType ->
                InteractionRow(
                    interactionIconResId = actionType.interactionIconResId,
                    actionName = stringResource(actionType.actionName),
                ) {
                    when (actionType) {
                        AddCategoryActionType.COLOR_SETTING ->
                            ColorSettingButton(
                                selectedColorString = selectedColorString,
                                onColorSettingRowActionIconClick = onColorSettingRowActionIconClick,
                            )
                    }
                }
            }
        }

        CategoryColorPickerBottomSheet(
            isBottomSheetVisible = isColorPickerBottomSheetVisible,
            onDismissRequest = onColorPickerBottomSheetDismissRequest,
            onColorItemClick = onColorItemClick,
        )
    }
}

@Composable
private fun AddCategoryHeader(
    modifier: Modifier = Modifier,
    isDoneEnable: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    onDoneButtonClick: () -> Unit = {},
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .noRippleClickable(
                        isRunOnce = true,
                        onClick = onBackButtonClick,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_24),
                contentDescription = stringResource(R.string.add_category_back_navigation_icon_description),
                modifier = Modifier.padding(horizontal = 18.dp),
                tint = BbangZipTheme.color.labelAssistive_C9C7C5,
            )
        }

        Text(
            text = stringResource(R.string.add_category_screen_title),
            modifier =
                Modifier
                    .padding(vertical = 18.dp)
                    .padding(start = 80.dp),
            style = BbangZipTheme.typography.title2Medium,
            color = BbangZipTheme.color.labelNormal_6B6560,
            textAlign = TextAlign.Center,
        )

        Gap()

        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .noRippleClickable(
                        enabled = isDoneEnable,
                        isRunOnce = true,
                        onClick = onDoneButtonClick,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.add_category_done_button_label),
                modifier = Modifier.padding(horizontal = 26.dp),
                style = BbangZipTheme.typography.body1Medium,
                color = if (isDoneEnable) BbangZipTheme.color.labelNormal_6B6560 else BbangZipTheme.color.labelDisable_E4E2E0,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddCategoryScreenPreview() {
    var categoryName by remember { mutableStateOf("") }
    var isDoneEnable by remember { mutableStateOf(false) }
    var isColorPickerVisible by remember { mutableStateOf(false) }
    var selectedColorString by remember { mutableStateOf("RED1") }

    BBANGZIPANDROIDTheme {
        AddCategoryScreen(
            categoryName = categoryName,
            selectedColorString = selectedColorString,
            onCategoryNameChange = {
                categoryName = it
                isDoneEnable = it.isNotEmpty()
            },
            isDoneEnable = isDoneEnable,
            isColorPickerBottomSheetVisible = isColorPickerVisible,
            onColorSettingRowActionIconClick = { isColorPickerVisible = !isColorPickerVisible },
            onColorPickerBottomSheetDismissRequest = { isColorPickerVisible = false },
            onColorItemClick = {
                isColorPickerVisible = false
                selectedColorString = it
            },
        )
    }
}
