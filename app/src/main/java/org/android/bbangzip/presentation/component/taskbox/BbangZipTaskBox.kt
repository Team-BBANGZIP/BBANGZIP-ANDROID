package org.android.bbangzip.presentation.component.taskbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.taskbox.model.TaskBoxColors
import org.android.bbangzip.presentation.component.taskbox.model.TaskBoxTextStyle
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.formatTimeWithAmPm
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import java.time.LocalTime

/**
 * 각각의 작업을 나타낼 Box
 *
 * 작업 완료 여부 체크 및 드래그/드롭(미구현) 이벤트 처리가 가능
 *
 * @param task 작업 내용
 * @param categoryColor 카테고리 색상이 체크 박스의 색상이 됌
 * @param isCompleted 작업 완료 여부를 나타냄. true일 경우 체크 아이콘이 표시
 * @param startTime 작업의 시작 시간. 지정 시 AM/PM 형식으로 표시
 * @param onCheckBoxClick 체크박스 클릭 시 호출되는 콜백
 * @param onMenuClick 메뉴 아이콘 클릭 시 호출되는 콜백
 * @param onDrag 드래그 시작 시 호출되는 콜백
 * @param onDrop 드롭 완료 시 호출되는 콜백
 */
@Composable
fun BbangZipTaskBox(
    task: String,
    categoryColor: Color,
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    startTime: LocalTime? = null,
    colors: TaskBoxColors = BbangZipTaskBoxDefaults.colors(),
    textStyles: TaskBoxTextStyle = BbangZipTaskBoxDefaults.textStyles(),
    onCheckBoxClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onDrag: () -> Unit = {}, // TODO: 드래그 기능 구현 시 사용
    onDrop: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        CheckBox(
            isCompleted = isCompleted,
            checkedBoxColor = categoryColor,
            uncheckedBoxColor = colors.unCheckedBoxColor,
            checkIconColor = colors.checkIconColor,
            onCheckBoxClick = onCheckBoxClick,
        )

        Gap(8)

        TaskText(
            task = task,
            taskTextColor = colors.taskTextColor,
            timeContentColor = colors.timeContentColor,
            taskBoxTextStyle = textStyles,
            modifier = Modifier.weight(1f),
            startTime = startTime,
        )

        Gap(12)

        Icon(
            painter = painterResource(R.drawable.ic_meatball_menu_default_24),
            contentDescription = null,
            modifier =
                Modifier
                    .size(BbangZipTaskBoxDefaults.MENU_ICON_SIZE)
                    .noRippleClickable { onMenuClick() },
            tint = colors.menuIconColor,
        )
    }
}

@Composable
private fun CheckBox(
    checkedBoxColor: Color,
    uncheckedBoxColor: Color,
    checkIconColor: Color,
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    onCheckBoxClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier.noRippleClickable { onCheckBoxClick() },
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_bread_default_24),
            contentDescription = null,
            modifier =
                modifier
                    .size(BbangZipTaskBoxDefaults.CHECK_BOX_SIZE),
            tint = if (isCompleted) checkedBoxColor else uncheckedBoxColor,
        )
        if (isCompleted) {
            Box(
                modifier = Modifier.align(Alignment.Center),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_check_default_24),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(BbangZipTaskBoxDefaults.CHECK_ICON_SIZE),
                    tint = checkIconColor,
                )
            }
        }
    }
}

@Composable
private fun TaskText(
    task: String,
    taskTextColor: Color,
    timeContentColor: Color,
    taskBoxTextStyle: TaskBoxTextStyle,
    modifier: Modifier = Modifier,
    startTime: LocalTime? = null,
) {
    Column(
        modifier = modifier.padding(top = 4.dp),
    ) {
        Text(
            text = task,
            style = taskBoxTextStyle.taskTextStyle,
            color = taskTextColor,
        )

        if (startTime != null) {
            Gap(4)

            Time(
                startTime = startTime,
                timeContentColor = timeContentColor,
                timeTextStyle = taskBoxTextStyle.timeTextStyle,
            )
        }
    }
}

@Composable
private fun Time(
    startTime: LocalTime,
    timeContentColor: Color,
    timeTextStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    val displayTime =
        remember(startTime) {
            startTime.formatTimeWithAmPm()
        }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_clock_default_24),
            contentDescription = null,
            modifier =
                Modifier
                    .size(BbangZipTaskBoxDefaults.CLOCK_ICON_SIZE),
            tint = timeContentColor,
        )

        Gap(3)

        Text(
            text = displayTime,
            style = timeTextStyle,
            color = timeContentColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BbangZipTaskBoxPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            BbangZipTaskBox(
                task = "두줄 텍스트 두줄 텍스트 두줄 텍스트 두줄 텍스트 두줄 텍스트",
                categoryColor = Color.Red,
            )

            BbangZipTaskBox(
                task = "한줄 텍스트",
                categoryColor = Color.Red,
                isCompleted = true,
            )

            BbangZipTaskBox(
                task = "한줄 텍스트",
                categoryColor = Color.Red,
                isCompleted = true,
                startTime = LocalTime.now(),
            )
        }
    }
}
