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
import org.android.bbangzip.presentation.util.extension.to12HourText
import org.android.bbangzip.presentation.util.extension.toAmPmText
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import java.time.LocalTime

private const val DEFAULT_PADDING_ITEMS_COUNT = 3

enum class AmPm(val displayText: String) {
    AM("오전"),
    PM("오후")
}

private val amPmItems: List<String> = listOf(AmPm.AM.displayText, AmPm.PM.displayText)
private val hourItems = (1..12).map { it.toString() }
private val minuteItems = (0..59).map { it.toString().padStart(2, '0') }


/**
 * 시간 선택을 위한 [BbangZipWheelPicker]를 사용하는 컴포저블
 * 오전/오후, 시간, 분을 각각 선택할 수 있습니다.
 *
 * @param initialTime 초기에 설정될 시간입니다.
 * @param onTimeSelected 시간이 선택될 때 호출되는 콜백 함수입니다. 선택된 [LocalTime] 객체를 전달받습니다.
 * @param modifier 컴포저블에 적용할 [Modifier]입니다.
 * @param paddingItemsCount 각 휠 피커의 아이템 리스트 위아래에 추가될 빈 아이템의 개수입니다.
 * @param itemHeight 각 휠 피커의 아이템 높이입니다.
 * @param colors 각 휠 피커의 색상 설정을 담고 있는 [WheelPickerColors] 객체입니다.
 * @param typography 각 휠 피커의 텍스트 스타일 설정을 담고 있는 [WheelPickerTypography] 객체입니다.
 */
@Composable
fun BbangZipTimePicker(
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    paddingItemsCount: Int = DEFAULT_PADDING_ITEMS_COUNT,
    itemHeight: Dp = BbangZipWheelPickerDefaults.DefaultItemHeight,
    colors: WheelPickerColors = BbangZipWheelPickerDefaults.colors(),
    typography: WheelPickerTypography = BbangZipWheelPickerDefaults.typography(),
) {
    var selectedHour by remember { mutableIntStateOf(initialTime.to12HourText()) }
    var selectedMinute by remember { mutableIntStateOf(initialTime.minute) }
    var selectedAmPm by remember { mutableStateOf(initialTime.toAmPmText()) }

    LaunchedEffect(selectedAmPm, selectedHour, selectedMinute) {
        onTimeSelected(
            LocalTime.of(
                if (selectedAmPm == AmPm.PM.displayText) selectedHour + 12 else selectedHour,
                selectedMinute
            )
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Am/Pm 휠 피커
        BbangZipWheelPicker(
            modifier = Modifier.weight(weight = BbangZipWheelPickerDefaults.AM_PM_WEIGHT),
            items = amPmItems,
            initialIndex = amPmItems.indexOf(initialTime.toAmPmText()),
            paddingItemsCount = paddingItemsCount,
            itemHeight = itemHeight,
            onItemSelected = { _, item ->
                selectedAmPm = item
            },
            alignment = Alignment.End,
            colors = colors,
            typography = typography,
        )

        //시각 선택 휠 피커
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

        //분 선택 휠 피커
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
        var selectedDisplayTime by remember { mutableStateOf<LocalTime?>(null) }

        Column(modifier = Modifier.padding(16.dp)) {
            BbangZipTimePicker(
                initialTime = LocalTime.of(13, 30),
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
