package org.android.bbangzip.presentation.component.taskbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.component.taskbox.model.TaskBoxColors
import org.android.bbangzip.presentation.component.taskbox.model.TaskBoxTextStyle
import org.android.bbangzip.ui.theme.BbangZipTheme

object BbangZipTaskBoxDefaults {

    val CHECK_BOX_SIZE = 28.dp
    val CHECK_ICON_SIZE = 18.dp
    val MENU_ICON_SIZE = 20.dp
    val CLOCK_ICON_SIZE = 12.dp

    @Composable
    fun colors(
        unCheckedBoxColor: Color = BbangZipTheme.color.secondaryNormal_F6F1EE,
        checkIconColor: Color = BbangZipTheme.color.staticWhite_FFFFFF,
        menuIconColor: Color = BbangZipTheme.color.secondaryStrong_F2EAE4,
        taskTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        timeContentColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
    ): TaskBoxColors = remember(
        unCheckedBoxColor,
        checkIconColor,
        taskTextColor,
        timeContentColor,
    ){
        TaskBoxColors(
            unCheckedBoxColor = unCheckedBoxColor,
            checkIconColor = checkIconColor,
            menuIconColor = menuIconColor,
            taskTextColor = taskTextColor,
            timeContentColor = timeContentColor,
        )
    }

    @Composable
    fun textStyles(
        taskTextStyle: TextStyle = BbangZipTheme.typography.body2Medium,
        timeTextStyle: TextStyle = BbangZipTheme.typography.label4Regular,
    ): TaskBoxTextStyle = remember(
        taskTextStyle,
        timeTextStyle,
    )
        {
            TaskBoxTextStyle(
                taskTextStyle = taskTextStyle,
                timeTextStyle = timeTextStyle,
            )
        }
}