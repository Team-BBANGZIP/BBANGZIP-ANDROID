package org.android.bbangzip.presentation.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit // 추가된 import
import java.util.Locale
import java.time.format.TextStyle as TimeTextStyle

/** 캘린더 헤더에 표시될 날짜 형식 패턴 (예: "2025년 8월") */
private const val HEADER_DATE_PATTERN = "yyyy년 MMMM"

/** 한 주의 일 수 (7일) */
private const val DAYS_IN_WEEK = 7

/** 달력에 표시할 최대 주의 수 */
private const val MAX_WEEKS_IN_MONTH_DISPLAY = 6

/** 달력의 그리드에 표시될 셀의 총 수 */
private const val CALENDAR_GRID_CELL_COUNT = DAYS_IN_WEEK * MAX_WEEKS_IN_MONTH_DISPLAY

/** HorizontalPager의 시작 페이지 인덱스 (무한 스크롤 효과를 위해 중앙값 사용) */
private const val STARTING_PAGE_INDEX = Int.MAX_VALUE / 2

/**
 * 월간 달력의 각 날짜 셀을 나타내는 데이터 클래스
 *
 * @property date 해당 셀의 [LocalDate]입니다.
 * @property isCurrentMonth 현재 표시된 달에 속하는 날짜인지 여부입니다.
 * @property isToday 오늘 날짜인지 여부입니다.
 */
@Immutable
private data class MonthlyCalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
)

/**
 * 월간 달력을 표시하는 컴포저블 함수
 *
 * 사용자는 아이콘 클릭이나 스와이프를 통해 이전/다음 달로 이동할 수 있으며, 특정 날짜를 선택할 수 있습니다.
 *
 * @param modifier 이 컴포저블에 적용할 [Modifier]입니다.
 * @param initialYearMonth 달력이 처음 표시될 때의 년/월입니다. 기본값은 현재 년/월입니다.
 * @param onDateSelected 날짜가 선택될 때 호출되는 콜백 함수입니다. 선택된 [LocalDate]를 인자로 받습니다.
 * @param colors 달력의 색상 설정을 정의하는 [MonthlyCalendarColors] 객체입니다.
 *               기본값은 [BbangZipMonthlyCalendarDefaults.colors]에서 제공됩니다.
 * @param typography 달력의 텍스트 스타일 설정을 정의하는 [MonthlyCalendarTypography] 객체입니다.
 *                   기본값은 [BbangZipMonthlyCalendarDefaults.typography]에서 제공됩니다.
 */
@Composable
fun MonthlyCalendar(
    modifier: Modifier = Modifier,
    initialYearMonth: YearMonth = YearMonth.now(),
    onDateSelected: (LocalDate) -> Unit = {},
    colors: MonthlyCalendarColors = BbangZipMonthlyCalendarDefaults.colors(),
    typography: MonthlyCalendarTypography = BbangZipMonthlyCalendarDefaults.typography(),
) {
    val pagerState = rememberPagerState(
        initialPage = STARTING_PAGE_INDEX,
        pageCount = { Int.MAX_VALUE }
    )
    var selectedDate by remember { mutableStateOf(value = LocalDate.now()) }
    val today = remember { LocalDate.now() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedDate) {
        onDateSelected(selectedDate)
    }

    val currentYearMonth = remember(pagerState.currentPage, initialYearMonth) {
            initialYearMonth.plusMonths((pagerState.currentPage - STARTING_PAGE_INDEX).toLong())
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        CalendarHeader(
            yearMonth = currentYearMonth,
            pagerState = pagerState,
            colors = colors,
            typography = typography,
        )

        Gap(height = BbangZipMonthlyCalendarDefaults.HeaderToDayOfWeekGap)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { pageIndex ->
            val yearMonthForPage = remember(pageIndex, initialYearMonth) {
                initialYearMonth.plusMonths((pageIndex - STARTING_PAGE_INDEX).toLong())
            }

            val daysInMonth = remember(key1 = yearMonthForPage, key2 = today) {
                generateMonthDays(
                    yearMonth = yearMonthForPage,
                    today = today,
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                DayOfWeekHeader(
                    color = colors.dayOfWeekTextColor,
                    typography = typography.dayOfWeekTextStyle,
                )

                Gap(height = BbangZipMonthlyCalendarDefaults.DayOfWeekToDayOfMonthGap)

                CalendarGrid(
                    days = daysInMonth,
                    selectedDate = selectedDate,
                    onDateClick = { day ->
                        selectedDate = day.date

                        val newSelectedYearMonth = YearMonth.from(day.date)

                        if (newSelectedYearMonth != yearMonthForPage) {
                            val monthDiff = ChronoUnit.MONTHS.between(initialYearMonth, newSelectedYearMonth)
                            val targetPage = STARTING_PAGE_INDEX + monthDiff.toInt()
                            scope.launch {
                                pagerState.animateScrollToPage(targetPage)
                            }
                        }
                    },
                    colors = colors,
                    typography = typography,
                )
            }
        }
    }
}

/**
 * 달력의 헤더 부분을 표시하는 컴포저블
 *
 * 현재 년/월과 이전/다음 달 이동 아이콘을 포함합니다.
 *
 * @param yearMonth 표시할 년/월입니다.
 * @param pagerState 달력의 페이지 상태를 제어하는 [PagerState] 객체입니다.
 * @param colors 헤더의 색상 설정을 담고 있는 [MonthlyCalendarColors] 객체입니다.
 * @param typography 헤더의 텍스트 스타일 설정을 담고 있는 [MonthlyCalendarTypography] 객체입니다.
 */
@Composable
private fun CalendarHeader(
    yearMonth: YearMonth,
    pagerState: PagerState,
    colors: MonthlyCalendarColors,
    typography: MonthlyCalendarTypography,
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val monthYearFormatter =
            remember {
                DateTimeFormatter.ofPattern(HEADER_DATE_PATTERN, Locale.getDefault())
            }
        Text(
            text = yearMonth.format(monthYearFormatter),
            style = typography.headerTextStyle,
            color = colors.headerTextColor,
        )

        Gap(width = BbangZipMonthlyCalendarDefaults.MonthToNavigationIconGap)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left_24),
            contentDescription = stringResource(R.string.calendar_previous_month_description),
            modifier =
            Modifier
                .noRippleClickable(onClick = {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }),
            tint = colors.headerNavigationIconColor,
        )

        Gap(width = BbangZipMonthlyCalendarDefaults.NavigationIconGap)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = stringResource(R.string.calendar_next_month_description),
            modifier =
            Modifier
                .noRippleClickable(onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pagerState.pageCount -1) {
                             pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }),
            tint = colors.headerNavigationIconColor,
        )
    }
}

/**
 * 달력의 요일 헤더(예: 월, 화, 수...)를 표시하는 컴포저블
 *
 * @param color 요일 텍스트의 색상입니다.
 * @param typography 요일 텍스트의 스타일입니다.
 */
@Composable
private fun DayOfWeekHeader(
    color: Color,
    typography: TextStyle,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = BbangZipMonthlyCalendarDefaults.DayCellHorizontalSpacing),
    ) {
        val daysOfWeek = remember { getDaysOfWeekStartingFrom() }

        for (dayOfWeek in daysOfWeek) {
            Text(
                text = dayOfWeek.getDisplayName(TimeTextStyle.SHORT, Locale.getDefault()),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                style = typography,
                color = color,
            )
        }
    }
}

/**
 * 달력의 날짜 그리드를 [LazyVerticalGrid]를 사용하여 표시하는 컴포저블
 *
 * @param days 표시할 [MonthlyCalendarDay]의 목록입니다.
 * @param selectedDate 현재 선택된 날짜입니다.
 * @param onDateClick 날짜 셀이 클릭되었을 때 호출되는 콜백입니다. 클릭된 [MonthlyCalendarDay]를 인자로 받습니다.
 * @param colors 그리드 및 셀의 색상 설정을 담고 있는 [MonthlyCalendarColors] 객체입니다.
 * @param typography 그리드 및 셀의 텍스트 스타일 설정을 담고 있는 [MonthlyCalendarTypography] 객체입니다.
 */
@Composable
private fun CalendarGrid(
    days: List<MonthlyCalendarDay>,
    selectedDate: LocalDate,
    onDateClick: (MonthlyCalendarDay) -> Unit,
    colors: MonthlyCalendarColors,
    typography: MonthlyCalendarTypography,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = DAYS_IN_WEEK),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = BbangZipMonthlyCalendarDefaults.DayCellVerticalSpacing),
        horizontalArrangement = Arrangement.spacedBy(space = BbangZipMonthlyCalendarDefaults.DayCellHorizontalSpacing),
        userScrollEnabled = false
    ) {
        items(
            items = days,
            key = { day -> day.date.toEpochDay() },
        ) { day ->
            CalendarDayCell(
                day = day,
                isSelected = day.date == selectedDate,
                onClick = { onDateClick(day) },
                colors = colors,
                typography = typography,
            )
        }
    }
}

/**
 * 달력 그리드 내의 개별 날짜 셀을 표시하는 컴포저블
 *
 * @param day 표시할 [MonthlyCalendarDay] 객체입니다.
 * @param isSelected 이 날짜 셀이 현재 선택된 날짜인지 여부입니다.
 * @param onClick 날짜 셀이 클릭되었을 때 호출되는 콜백입니다.
 * @param colors 셀의 색상 설정을 담고 있는 [MonthlyCalendarColors] 객체입니다.
 * @param typography 셀의 텍스트 스타일 설정을 담고 있는 [MonthlyCalendarTypography] 객체입니다.
 */
@Composable
private fun CalendarDayCell(
    day: MonthlyCalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: MonthlyCalendarColors,
    typography: MonthlyCalendarTypography,
) {
    val backgroundColor =
        colors.dayCellBackgroundColor(isSelected = isSelected, isToday = day.isToday)

    val textColor =
        colors.dayCellTextColor(isSelected = isSelected, isCurrentMonth = day.isCurrentMonth)

    Box(
        modifier =
        Modifier
            .aspectRatio(1f)
            .clip(BbangZipMonthlyCalendarDefaults.DayCellShape)
            .noRippleClickable(
                enabled = true,
                onClick = onClick,
            )
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = typography.dayCellDateTextStyle,
        )
    }
}

/**
 * 주어진 [yearMonth]와 "오늘" 날짜를 기준으로 월간 달력에 표시될 날짜 목록([MonthlyCalendarDay])을 생성하는 함수
 *
 * 이전 달, 현재 달, 다음 달의 날짜를 포함해 [CALENDAR_GRID_CELL_COUNT]를 채웁니다.
 *
 * @param yearMonth 날짜 목록을 생성할 기준 년/월입니다.
 * @param today "오늘"로 표시될 날짜입니다.
 * @param startDayOfWeek 한 주의 시작 요일입니다. 기본값은 [DayOfWeek.MONDAY] (월요일) 입니다.
 * @return 생성된 [MonthlyCalendarDay]의 목록입니다.
 */
private fun generateMonthDays(
    yearMonth: YearMonth,
    today: LocalDate,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
): List<MonthlyCalendarDay> {
    val firstDayOfMonth = yearMonth.atDay(1)

    var currentDay = firstDayOfMonth
    while (currentDay.dayOfWeek != startDayOfWeek) {
        currentDay = currentDay.minusDays(1)
    }

    val days = mutableListOf<MonthlyCalendarDay>()

    for (i in 0 until CALENDAR_GRID_CELL_COUNT) {
        val date = currentDay.plusDays(i.toLong())
        days.add(
            MonthlyCalendarDay(
                date = date,
                isCurrentMonth = YearMonth.from(date) == yearMonth,
                isToday = date.isEqual(today)
            )
        )
    }
    return days
}


/**
 * 지정된 [startDayOfWeek]부터 시작하는 요일 목록을 반환하는 함수
 *
 * 예를 들어, [startDayOfWeek]가 [DayOfWeek.MONDAY]이면 [월, 화, 수, 목, 금, 토, 일] 순서로 반환됩니다.
 *
 * @param startDayOfWeek 반환될 요일 목록의 시작 요일입니다. 기본값은 [DayOfWeek.MONDAY]입니다.
 * @return [startDayOfWeek]부터 시작하도록 정렬된 [DayOfWeek]의 목록입니다.
 */
private fun getDaysOfWeekStartingFrom(startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY): List<DayOfWeek> {
    val daysOfWeek = DayOfWeek.entries.toTypedArray()
    val startIndex = daysOfWeek.indexOf(startDayOfWeek)
    return daysOfWeek.slice(startIndex until daysOfWeek.size) + daysOfWeek.slice(0 until startIndex)
}


@Preview(showBackground = true)
@Composable
fun MonthlyCalendarPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            MonthlyCalendar(
                initialYearMonth = YearMonth.now(),
                onDateSelected = {
                    Timber.tag("BbangZipMonthlyCalendar").d("onDateSelected: $it")
                },
            )
        }
    }
}
