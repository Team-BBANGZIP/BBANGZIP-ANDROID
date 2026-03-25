package org.android.bbangzip.presentation.ui.editcategory.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmationBottomSheet(
    isBottomSheetVisible: Boolean = false,
    categoryName: String = "",
    onDismissRequest: () -> Unit = {},
    onConfirmButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                stringResource(R.string.delete_confirmation_bottom_sheet_title),
                style = BbangZipTheme.typography.title1SemiBold,
                color = BbangZipTheme.color.primaryNormal_897869,
            )
        },
        content = {
            Gap(height = 60.dp)

            Text(
                stringResource(R.string.delete_confirmation_bottom_sheet_content, categoryName),
                style = BbangZipTheme.typography.body2Medium,
                color = BbangZipTheme.color.labelAlternative_A29D96,
                textAlign = TextAlign.Center,
            )

            Gap(height = 60.dp)
        },
        interactRow = {
            DeleteConfirmationInteractionRow(
                onCancelButtonClick = onCancelButtonClick,
                onConfirmButtonClick = onConfirmButtonClick,
            )

            Gap(height = 12.dp)
        },
    )
}

@Composable
private fun DeleteConfirmationInteractionRow(
    onCancelButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        BbangzipBaseButton(
            modifier = Modifier.weight(1f),
            onClick = onCancelButtonClick,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_go_back_default_24),
                    contentDescription = null,
                    tint = BbangZipTheme.color.staticWhite_FFFFFF,
                    modifier = Modifier.size(16.dp),
                )
            },
            content = {
                Text(
                    text = stringResource(R.string.button_label_go_back),
                    style = BbangZipTheme.typography.body2Medium,
                )
            },
        )

        Gap(width = 8.dp)

        BbangzipBaseButton(
            modifier = Modifier.weight(1f),
            onClick = onConfirmButtonClick,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_trash_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            },
            colors =
                BbangZipButtonDefaults.colors(
                    enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                ),
            content = {
                Text(
                    text = stringResource(R.string.button_label_delete),
                    style = BbangZipTheme.typography.body2Medium,
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DeleteConfirmationBottomSheetPreview() {
    BBANGZIPANDROIDTheme {
        DeleteConfirmationBottomSheet(isBottomSheetVisible = true)
    }
}
