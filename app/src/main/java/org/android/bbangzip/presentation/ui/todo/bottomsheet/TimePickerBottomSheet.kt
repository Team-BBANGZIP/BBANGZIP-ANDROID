package org.android.bbangzip.presentation.ui.todo.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.android.bbangzip.presentation.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.component.timepicker.BbangZipTimePicker
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
){
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "시작 시간 설정",
                color = BbangZipTheme.color.labelAlternative_A29D96,
                style = BbangZipTheme.typography.title3SemiBold,
            )
        },
        content = {
            BbangZipTimePicker(
                initialTime = LocalTime.of(12,0),
                onTimeSelected = onTimeSelected,
            )
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TimePickerBottomSheetPreview(){
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    BBANGZIPANDROIDTheme {
        Column(
            modifier = Modifier.fillMaxSize().systemBarsPadding()
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
                onTimeSelected = {},
            )
        }
    }
}