package org.android.bbangzip.presentation.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.presentation.util.extension.startOfWeek
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

/** 페이저의 "무한 스크롤"을 구현하기 위한 페이지 수 */
private const val PAGER_PAGE_COUNT = Int.MAX_VALUE
/** 페이저의 초기 페이지 인덱스
 *
 * [PAGER_PAGE_COUNT]의 중앙값으로 설정하여 양방향 스크롤을 가능하게 합니다. */
private const val INITIAL_PAGE_INDEX = Int.MAX_VALUE / 2
/** 한 주의 일 수 (7일) */
private const val DAYS_IN_WEEK = 7

/** 캘린더 헤더에 표시될 날짜 형식 패턴(예: "2025년 8월") */
private const val HEADER_DATE_PATTERN = "yyyy년 MMMM"

/**
 * 주간 캘린더의 각 날짜 셀을 나타내는 데이터 클래스
 *
 * @property date 해당 셀의 [LocalDate]입니다.
 * @property isToday 오늘 날짜인지 여부입니다.
 */
@Immutable
private data class WeeklyCalendarDay(
    val date: LocalDate,
    val isToday: Boolean,
)

/**
 * 주간 단위로 날짜를 보여주는 캘린더 컴포저블
 *
 * 사용자는 이전/다음 주로 스와이프 및 아이콘 클릭을 하여 이동할 수 있으며, 특정 날짜를 선택할 수 있습니다.
 *
 * @param modifier 이 컴포저블에 적용할 [Modifier]입니다.
 * @param initialDate 캘린더가 처음 표시될 때의 초기 날짜입니다. 기본값은 오늘 날짜입니다.
 * @param startDayOfWeek 한 주의 시작 요일입니다. 기본값은 [DayOfWeek.MONDAY] (월요일) 입니다.
 * @param colors 캘린더의 색상 설정을 정의하는 [WeeklyCalendarColors] 객체입니다.
 * 기본값은 [BbangZipWeeklyCalendarDefaults.colors]에서 제공됩니다.
 * @param typography 캘린더의 텍스트 스타일 설정을 정의하는 [WeeklyCalendarTypography] 객체입니다.
 * 기본값은 [BbangZipWeeklyCalendarDefaults.typography]에서 제공됩니다.
 * @param onDateSelected 날짜가 선택될 때 호출되는 콜백 함수입니다. 선택된 [LocalDate]를 인자로 받습니다.
 * @param onMenuClick 메뉴 아이콘 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun BbangZipWeeklyCalendar(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    colors: WeeklyCalendarColors = BbangZipWeeklyCalendarDefaults.colors(),
    typography: WeeklyCalendarTypography = BbangZipWeeklyCalendarDefaults.typography(),
    onDateSelected: (LocalDate) -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val today = remember { LocalDate.now() }
    var selectedDate by remember(key1 = initialDate) { mutableStateOf(value = initialDate) }

    val pagerState =
        rememberPagerState(
            initialPage = INITIAL_PAGE_INDEX,
            pageCount = { PAGER_PAGE_COUNT },
        )

    val firstDayOfInitialPagerWeek =
        remember(key1 = initialDate, key2 = startDayOfWeek) {
            initialDate.startOfWeek(startDayOfWeek)
        }

    val currentDisplayWeekStartDate by remember {
        derivedStateOf {
            val weeksOffset = pagerState.currentPage - INITIAL_PAGE_INDEX
            firstDayOfInitialPagerWeek.plusWeeks(weeksOffset.toLong())
        }
    }

    LaunchedEffect(key1 = selectedDate) {
        onDateSelected(selectedDate)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        WeeklyCalendarHeader(
            currentDisplayWeekViewStartDate = currentDisplayWeekStartDate,
            selectedDate = selectedDate,
            onMenuClick = onMenuClick,
            onPreviousWeek = {
                if (pagerState.currentPage > 0) {
                    scope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                    }
                }
            },
            onNextWeek = {
                if (pagerState.currentPage < PAGER_PAGE_COUNT - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                    }
                }
            },
            colors = colors,
            typography = typography,
        )

        Gap(height = BbangZipWeeklyCalendarDefaults.HeaderToWeekRowGap)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { pageIndex ->
            val weeksOffset = pageIndex - INITIAL_PAGE_INDEX
            val firstDayForThisPage =
                remember(key1 = firstDayOfInitialPagerWeek, key2 = weeksOffset) {
                    firstDayOfInitialPagerWeek.plusWeeks(weeksOffset.toLong())
                }
            val currentPageWeekDays by remember(key1 = firstDayForThisPage, key2 = today) {
                derivedStateOf {
                    generateWeekDaysList(startDateOfWeek = firstDayForThisPage, today = today)
                }
            }

            WeekRow(
                days = currentPageWeekDays,
                selectedDate = selectedDate,
                onDateClick = { day ->
                    selectedDate = day.date
                },
                colors = colors,
                typography = typography,
            )
        }
    }
}

/**
 * 주간 캘린더의 헤더를 표시하는 컴포저블
 *
 * 현재 표시된 주의 월 정보, 이전/다음 주 이동 아이콘, 메뉴 아이콘을 포함합니다.
 *
 * @param currentDisplayWeekViewStartDate 현재 화면에 표시되는 주의 시작 [LocalDate]입니다.
 * @param selectedDate 현재 선택된 [LocalDate]입니다. 헤더의 월 표시 결정에 사용됩니다.
 * @param onPreviousWeek 이전 주로 이동하는 액션을 처리하는 콜백입니다.
 * @param onNextWeek 다음 주로 이동하는 액션을 처리하는 콜백입니다.
 * @param onClickMenu 메뉴 아이콘 클릭 시 호출되는 콜백입니다.
 * @param colors 헤더의 색상 설정을 담고 있는 [WeeklyCalendarColors] 객체입니다.
 * @param typography 헤더의 텍스트 스타일 설정을 담고 있는 [WeeklyCalendarTypography] 객체입니다.
 */
@Composable
private fun WeeklyCalendarHeader(
    currentDisplayWeekViewStartDate: LocalDate,
    selectedDate: LocalDate,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    onMenuClick: () -> Unit,
    colors: WeeklyCalendarColors,
    typography: WeeklyCalendarTypography,
) {
    val displayDateForMonth = currentDisplayWeekViewStartDate.getDisplayMonth(selectedDate)

    val displayFormatter = remember { DateTimeFormatter.ofPattern(HEADER_DATE_PATTERN, Locale.getDefault()) }
    val displayText =
        remember(displayDateForMonth) {
            displayDateForMonth.format(displayFormatter)
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = displayText,
            style = typography.headerDateTextStyle,
            color = colors.headerDateColor,
        )

        Gap(width = BbangZipWeeklyCalendarDefaults.MonthToNavigationIconGap)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left_24),
            contentDescription = stringResource(id = R.string.calendar_previous_week_description),
            modifier = Modifier.noRippleClickable(onClick = onPreviousWeek),
            tint = colors.headerNavigationIconColor,
        )

        Gap(width = BbangZipWeeklyCalendarDefaults.NavigationIconGap)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = stringResource(id = R.string.calendar_next_week_description),
            modifier = Modifier.noRippleClickable(onClick = onNextWeek),
            tint = colors.headerNavigationIconColor,
        )

        Gap()

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger_menu_default_24),
            contentDescription = stringResource(R.string.calendar_menu_description),
            modifier = Modifier.noRippleClickable(onClick = onMenuClick),
            tint = colors.headerNavigationIconColor,
        )
    }
}

/**
 * 한 주의 날짜들을 가로로 나열하여 표시하는 컴포저블
 *
 * @param days 표시할 [WeeklyCalendarDay] 객체의 목록입니다.
 * @param selectedDate 현재 선택된 [LocalDate]입니다.
 * @param onDateClick 날짜 셀이 클릭되었을 때 호출되는 콜백입니다. 클릭된 [WeeklyCalendarDay]를 인자로 받습니다.
 * @param colors 셀의 색상 설정을 담고 있는 [WeeklyCalendarColors] 객체입니다.
 * @param typography 셀의 텍스트 스타일 설정을 담고 있는 [WeeklyCalendarTypography] 객체입니다.
 */
@Composable
private fun WeekRow(
    days: List<WeeklyCalendarDay>,
    selectedDate: LocalDate,
    onDateClick: (WeeklyCalendarDay) -> Unit,
    colors: WeeklyCalendarColors,
    typography: WeeklyCalendarTypography,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = BbangZipWeeklyCalendarDefaults.DayCellSpacing),
    ) {
        days.forEach { day ->
            key(day.date) {
                DayCell(
                    dayData = day,
                    isSelected = day.date == selectedDate,
                    onClick = { onDateClick(day) },
                    modifier = Modifier.weight(weight = 1f),
                    colors = colors,
                    typography = typography,
                )
            }
        }
    }
}


/**
 * 주간 캘린더 내의 개별 날짜 셀(요일과 일자 포함)을 표시하는 컴포저블입니다.
 *
 * @param dayData 표시할 [WeeklyCalendarDay] 객체입니다.
 * @param isSelected 이 날짜 셀이 현재 선택된 날짜인지 여부입니다.
 * @param colors 셀의 색상 설정을 담고 있는 [WeeklyCalendarColors] 객체입니다.
 * @param typography 셀의 텍스트 스타일 설정을 담고 있는 [WeeklyCalendarTypography] 객체입니다.
 * @param modifier 이 컴포저블에 적용할 [Modifier]입니다.
 * @param onClick 날짜 셀이 클릭되었을 때 호출되는 콜백입니다.
 */
@Composable
private fun DayCell(
    dayData: WeeklyCalendarDay,
    isSelected: Boolean,
    colors: WeeklyCalendarColors,
    typography: WeeklyCalendarTypography,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val backgroundColor = colors.dayCellBackgroundColor(isSelected = isSelected)
    val dateTextColor = colors.dayCellDateTextColor(isSelected = isSelected)
    val dateTextStyle = typography.dayCellDateTextStyle(isSelected = isSelected)

    Box(
        modifier =
            modifier
                .clip(shape = BbangZipWeeklyCalendarDefaults.DayCellShape)
                .noRippleClickable(onClick = onClick)
                .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = BbangZipWeeklyCalendarDefaults.DayCellVerticalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = dayData.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = dateTextColor,
                style = dateTextStyle,
            )

            Gap(height = BbangZipWeeklyCalendarDefaults.DayOfWeekToDayOfMonthGap)

            Text(
                text = dayData.date.dayOfMonth.toString(),
                color = dateTextColor,
                style = dateTextStyle,
            )
        }
    }
}

/**
 * 주간 캘린더 헤더에 표시할 월(Month)을 결정하는 확장 함수
 *
 * 주가 두 달에 걸쳐 있을 경우, 선택된 날짜([selectedDate])가 포함된 월을 우선적으로 표시합니다.
 *
 * @param selectedDate 현재 선택된 날짜입니다.
 * @return 헤더에 표시할 기준이 되는 [LocalDate]입니다. 이 날짜의 월 정보를 사용합니다.
 */
private fun LocalDate.getDisplayMonth(selectedDate: LocalDate): LocalDate {
    val lastDayOfWeek = this.plusDays(DAYS_IN_WEEK - 1L)
    return if (this.month != lastDayOfWeek.month) {
        if (selectedDate.isAfter(this) && selectedDate.isBefore(lastDayOfWeek) && selectedDate.month == lastDayOfWeek.month) {
            selectedDate
        } else {
            this
        }
    } else {
        this
    }
}

/**
 * 특정 주의 시작 날짜([startDateOfWeek])와 "오늘"([today])을 기준으로
 * 해당 주의 [WeeklyCalendarDay] 목록을 생성하는 함수
 *
 * @param startDateOfWeek 목록을 생성할 주의 시작 [LocalDate]입니다.
 * @param today "오늘"로 표시될 기준이 되는 [LocalDate]입니다.
 * @return 생성된 [WeeklyCalendarDay]의 목록 (총 7일).
 */
private fun generateWeekDaysList(
    startDateOfWeek: LocalDate,
    today: LocalDate,
): List<WeeklyCalendarDay> {
    return List(DAYS_IN_WEEK) { i ->
        val date = startDateOfWeek.plusDays(i.toLong())
        WeeklyCalendarDay(
            date = date,
            isToday = date.isEqual(today),
        )
    }
}

@Preview(showBackground = true, name = "BbangZip Weekly Calendar")
@Composable
fun WeeklyCalendarPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            BbangZipWeeklyCalendar(
                onDateSelected = {
                    Timber.tag("BbangZipWeeklyCalendar").d("onDateSelected: $it")
                },
            )
        }
    }
}
