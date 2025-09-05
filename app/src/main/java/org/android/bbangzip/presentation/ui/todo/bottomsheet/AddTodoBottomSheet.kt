package org.android.bbangzip.presentation.ui.todo.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.component.textfield.BbangZipTextField
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    focusManager: FocusManager,
    todo: String,
    onTodoChange: (String) -> Unit,
    onSettingTimeClick: () -> Unit,
    modifier: Modifier = Modifier,
    startTime: LocalTime? = null,
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.add_todo_title),
                color = BbangZipTheme.color.labelAlternative_A29D96,
                style = BbangZipTheme.typography.title3SemiBold,
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Gap(31.dp)

                BbangZipTextField(
                    value = todo,
                    onValueChange = onTodoChange,
                    focusManager = focusManager,
                    placeholder = R.string.add_todo_placeholder,
                )

                Gap(16.dp)

                HorizontalDivider(
                    color = Color(0xFFF5F6F5),
                )

                Gap(16.dp)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_clock_default_24),
                        contentDescription = stringResource(id = R.string.add_todo_timer_icon_description),
                        tint = BbangZipTheme.color.labelAlternative_A29D96,
                    )

                    Gap(8.dp)

                    Text(
                        text = stringResource(id = R.string.add_todo_start_time),
                        color = BbangZipTheme.color.labelAlternative_A29D96,
                        style = BbangZipTheme.typography.body2Medium,
                    )

                    Gap()

                    TimeSettingButton(onSettingTimeClick)
                }
            }
        },
    )
}

@Composable
private fun TimeSettingButton(onSettingTimeClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.noRippleClickable(onClick = onSettingTimeClick),
    ) {
        Text(
            text = stringResource(id = R.string.add_todo_start_time_not_set),
            color = BbangZipTheme.color.labelAlternative_A29D96,
            style = BbangZipTheme.typography.body1Medium,
        )

        Gap(8.dp)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = stringResource(id = R.string.add_todo_arrow_icon_description),
            tint = BbangZipTheme.color.labelAlternative_A29D96,
            modifier = Modifier.size(20.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTodoBottomSheetPreview() {
    val focusManager = LocalFocusManager.current

    var text by remember { mutableStateOf("") }
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
        }
        AddTodoBottomSheet(
            isBottomSheetVisible = isBottomSheetVisible,
            onDismissRequest = { isBottomSheetVisible = !isBottomSheetVisible },
            focusManager = focusManager,
            todo = text,
            onTodoChange = { text = it },
            onSettingTimeClick = { isBottomSheetVisible = !isBottomSheetVisible },
        )
    }
}
