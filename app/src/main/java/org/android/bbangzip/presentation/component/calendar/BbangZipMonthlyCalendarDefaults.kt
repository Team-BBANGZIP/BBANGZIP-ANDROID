package org.android.bbangzip.presentation.component.calendar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme

@Immutable
data class MonthlyCalendarColors(
    val selectedDayCellBackgroundColor: Color,
    val todayCellBackgroundColor: Color,
    val defaultDayCellBackgroundColor: Color,
    val selectedDayCellTextColor: Color,
    val notCurrentMonthDayCellTextColor: Color,
    val defaultDayCellTextColor: Color,
    val dayOfWeekTextColor: Color,
    val headerNavigationIconColor: Color,
    val headerTextColor: Color
) {
    internal fun dayCellBackgroundColor(isSelected: Boolean, isToday: Boolean): Color {
        return when {
            isSelected -> selectedDayCellBackgroundColor
            isToday -> todayCellBackgroundColor
            else -> defaultDayCellBackgroundColor
        }
    }

    internal fun dayCellTextColor(isSelected: Boolean, isCurrentMonth: Boolean): Color {
        return when {
            isSelected -> selectedDayCellTextColor
            !isCurrentMonth -> notCurrentMonthDayCellTextColor
            else -> defaultDayCellTextColor
        }
    }
}

@Immutable
data class MonthlyCalendarTypography(
    val dayCellDateTextStyle: TextStyle,
    val dayOfWeekTextStyle: TextStyle,
    val headerTextStyle: TextStyle
)

object BbangZipMonthlyCalendarDefaults {
    val DayCellShape: Shape = CircleShape

    val HeaderToDayOfWeekGap: Dp = 20.dp
    val DayOfWeekToDayOfMonthGap: Dp = 6.dp
    val MonthToNavigationIconGap: Dp = 20.dp
    val NavigationIconGap: Dp = 20.dp
    val DayCellVerticalSpacing: Dp = 4.dp
    val DayCellHorizontalSpacing: Dp = 9.dp


    @Composable
    fun colors(
        selectedDayCellBackgroundColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        todayCellBackgroundColor: Color = BbangZipTheme.color.backgroundAlternative_FAF6F3,
        defaultDayCellBackgroundColor: Color = Color.Transparent,
        selectedDayCellTextColor: Color = BbangZipTheme.color.staticWhite_FFFFFF,
        notCurrentMonthDayCellTextColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        defaultDayCellTextColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        dayOfWeekHeaderTextColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        headerNavigationIconColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        headerTextColor: Color = BbangZipTheme.color.labelNeutral_706A63
    ): MonthlyCalendarColors {
        return remember(
            selectedDayCellBackgroundColor,
            todayCellBackgroundColor,
            defaultDayCellBackgroundColor,
            selectedDayCellTextColor,
            notCurrentMonthDayCellTextColor,
            defaultDayCellTextColor,
            dayOfWeekHeaderTextColor,
            headerNavigationIconColor,
            headerTextColor
        ) {
            MonthlyCalendarColors(
                selectedDayCellBackgroundColor = selectedDayCellBackgroundColor,
                todayCellBackgroundColor = todayCellBackgroundColor,
                defaultDayCellBackgroundColor = defaultDayCellBackgroundColor,
                selectedDayCellTextColor = selectedDayCellTextColor,
                notCurrentMonthDayCellTextColor = notCurrentMonthDayCellTextColor,
                defaultDayCellTextColor = defaultDayCellTextColor,
                dayOfWeekTextColor = dayOfWeekHeaderTextColor,
                headerNavigationIconColor = headerNavigationIconColor,
                headerTextColor = headerTextColor
            )
        }
    }

    @Composable
    fun typography(
        dayCellTextStyle: TextStyle = BbangZipTheme.typography.label4Regular,
        dayOfWeekTextStyle: TextStyle = BbangZipTheme.typography.label4Regular,
        headerTextStyle: TextStyle = BbangZipTheme.typography.subTitle1Medium
    ): MonthlyCalendarTypography {
        return remember(
            dayCellTextStyle,
            dayOfWeekTextStyle,
            headerTextStyle
        ) {
            MonthlyCalendarTypography(
                dayCellDateTextStyle = dayCellTextStyle,
                dayOfWeekTextStyle = dayOfWeekTextStyle,
                headerTextStyle = headerTextStyle
            )
        }
    }
}

