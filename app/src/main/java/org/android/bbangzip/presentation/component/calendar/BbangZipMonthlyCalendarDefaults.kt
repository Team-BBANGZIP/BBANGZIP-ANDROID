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
import com.google.protobuf.copy
import org.android.bbangzip.ui.theme.BbangZipTheme

/**
 * 월간 달력 컴포넌트([MonthlyCalendar])의 색상을 정의하는 데이터 클래스
 *
 * 이 클래스의 인스턴스는 [BbangZipMonthlyCalendarDefaults.colors] 함수를 통해 생성할 수 있습니다.
 *
 * @property selectedDayCellBackgroundColor 선택된 날짜 셀의 배경색입니다.
 * @property todayCellBackgroundColor "오늘" 날짜 셀의 배경색입니다. (선택되지 않았을 경우)
 * @property defaultDayCellBackgroundColor 기본 날짜 셀의 배경색입니다. (선택되지도 않고 오늘 날짜도 아닐 경우)
 * @property selectedDayCellTextColor 선택된 날짜 셀의 텍스트 색상입니다.
 * @property notCurrentMonthDayCellTextColor 현재 표시된 달에 속하지 않는 날짜 셀의 텍스트 색상입니다.
 * @property defaultDayCellTextColor 기본 날짜 셀의 텍스트 색상입니다. (현재 달에 속하며 선택되지 않았을 경우)
 * @property dayOfWeekTextColor 요일 헤더(예: 월, 화, 수)의 텍스트 색상입니다.
 * @property headerNavigationIconColor 달력 헤더의 이전/다음 달 이동 아이콘 색상입니다.
 * @property headerTextColor 달력 헤더의 년/월 텍스트 색상입니다.
 */
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
    val headerTextColor: Color,
) {
    /**
     * 날짜 셀의 상태에 따라 적절한 배경색을 반환하는 함수
     *
     * @param isSelected 해당 날짜 셀이 선택되었는지 여부입니다.
     * @param isToday 해당 날짜 셀이 "오늘"인지 여부입니다.
     * @return 계산된 [Color] 값입니다.
     */
    internal fun dayCellBackgroundColor(
        isSelected: Boolean,
        isToday: Boolean,
    ): Color {
        return when {
            isSelected -> selectedDayCellBackgroundColor
            isToday -> todayCellBackgroundColor
            else -> defaultDayCellBackgroundColor
        }
    }

    /**
     * 날짜 셀의 상태에 따라 적절한 텍스트 색상을 반환하는 함수
     *
     * @param isSelected 해당 날짜 셀이 선택되었는지 여부입니다.
     * @param isCurrentMonth 해당 날짜 셀이 현재 표시된 달에 속하는지 여부입니다.
     * @return 계산된 [Color] 값입니다.
     */
    internal fun dayCellTextColor(
        isSelected: Boolean,
        isCurrentMonth: Boolean,
    ): Color {
        return when {
            isSelected -> selectedDayCellTextColor
            !isCurrentMonth -> notCurrentMonthDayCellTextColor
            else -> defaultDayCellTextColor
        }
    }
}

/**
 * 월간 달력 컴포넌트([MonthlyCalendar])의 텍스트 스타일을 정의하는 데이터 클래스
 *
 * 이 클래스의 인스턴스는 [BbangZipMonthlyCalendarDefaults.typography] 함수를 통해 생성할 수 있습니다.
 *
 * @property dayCellDateTextStyle 날짜 셀의 일자(예: 1, 2, 3) 텍스트 스타일입니다.
 * @property dayOfWeekTextStyle 요일 헤더(예: 월, 화, 수)의 텍스트 스타일입니다.
 * @property headerTextStyle 달력 헤더의 년/월 텍스트 스타일입니다.
 */
@Immutable
data class MonthlyCalendarTypography(
    val dayCellDateTextStyle: TextStyle,
    val dayOfWeekTextStyle: TextStyle,
    val headerTextStyle: TextStyle,
)

/**
 * [MonthlyCalendar] 컴포저블의 디자인 기본값들을 제공하는 `object`
 */
object BbangZipMonthlyCalendarDefaults {
    /** 날짜 셀의 기본 모양입니다. 원형으로 설정되어 있습니다. */
    val DayCellShape: Shape = CircleShape

    /** 날짜 그리드에서 날짜 셀들 사이의 수직 간격입니다. */
    val DayCellVerticalSpacing: Dp = 4.dp

    /** 날짜 그리드 및 요일 헤더에서 셀들 사이의 수평 간격입니다. */
    val DayCellHorizontalSpacing: Dp = 9.dp

    /** 달력 헤더와 요일 헤더 사이의 간격입니다. */
    val HeaderToDayOfWeekGap: Dp = 20.dp

    /** 요일 헤더와 날짜 그리드 사이의 간격입니다. */
    val DayOfWeekToDayOfMonthGap: Dp = 6.dp

    /** 달력 헤더의 년/월 텍스트와 네비게이션 아이콘 사이의 간격입니다. */
    val MonthToNavigationIconGap: Dp = 20.dp

    /** 달력 헤더의 네비게이션 아이콘들 사이의 간격입니다. */
    val NavigationIconGap: Dp = 20.dp

    /**
     * [MonthlyCalendar]의 기본 색상 구성을 생성하는 함수
     *
     * 각 색상 파라미터는 [BbangZipTheme]에서 기본값을 가져오며, 필요에 따라 재정의할 수 있습니다([copy]).
     *
     * @param selectedDayCellBackgroundColor 선택된 날짜 셀의 배경색입니다.
     * @param todayCellBackgroundColor "오늘" 날짜 셀의 배경색입니다.
     * @param defaultDayCellBackgroundColor 기본 날짜 셀의 배경색입니다.
     * @param selectedDayCellTextColor 선택된 날짜 셀의 텍스트 색상입니다.
     * @param notCurrentMonthDayCellTextColor 현재 달에 속하지 않는 날짜 셀의 텍스트 색상입니다.
     * @param defaultDayCellTextColor 기본 날짜 셀의 텍스트 색상입니다.
     * @param dayOfWeekHeaderTextColor 요일 헤더의 텍스트 색상입니다.
     * @param headerNavigationIconColor 달력 헤더 네비게이션 아이콘의 색상입니다.
     * @param headerTextColor 달력 헤더 텍스트의 색상입니다.
     * @return [MonthlyCalendarColors] 인스턴스를 반환합니다.
     */
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
        headerTextColor: Color = BbangZipTheme.color.labelNeutral_706A63,
    ): MonthlyCalendarColors {
        // 동일한 색상 입력에 대해 MonthlyCalendarColors 인스턴스를 캐싱하여 불필요한 재생성을 방지합니다.
        return remember(
            selectedDayCellBackgroundColor,
            todayCellBackgroundColor,
            defaultDayCellBackgroundColor,
            selectedDayCellTextColor,
            notCurrentMonthDayCellTextColor,
            defaultDayCellTextColor,
            dayOfWeekHeaderTextColor,
            headerNavigationIconColor,
            headerTextColor,
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
                headerTextColor = headerTextColor,
            )
        }
    }

    /**
     * [MonthlyCalendar]의 기본 텍스트 스타일 구성을 생성하는 함수
     *
     * 각 텍스트 스타일 파라미터는 [BbangZipTheme]에서 기본값을 가져오며,
     * 필요에 따라 재정의할 수 있습니다([copy]).
     *
     * @param dayCellTextStyle 날짜 셀의 일자 텍스트 스타일입니다.
     * @param dayOfWeekTextStyle 요일 헤더의 텍스트 스타일입니다.
     * @param headerTextStyle 달력 헤더의 년/월 텍스트 스타일입니다.
     * @return [MonthlyCalendarTypography] 인스턴스를 반환합니다.
     */
    @Composable
    fun typography(
        dayCellTextStyle: TextStyle = BbangZipTheme.typography.label4Regular,
        dayOfWeekTextStyle: TextStyle = BbangZipTheme.typography.label4Regular,
        headerTextStyle: TextStyle = BbangZipTheme.typography.subTitle1Medium,
    ): MonthlyCalendarTypography {
        // 동일한 텍스트 스타일 입력에 대해 MonthlyCalendarTypography 인스턴스를 캐싱합니다.
        return remember(
            dayCellTextStyle,
            dayOfWeekTextStyle,
            headerTextStyle,
        ) {
            MonthlyCalendarTypography(
                dayCellDateTextStyle = dayCellTextStyle,
                dayOfWeekTextStyle = dayOfWeekTextStyle,
                headerTextStyle = headerTextStyle,
            )
        }
    }
}
