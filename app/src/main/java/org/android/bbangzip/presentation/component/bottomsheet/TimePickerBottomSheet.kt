package org.android.bbangzip.presentation.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.component.timepicker.BbangZipTimePicker
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onCancleButtonClick: () -> Unit,
    onConfirmButtonClick: (LocalTime) -> Unit,
    initialTime: LocalTime,
) {
    var selectedTime by remember(initialTime) { mutableStateOf(initialTime) }

    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.time_picker_title),
                color = BbangZipTheme.color.labelAlternative_A29D96,
                style = BbangZipTheme.typography.title3SemiBold,
            )
        },
        content = {
            Gap(height = 32.dp)

            BbangZipTimePicker(
                initialTime = initialTime,
                onTimeSelected = { time ->
                    selectedTime = time
                },
            )

            Gap(height = 48.dp)
        },
        interactRow = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancleButtonClick,
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_x_default_24),
                            contentDescription = stringResource(R.string.button_cancel_description),
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_cancellation),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onConfirmButtonClick(selectedTime) },
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_check_default_24),
                            contentDescription = stringResource(R.string.button_setting_description),
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_setting),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TimePickerBottomSheetPreview() {
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.Blue,
                    ),
                onClick = { isBottomSheetVisible = !isBottomSheetVisible },
            ) {
                Text("바텀시트 띄우기")
            }

            TimePickerBottomSheet(
                isBottomSheetVisible = isBottomSheetVisible,
                onDismissRequest = { isBottomSheetVisible = !isBottomSheetVisible },
                onCancleButtonClick = {},
                onConfirmButtonClick = {},
                initialTime = LocalTime.now(),
            )
        }
    }
}
