package org.android.bbangzip.presentation.component.calendar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme // 테마 import
import org.android.bbangzip.ui.theme.BbangZipTypography // 타이포그래피가 여기에 정의되어 있다고 가정합니다

object BbangZipWeeklyCalendarDefaults {

    val DayCellCornerRadius: Dp = 10.dp
    val DayCellShape: Shape = RoundedCornerShape(DayCellCornerRadius)

    val DayCellVerticalPadding: Dp = 8.dp

    val DayCellSpacing: Dp = 9.dp

    val HeaderToWeekRowGap: Dp = 20.dp
    val MonthToNavigationIconGap: Dp = 20.dp
    val NavigationIconGap: Dp = 20.dp
    val DayOfWeekToDayOfMonthGap: Dp = 8.dp

    @Immutable
    data class Colors(
        val selectedDayCellBackgroundColor: Color,
        val defaultDayCellBackgroundColor: Color,
        val selectedDayCellDateTextColor: Color,
        val defaultDayCellDateTextColor: Color,
        val headerNavigationIconColor: Color,
        val headerDateColor: Color
    ){
        fun dayCellBackgroundColor(isSelected: Boolean): Color =
            if (isSelected) selectedDayCellBackgroundColor else defaultDayCellBackgroundColor
        fun dayCellDateTextColor(isSelected: Boolean): Color =
            if (isSelected) selectedDayCellDateTextColor else defaultDayCellDateTextColor
    }

    @Immutable
    data class Typography(
        val selectedDayCellDateTextStyle: TextStyle,
        val defaultDayCellDateTextStyle: TextStyle,
        val headerDateTextStyle: TextStyle
    ){
        fun dayCellDateTextStyle(isSelected: Boolean): TextStyle =
            if (isSelected) selectedDayCellDateTextStyle else defaultDayCellDateTextStyle
    }

    @Composable
    fun colors(
        selectedDayCellBackgroundColor: Color = BbangZipTheme.color.secondaryStrong_F2EAE4,
        defaultDayCellBackgroundColor: Color = Color.Transparent,
        defaultDayCellDateTextColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        selectedDayCellDateTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        headerNavigationIconColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        headerDateColor: Color = BbangZipTheme.color.labelNeutral_706A63
    ): Colors {
        return remember(
            selectedDayCellBackgroundColor,
                    defaultDayCellBackgroundColor,
                    defaultDayCellDateTextColor,
                    selectedDayCellDateTextColor,
                    headerNavigationIconColor,
                    headerDateColor,
        ){
            Colors(
                selectedDayCellBackgroundColor = selectedDayCellBackgroundColor,
                defaultDayCellBackgroundColor = defaultDayCellBackgroundColor,
                selectedDayCellDateTextColor = defaultDayCellDateTextColor,
                defaultDayCellDateTextColor = selectedDayCellDateTextColor,
                headerNavigationIconColor = headerNavigationIconColor,
                headerDateColor = headerDateColor
            )
        }
    }

    @Composable
    fun typography(
        selectedDayCellDateTextStyle: TextStyle = BbangZipTheme.typography.label1SemiBold,
        defaultDayCellDateTextStyle: TextStyle = BbangZipTheme.typography.label2Regular,
        headerDateTextStyle: TextStyle = BbangZipTheme.typography.subTitle1Medium,
    ): Typography{
        return remember(
            selectedDayCellDateTextStyle,
                    defaultDayCellDateTextStyle,
                    headerDateTextStyle,
        ){
            Typography(
                selectedDayCellDateTextStyle = selectedDayCellDateTextStyle,
                defaultDayCellDateTextStyle = defaultDayCellDateTextStyle,
                headerDateTextStyle = headerDateTextStyle,
            )
        }
    }
}
