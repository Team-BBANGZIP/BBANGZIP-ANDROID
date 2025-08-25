package org.android.bbangzip.presentation.component.calendar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.protobuf.copy
import org.android.bbangzip.ui.theme.BbangZipTheme

/**
 * 주간 달력 컴포넌트([BbangZipWeeklyCalendar])의 색상을 정의하는 데이터 클래스
 *
 * 이 클래스의 인스턴스는 [BbangZipWeeklyCalendarDefaults.colors] 함수를 통해 생성할 수 있습니다.
 *
 * @property selectedDayCellBackgroundColor 선택된 날짜 셀의 배경색입니다.
 * @property defaultDayCellBackgroundColor 기본 날짜 셀의 배경색입니다. (선택되지 않았을 경우)
 * @property selectedDayCellDateTextColor 선택된 날짜 셀의 날짜 텍스트 색상입니다.
 * @property defaultDayCellDateTextColor 기본 날짜 셀의 날짜 텍스트 색상입니다. (선택되지 않았을 경우)
 * @property headerNavigationIconColor 달력 헤더의 이전/다음 주 이동 아이콘 색상입니다.
 * @property headerDateColor 달력 헤더의 년/월 텍스트 색상입니다.
 */
@Immutable
data class WeeklyCalendarColors(
    val selectedDayCellBackgroundColor: Color,
    val defaultDayCellBackgroundColor: Color,
    val selectedDayCellDateTextColor: Color,
    val defaultDayCellDateTextColor: Color,
    val headerNavigationIconColor: Color,
    val headerDateColor: Color,
) {
    /**
     * 날짜 셀의 선택 여부에 따라 적절한 배경색을 반환하는 함수
     *
     * @param isSelected 해당 날짜 셀이 선택되었는지 여부입니다.
     * @return 계산된 [Color] 값입니다.
     */
    fun dayCellBackgroundColor(isSelected: Boolean): Color =
        if (isSelected) selectedDayCellBackgroundColor else defaultDayCellBackgroundColor

    /**
     * 날짜 셀의 선택 여부에 따라 적절한 날짜 텍스트 색상을 반환하는 함수
     *
     * @param isSelected 해당 날짜 셀이 선택되었는지 여부입니다.
     * @return 계산된 [Color] 값입니다.
     */
    fun dayCellDateTextColor(isSelected: Boolean): Color =
        if (isSelected) selectedDayCellDateTextColor else defaultDayCellDateTextColor
}

/**
 * 주간 달력 컴포넌트([BbangZipWeeklyCalendar])의 텍스트 스타일을 정의하는 데이터 클래스
 *
 * 이 클래스의 인스턴스는 [BbangZipWeeklyCalendarDefaults.typography] 함수를 통해 생성할 수 있습니다.
 *
 * @property selectedDayCellDateTextStyle 선택된 날짜 셀의 날짜 텍스트 스타일입니다.
 * @property defaultDayCellDateTextStyle 기본 날짜 셀의 날짜 텍스트 스타일입니다. (선택되지 않았을 경우)
 * @property headerDateTextStyle 달력 헤더의 년/월 텍스트 스타일입니다.
 */
@Immutable
data class WeeklyCalendarTypography(
    val selectedDayCellDateTextStyle: TextStyle,
    val defaultDayCellDateTextStyle: TextStyle,
    val headerDateTextStyle: TextStyle,
) {
    /**
     * 날짜 셀의 선택 여부에 따라 적절한 날짜 텍스트 스타일을 반환하는 함수
     *
     * @param isSelected 해당 날짜 셀이 선택되었는지 여부입니다.
     * @return 계산된 [TextStyle] 값입니다.
     */
    fun dayCellDateTextStyle(isSelected: Boolean): TextStyle =
        if (isSelected) selectedDayCellDateTextStyle else defaultDayCellDateTextStyle
}

/**
 * [BbangZipWeeklyCalendar] 컴포저블의 디자인 기본값들을 제공하는 `object`
 */
object BbangZipWeeklyCalendarDefaults {
    /** 날짜 셀의 모서리 둥글기 값입니다. */
    val DayCellCornerRadius: Dp = 10.dp

    /** 날짜 셀의 기본 모양입니다. [DayCellCornerRadius]를 사용한 둥근 모서리 사각형입니다. */
    val DayCellShape: Shape = RoundedCornerShape(DayCellCornerRadius)

    /** 날짜 셀 내부의 상하 여백(padding)입니다. */
    val DayCellVerticalPadding: Dp = 8.dp

    /** 주(Week) 내에서 날짜 셀들 사이의 간격입니다. */
    val DayCellSpacing: Dp = 9.dp

    /** 달력 헤더와 주(Week) 행 사이의 간격입니다. */
    val HeaderToWeekRowGap: Dp = 20.dp

    /** 달력 헤더의 년/월 텍스트와 네비게이션 아이콘 사이의 간격입니다. */
    val MonthToNavigationIconGap: Dp = 20.dp

    /** 달력 헤더의 네비게이션 아이콘들(이전 주, 다음 주) 사이의 간격입니다. */
    val NavigationIconGap: Dp = 20.dp

    /** 날짜 셀 내부에서 요일 텍스트와 일자 텍스트 사이의 간격입니다. */
    val DayOfWeekToDayOfMonthGap: Dp = 8.dp

    /**
     * [BbangZipWeeklyCalendar]의 기본 색상 구성을 생성하는 함수
     *
     * 각 색상 파라미터는 [BbangZipTheme]에서 기본값을 가져오며, 필요에 따라 재정의할 수 있습니다([copy]).
     *
     * @param selectedDayCellBackgroundColor 선택된 날짜 셀의 배경색입니다.
     * @param defaultDayCellBackgroundColor 기본 날짜 셀의 배경색입니다.
     * @param defaultDayCellDateTextColor 기본 날짜 셀의 날짜 텍스트 색상입니다.
     * @param selectedDayCellDateTextColor 선택된 날짜 셀의 날짜 텍스트 색상입니다.
     * @param headerNavigationIconColor 달력 헤더 네비게이션 아이콘의 색상입니다.
     * @param headerDateColor 달력 헤더 텍스트의 색상입니다.
     * @return [WeeklyCalendarColors] 인스턴스를 반환합니다.
     */
    @Composable
    fun colors(
        selectedDayCellBackgroundColor: Color = BbangZipTheme.color.secondaryStrong_F2EAE4,
        defaultDayCellBackgroundColor: Color = Color.Transparent,
        defaultDayCellDateTextColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        selectedDayCellDateTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        headerNavigationIconColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        headerDateColor: Color = BbangZipTheme.color.labelNeutral_706A63,
    ): WeeklyCalendarColors {
        // 동일한 색상 입력에 대해 WeeklyCalendarColors 인스턴스를 캐싱하여 불필요한 재생성을 방지합니다.
        return remember(
            selectedDayCellBackgroundColor,
            defaultDayCellBackgroundColor,
            defaultDayCellDateTextColor,
            selectedDayCellDateTextColor,
            headerNavigationIconColor,
            headerDateColor,
        ) {
            WeeklyCalendarColors(
                selectedDayCellBackgroundColor = selectedDayCellBackgroundColor,
                defaultDayCellBackgroundColor = defaultDayCellBackgroundColor,
                selectedDayCellDateTextColor = selectedDayCellDateTextColor,
                defaultDayCellDateTextColor = defaultDayCellDateTextColor,
                headerNavigationIconColor = headerNavigationIconColor,
                headerDateColor = headerDateColor,
            )
        }
    }

    /**
     * [BbangZipWeeklyCalendar]의 기본 텍스트 스타일 구성을 생성하는 함수.
     *
     * 각 텍스트 스타일 파라미터는 [BbangZipTheme]에서 기본값을 가져오며, 필요에 따라 재정의할 수 있습니다([copy]).
     *
     * @param selectedDayCellDateTextStyle 선택된 날짜 셀의 날짜 텍스트 스타일입니다.
     * @param defaultDayCellDateTextStyle 기본 날짜 셀의 날짜 텍스트 스타일입니다.
     * @param headerDateTextStyle 달력 헤더의 년/월 텍스트 스타일입니다.
     * @return [WeeklyCalendarTypography] 인스턴스를 반환합니다.
     */
    @Composable
    fun typography(
        selectedDayCellDateTextStyle: TextStyle = BbangZipTheme.typography.label1SemiBold,
        defaultDayCellDateTextStyle: TextStyle = BbangZipTheme.typography.label2Regular,
        headerDateTextStyle: TextStyle = BbangZipTheme.typography.subTitle1Medium,
    ): WeeklyCalendarTypography {
        // 동일한 텍스트 스타일 입력에 대해 WeeklyCalendarTypography 인스턴스를 캐싱합니다.
        return remember(
            selectedDayCellDateTextStyle,
            defaultDayCellDateTextStyle,
            headerDateTextStyle,
        ) {
            WeeklyCalendarTypography(
                selectedDayCellDateTextStyle = selectedDayCellDateTextStyle,
                defaultDayCellDateTextStyle = defaultDayCellDateTextStyle,
                headerDateTextStyle = headerDateTextStyle,
            )
        }
    }
}
