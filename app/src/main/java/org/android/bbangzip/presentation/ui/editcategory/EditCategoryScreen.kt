package org.android.bbangzip.presentation.ui.editcategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.component.button.ColorSettingButton
import org.android.bbangzip.presentation.common.component.row.InteractionRow
import org.android.bbangzip.presentation.common.component.textfield.BbangZipUnderLinedTextField
import org.android.bbangzip.presentation.common.component.toggle.BbangZipSwitch
import org.android.bbangzip.presentation.common.type.CategoryColor
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.presentation.ui.editcategory.type.EditCategoryActionType
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun EditCategoryScreen(
    categoryName: String = "",
    isConfirmEnable: Boolean = false,
    isColorPickerVisible: Boolean = false,
    selectedColorString: String = "RED1",
    isStopped: Boolean = false,
    onCategoryNameChange: (String) -> Unit = {},
    onBackIconClick: () -> Unit = {},
    onConfirmButtonClick: () -> Unit = {},
    onColorSettingRowActionIconClick: () -> Unit = {},
    onColorPickerBottomSheetDismissRequest: () -> Unit = {},
    onColorItemClick: (String) -> Unit = {},
    onStopRowSwitchClick: () -> Unit = {},
){
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(bottom = 12.dp),
    ){
        EditCategoryHeader(
            isConfirmEnable = isConfirmEnable,
            onBackButtonClick = onBackIconClick,
            onConfirmButtonClick = onConfirmButtonClick
        )

        Gap(height = 32.dp)

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            BbangZipUnderLinedTextField(
                value = categoryName,
                onValueChange = onCategoryNameChange,
                focusRequester = focusRequester,
                focusManager = focusManager,
                placeholder = R.string.edit_category_text_field_placeholder,
            )

            EditCategoryActionType.entries.forEach { actionType ->
                InteractionRow(
                    interactionIconResId = actionType.interactionIconResId,
                    actionName = stringResource(actionType.actionName),
                    description = actionType.description,
                ){
                    when(actionType){
                        EditCategoryActionType.COLOR_SETTING ->
                            ColorSettingButton(
                                selectedColorString = selectedColorString,
                                onColorSettingRowActionIconClick = onColorSettingRowActionIconClick
                            )
                        EditCategoryActionType.STOP ->
                            BbangZipSwitch(
                                modifier = Modifier.fillMaxWidth(44 / 335f),
                                isChecked = isStopped,
                                onCheckedChange = onStopRowSwitchClick,
                            )
                    }
                }
            }

            Gap()

            BbangzipBaseButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_trash_default_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                },
                content = {
                    Text(
                        text = stringResource(R.string.button_label_delete),
                        style = BbangZipTheme.typography.body2Medium,
                    )
                },
            )
        }


        CategoryColorPickerBottomSheet(
            isBottomSheetVisible = isColorPickerVisible,
            onDismissRequest = onColorPickerBottomSheetDismissRequest,
            onColorItemClick = onColorItemClick
        )
    }
}

@Composable
private fun EditCategoryHeader(
    modifier: Modifier = Modifier,
    isConfirmEnable: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    onConfirmButtonClick: () -> Unit = {},
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .noRippleClickable(onClick = onBackButtonClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_24),
                contentDescription = stringResource(R.string.edit_category_back_navigation_icon_description),
                modifier = Modifier.padding(horizontal = 18.dp),
                tint = BbangZipTheme.color.labelAssistive_C9C7C5
            )
        }

        Text(
            text = stringResource(R.string.edit_category_screen_title),
            modifier = Modifier
                .padding(vertical = 18.dp)
                .padding(start = 80.dp),
            style = BbangZipTheme.typography.title2Medium,
            color = BbangZipTheme.color.labelNormal_6B6560,
            textAlign = TextAlign.Center
        )

        Gap()

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .noRippleClickable(onClick = onConfirmButtonClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.edit_category_confirm_button_label),
                modifier = Modifier.padding(horizontal = 26.dp),
                style = BbangZipTheme.typography.body1Medium,
                color = if(isConfirmEnable) BbangZipTheme.color.labelNormal_6B6560 else BbangZipTheme.color.labelDisable_E4E2E0
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditCategoryScreenPreview(){
    var categoryName by remember { mutableStateOf("") }
    var isDoneEnable by remember { mutableStateOf(false) }
    var isColorPickerVisible by remember { mutableStateOf(false) }
    var selectedColorString by remember { mutableStateOf("RED1") }
    var isStopped by remember { mutableStateOf(false) }

    BBANGZIPANDROIDTheme {
        EditCategoryScreen(
            categoryName = categoryName,
            selectedColorString = selectedColorString,
            onCategoryNameChange = {
                categoryName = it
                isDoneEnable = it.isNotEmpty()
            },
            isConfirmEnable = isDoneEnable,
            isColorPickerVisible = isColorPickerVisible,
            isStopped = isStopped,
            onColorSettingRowActionIconClick = {isColorPickerVisible = !isColorPickerVisible},
            onColorPickerBottomSheetDismissRequest = {isColorPickerVisible = false},
            onColorItemClick = {
                isColorPickerVisible = false
                selectedColorString = it
            },
            onStopRowSwitchClick = {isStopped = !isStopped}
        )
    }
}