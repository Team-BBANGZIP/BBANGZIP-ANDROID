package org.android.bbangzip.presentation.component.taskbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.component.taskbox.model.TaskBoxColors
import org.android.bbangzip.presentation.component.taskbox.model.TaskBoxTextStyle
import org.android.bbangzip.ui.theme.BbangZipTheme

/**
 * [BbangZipTaskBox]의 기본값(수치, 색상, 스타일)들을 정의하는 객체
 */
object BbangZipTaskBoxDefaults {
    // Component Size
    val CHECK_BOX_SIZE: Dp = 28.dp
    val CHECK_ICON_SIZE: Dp = 18.dp
    val MENU_ICON_SIZE: Dp = 20.dp
    val CLOCK_ICON_SIZE: Dp = 12.dp

    // Layout Gaps
    val GAP_CHECKBOX_TO_CONTENT: Dp = 8.dp
    val GAP_CONTENT_TO_MENU: Dp = 12.dp
    val GAP_TASK_TO_TIME = 4.dp
    val GAP_TIME_ICON_TO_TEXT: Dp = 3.dp

    // PADDING
    val TASK_CONTENT_TOP_PADDING = 4.dp

    /**
     * [BbangZipTaskBox]에 사용될 기본 [TaskBoxColors]를 생성
     *
     * @param unCheckedBoxColor 미완료 시 체크박스 배경색
     * @param checkIconColor 완료 시 체크 아이콘 색상
     * @param menuIconColor 메뉴 아이콘 색상
     * @param taskTextColor 작업 텍스트 색상
     * @param timeContentColor 시간 표시 텍스트 및 아이콘 색상
     * @return 기본 [TaskBoxColors] 객체
     */
    @Composable
    fun colors(
        unCheckedBoxColor: Color = BbangZipTheme.color.secondaryNormal_F6F1EE,
        checkIconColor: Color = BbangZipTheme.color.staticWhite_FFFFFF,
        menuIconColor: Color = BbangZipTheme.color.secondaryStrong_F2EAE4,
        taskTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        timeContentColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
    ): TaskBoxColors {
        return remember(
            unCheckedBoxColor,
            checkIconColor,
            menuIconColor,
            taskTextColor,
            timeContentColor,
        ) {
            TaskBoxColors(
                unCheckedBoxColor = unCheckedBoxColor,
                checkIconColor = checkIconColor,
                menuIconColor = menuIconColor,
                taskTextColor = taskTextColor,
                timeContentColor = timeContentColor,
            )
        }
    }

    /**
     * [BbangZipTaskBox]에 사용될 기본 [TaskBoxTextStyle]을 생성
     *
     * @param taskTextStyle 작업 텍스트 스타일
     * @param timeTextStyle 시간 텍스트 스타일
     * @return 기본 [TaskBoxTextStyle] 객체
     */
    @Composable
    fun textStyles(
        taskTextStyle: TextStyle = BbangZipTheme.typography.body2Medium,
        timeTextStyle: TextStyle = BbangZipTheme.typography.label4Regular,
    ): TaskBoxTextStyle {
        return remember(
            taskTextStyle,
            timeTextStyle,
        ) {
            TaskBoxTextStyle(
                taskTextStyle = taskTextStyle,
                timeTextStyle = timeTextStyle,
            )
        }
    }
}