package org.android.bbangzip.presentation.component.timepicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

private const val DEFAULT_PADDING_ITEMS_COUNT = 3

data class DisplayTime(val amPm: AmPm, val hour: Int, val minute: Int)

sealed interface AmPm {
    val displayText: String

    object AM : AmPm {
        override val displayText: String = "오전"
    }

    object PM : AmPm {
        override val displayText: String = "오후"
    }
}

private fun AmPm.toPickerString(): String = this.displayText

private fun String.toAmPm(): AmPm = if (this == AmPm.PM.displayText) AmPm.PM else AmPm.AM

private val amPmItems: List<String> = listOf(AmPm.AM.toPickerString(), AmPm.PM.toPickerString())
private val hourItems = (1..12).map { it.toString() }
private val minuteItems = (0..59 step 5).map { it.toString().padStart(2, '0') }

@Composable
fun BbangZipTimePicker(
    modifier: Modifier = Modifier,
    initialTime: DisplayTime = DisplayTime(AmPm.AM, 0, 0),
    onTimeSelected: (DisplayTime) -> Unit,
    paddingItemsCount: Int = DEFAULT_PADDING_ITEMS_COUNT,
    itemHeight: Dp = BbangZipWheelPickerDefaults.DefaultItemHeight,
    colors: WheelPickerColors = BbangZipWheelPickerDefaults.colors(),
    typography: WheelPickerTypography = BbangZipWheelPickerDefaults.typography(),
) {
    var selectedHour by remember { mutableIntStateOf(initialTime.hour) }
    var selectedMinute by remember { mutableIntStateOf(initialTime.minute) }
    var selectedAmPm by remember { mutableStateOf(initialTime.amPm) }

    LaunchedEffect(selectedAmPm, selectedHour, selectedMinute) {
        onTimeSelected(DisplayTime(amPm = selectedAmPm, hour = selectedHour, minute = selectedMinute))
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BbangZipWheelPicker(
            modifier = Modifier.weight(weight = BbangZipWheelPickerDefaults.AM_PM_WEIGHT),
            items = amPmItems,
            initialIndex = amPmItems.indexOf(initialTime.amPm.toPickerString()),
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { _, item ->
                selectedAmPm = item.toAmPm()
            },
            alignment = Alignment.End,
            colors = colors,
            typography = typography,
        )

        BbangZipWheelPicker(
            modifier = Modifier.weight(weight = BbangZipWheelPickerDefaults.HOUR_WEIGHT),
            items = hourItems,
            initialIndex = hourItems.indexOf(initialTime.hour.toString()),
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { _, item ->
                selectedHour = item.toInt()
            },
            alignment = Alignment.CenterHorizontally,
            colors = colors,
            typography = typography,
        )

        BbangZipWheelPicker(
            modifier = Modifier.weight(weight = BbangZipWheelPickerDefaults.MINUTE_WEIGHT),
            items = minuteItems,
            initialIndex = minuteItems.indexOf(initialTime.minute.toString().padStart(2, '0')),
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { _, item ->
                selectedMinute = item.toInt()
            },
            alignment = Alignment.Start,
            colors = colors,
            typography = typography,
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
                initialTime = DisplayTime(amPm = AmPm.PM, hour = 2, minute = 30),
                onTimeSelected = { time ->
                    selectedDisplayTime = time
                },
            )

            selectedDisplayTime?.let { time ->
                Text(
                    text = "선택된 시간: $time",
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
        }
    }
}
