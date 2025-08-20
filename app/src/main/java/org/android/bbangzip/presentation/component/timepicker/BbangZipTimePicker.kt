package org.android.bbangzip.presentation.component.timepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

data class DisplayTime(val amPm: String, val hour: Int, val minute: Int)

@Composable
fun BbangZipTimePicker(
    modifier: Modifier = Modifier,
    initialTime: DisplayTime = DisplayTime("오전", 0, 0),
    onTimeSelected: (DisplayTime) -> Unit,
    paddingItemsCount: Int = 3,
    itemHeight: Dp = 32.dp,
) {
    val amPmItems = remember { listOf("오전", "오후") }
    val hourItems = remember { (1..12).map { it.toString() } }
    val minuteItems = remember { (0..55 step 5).map { it.toString().padStart(2, '0') } }

    var pickerState by remember(initialTime) {
        mutableStateOf(
            initialTime,
        )
    }

    LaunchedEffect(pickerState) {
        onTimeSelected(pickerState)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BbangZipWheelPicker(
            modifier = Modifier.weight(125f / 335f),
            items = amPmItems,
            initialIndex = amPmItems.indexOf(pickerState.amPm),
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { _, item ->
                pickerState = pickerState.copy(amPm = item)
            },
            alignment = Alignment.End,
        )

        BbangZipWheelPicker(
            modifier = Modifier.weight(95f / 335f),
            items = hourItems,
            initialIndex = hourItems.indexOf(pickerState.hour.toString().padStart(2, '0')),
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { _, item ->
                pickerState = pickerState.copy(hour = item.toInt())
            },
            alignment = Alignment.CenterHorizontally,
        )

        BbangZipWheelPicker(
            modifier = Modifier.weight(115f / 335f),
            items = minuteItems,
            initialIndex = minuteItems.indexOf(pickerState.minute.toString().padStart(2, '0')),
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { _, item ->
                pickerState = pickerState.copy(minute = item.toInt())
            },
            alignment = Alignment.Start,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BbangZipTimePickerPreview() {
    BBANGZIPANDROIDTheme {
        var selectedDisplayTime by remember { mutableStateOf<DisplayTime?>(null) }

        Column(modifier = Modifier.padding(16.dp)) {
            BbangZipTimePicker(
                initialTime = DisplayTime("오후", 2, 30),
                onTimeSelected = { time ->
                    selectedDisplayTime = time
                },
            )

            selectedDisplayTime?.let { time ->
                Text(
                    text = "선택된 시간: ${time.amPm} ${time.hour} : ${time.minute.toString().padStart(2, '0')}",
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
        }
    }
}
