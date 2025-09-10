package org.android.bbangzip.presentation.component.taskbox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
 * 작업(할 일)을 표시하는 컴포저블
 *
 * 작업 완료 여부 체크 및 메뉴 클릭 이벤트 처리가 가능합니다.
 * 드래그 앤 드롭 기능은 현재 미구현 상태입니다.
 *
 * @param task 작업 내용 텍스트
 * @param categoryColor 카테고리 색상으로, 완료 시 체크박스 배경색으로 사용됨.
 * @param modifier 이 컴포저블에 적용할 [Modifier]
 * @param isCompleted 작업 완료 여부를 나타냄. `true`일 경우 체크 아이콘이 표시됨.
 * @param startTime 작업 시작 시간. AM/PM 형식으로 표시됨.
 * @param colors [TaskBoxColors]를 통해 컴포저블의 색상을 정의
 * @param textStyles [TaskBoxTextStyle]을 통해 컴포저블의 텍스트 스타일을 정의
 * @param onCheckBoxClick 체크박스 클릭 시 호출될 콜백
 * @param onMenuClick 메뉴 아이콘 클릭 시 호출될 콜백
 * @param onHeightMeasure 컴포저블의 높이 측정 시 호출될 콜백
 */
@Composable
fun BbangZipTaskBox(
    task: String,
    categoryColor: Color,
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    isLast: Boolean = false,
    startTime: LocalTime? = null,
    colors: TaskBoxColors = BbangZipTaskBoxDefaults.colors(),
    textStyles: TaskBoxTextStyle = BbangZipTaskBoxDefaults.textStyles(),
    onCheckBoxClick: (Boolean) -> Unit = {},
    onMenuClick: () -> Unit = {},
    onHeightMeasure: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(top = BbangZipTaskBoxDefaults.TASK_CONTENT_TOP_PADDING),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            TaskCheckBox(
                isCompleted = isCompleted,
                checkedBoxColor = categoryColor,
                uncheckedBoxColor = colors.unCheckedBoxColor,
                checkIconColor = colors.checkIconColor,
                onClick = { onCheckBoxClick(!isCompleted) },
            )

            Gap(width = BbangZipTaskBoxDefaults.GAP_CHECKBOX_TO_CONTENT)

            Column {
                Row {
                    TaskContent(
                        task = task,
                        startTime = startTime,
                        taskTextColor = colors.taskTextColor,
                        timeContentColor = colors.timeContentColor,
                        textStyles = textStyles,
                        modifier = Modifier.weight(1f),
                    )

                    Gap(width = BbangZipTaskBoxDefaults.GAP_CONTENT_TO_MENU)

                    Icon(
                        painter = painterResource(R.drawable.ic_meatball_menu_default_24),
                        contentDescription = stringResource(id = R.string.task_box_menu_description),
                        modifier =
                            Modifier
                                .size(BbangZipTaskBoxDefaults.MENU_ICON_SIZE)
                                .noRippleClickable(onClick = onMenuClick)
                                .align(Alignment.CenterVertically),
                        tint = colors.menuIconColor,
                    )
                }

                if (!isLast) {
                    Gap(height = BbangZipTaskBoxDefaults.GAP_CONTENT_TO_DIVIDER)

                    HorizontalDivider(
                        color = colors.dividerColor,
                        thickness = BbangZipTaskBoxDefaults.HORIZONTAL_DIVIDER_THICKNESS,
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskCheckBox(
    isCompleted: Boolean,
    checkedBoxColor: Color,
    uncheckedBoxColor: Color,
    checkIconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(BbangZipTaskBoxDefaults.CHECK_BOX_SIZE)
                .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_bread_default_14),
            contentDescription = if (isCompleted) stringResource(R.string.task_box_completed_task_description) else stringResource(R.string.task_box_uncompleted_task_description),
            tint = if (isCompleted) checkedBoxColor else uncheckedBoxColor,
            modifier = Modifier.size(BbangZipTaskBoxDefaults.CHECK_ICON_SIZE),
        )
        if (isCompleted) {
            Icon(
                painter = painterResource(R.drawable.ic_check_default_24),
                contentDescription = stringResource(R.string.task_box_completed_task_description),
                modifier = Modifier.size(BbangZipTaskBoxDefaults.CHECK_ICON_SIZE),
                tint = checkIconColor,
            )
        }
    }
}

@Composable
private fun TaskContent(
    task: String,
    startTime: LocalTime?,
    taskTextColor: Color,
    timeContentColor: Color,
    textStyles: TaskBoxTextStyle,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = BbangZipTaskBoxDefaults.TASK_TEXT_TOP_PADDING),
    ) {
        Text(
            text = task,
            style = textStyles.taskTextStyle,
            color = taskTextColor,
        )

        if (startTime != null) {
            Gap(height = BbangZipTaskBoxDefaults.GAP_TASK_TO_TIME)

            TaskTimeDisplay(
                time = startTime,
                textColor = timeContentColor,
                textStyle = textStyles.timeTextStyle,
            )
        }
    }
}

@Composable
private fun TaskTimeDisplay(
    time: LocalTime,
    textColor: Color,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    val displayTime =
        remember(time) {
            time.formatTimeWithAmPm()
        }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_clock_default_24),
            contentDescription = stringResource(id = R.string.task_box_time_description),
            modifier = Modifier.size(BbangZipTaskBoxDefaults.CLOCK_ICON_SIZE),
            tint = textColor,
        )

        Gap(width = BbangZipTaskBoxDefaults.GAP_TIME_ICON_TO_TEXT)

        Text(
            text = displayTime,
            style = textStyle,
            color = textColor,
        )
    }
}

@Preview(showBackground = true, name = "BbangZipTaskBox의 경우의 수")
@Composable
private fun BbangZipTaskBoxPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(30.dp),
        ) {
            BbangZipTaskBox(
                task = "두 줄 이상 표시되는 작업 텍스트입니다. 내용이 길어지면 자동으로 줄바꿈됩니다.",
                categoryColor = Color.Red,
            )

            BbangZipTaskBox(
                task = "한 줄 작업 텍스트 (완료됨)",
                categoryColor = Color.Blue,
                isCompleted = true,
            )

            BbangZipTaskBox(
                task = "시간이 포함된 작업 (완료됨)",
                categoryColor = Color.Green,
                isCompleted = true,
                startTime = LocalTime.of(14, 30),
            )

            BbangZipTaskBox(
                task = "시간만 포함된 작업",
                categoryColor = Color.Magenta,
                startTime = LocalTime.now(),
                isLast = true,
            )
        }
    }
}
