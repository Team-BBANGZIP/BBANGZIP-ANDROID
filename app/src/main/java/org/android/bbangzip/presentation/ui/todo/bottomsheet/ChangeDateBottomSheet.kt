package org.android.bbangzip.presentation.ui.todo.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import org.android.bbangzip.presentation.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.component.calendar.MonthlyCalendar
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeDateBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    date: LocalDate,
    modifier: Modifier = Modifier,
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.change_date_title),
                color = BbangZipTheme.color.labelAlternative_A29D96,
                style = BbangZipTheme.typography.title3SemiBold,
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Gap(24.dp)

                MonthlyCalendar(
                    initialDate = date,
                    initialYearMonth = YearMonth.of(date.year, date.month),
                )

                Gap(40.dp)

                BbangzipBaseButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp),
                    onClick = {},
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = BbangZipTheme.color.staticWhite_FFFFFF
                        )
                    },
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryNormal_897869,
                        ),
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_save),
                            style = BbangZipTheme.typography.body2Medium,
                            color = BbangZipTheme.color.staticWhite_FFFFFF,
                        )
                    },
                )

                Gap(height = 8.dp)
            }
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChangeDateBottomSheetPreview() {
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
        ChangeDateBottomSheet(
            isBottomSheetVisible = isBottomSheetVisible,
            onDismissRequest = { isBottomSheetVisible = !isBottomSheetVisible },
            date = LocalDate.now(),
        )
    }
}