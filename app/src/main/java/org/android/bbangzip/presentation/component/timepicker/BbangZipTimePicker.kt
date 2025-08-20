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

private data class BbangZipTimePickerState(
    val selectedAmPmIndex: Int,
    val selectedHourIndex: Int,
    val selectedMinuteIndex: Int,
) {
    fun toDisplayTime(
        amPmItems: List<String>,
        hourItems: List<String>,
        minuteItems: List<String>,
    ): DisplayTime {
        return DisplayTime(
            amPm = amPmItems[selectedAmPmIndex],
            hour = hourItems[selectedHourIndex].toInt(),
            minute = minuteItems[selectedMinuteIndex].toInt(),
        )
    }
}

data class DisplayTime(val amPm: String, val hour: Int, val minute: Int)

@Composable
fun BbangZipTimePicker(
    modifier: Modifier = Modifier,
    initialTime: DisplayTime = DisplayTime("AM", 0, 0),
    onTimeSelected: (DisplayTime) -> Unit,
    paddingItemsCount: Int = 3,
    itemHeight: Dp = 32.dp,
) {
    val amPmItems = remember { listOf("오전", "오후") }
    val hourItems = remember { (1..12).map { it.toString() } }
    val minuteItems = remember { (0..55 step 5).map { it.toString().padStart(2, '0') } }

    var pickerState by remember(initialTime) {
        mutableStateOf(
            BbangZipTimePickerState(
                selectedAmPmIndex = amPmItems.indexOf(initialTime.amPm).coerceAtLeast(0),
                selectedHourIndex = hourItems.indexOf(initialTime.hour.toString()).coerceAtLeast(0),
                selectedMinuteIndex = minuteItems.indexOf(initialTime.minute.toString().padStart(2, '0')).coerceAtLeast(0),
            ),
        )
    }

    LaunchedEffect(pickerState) {
        onTimeSelected(pickerState.toDisplayTime(amPmItems, hourItems, minuteItems))
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        BbangZipWheelPicker(
            modifier = Modifier.weight(125f / 335f),
            items = amPmItems,
            initialIndex = pickerState.selectedAmPmIndex,
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { index, _ ->
                pickerState = pickerState.copy(selectedAmPmIndex = index)
            },
            alignment = Alignment.End,
        )

        BbangZipWheelPicker(
            modifier = Modifier.weight(95f / 335f),
            items = hourItems,
            initialIndex = pickerState.selectedHourIndex,
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { index, _ ->
                pickerState = pickerState.copy(selectedHourIndex = index)
            },
            alignment = Alignment.CenterHorizontally,
        )

        BbangZipWheelPicker(
            modifier = Modifier.weight(115f / 335f),
            items = minuteItems,
            initialIndex = pickerState.selectedMinuteIndex,
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { index, _ ->
                pickerState = pickerState.copy(selectedMinuteIndex = index)
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
