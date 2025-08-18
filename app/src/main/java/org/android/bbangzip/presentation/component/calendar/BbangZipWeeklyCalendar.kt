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

private const val PAGER_PAGE_COUNT = Int.MAX_VALUE
private const val INITIAL_PAGE_INDEX = Int.MAX_VALUE / 2
private const val DAYS_IN_WEEK = 7

private const val HEADER_DATE_PATTERN = "yyyy년 MMMM"

private fun LocalDate.getDisplayMonth(selectedDate: LocalDate): LocalDate {
    val lastDayOfWeek = this.plusDays(DAYS_IN_WEEK - 1L)
    return if (this.month != lastDayOfWeek.month) {
        if (selectedDate.month != this.month && selectedDate.month == lastDayOfWeek.month) {
            selectedDate
        } else {
            this
        }
    } else {
        this
    }
}

@Immutable
private data class WeeklyCalendarDay(
    val date: LocalDate,
    val isToday: Boolean,
)

@Composable
fun BbangZipWeeklyCalendar(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    colors: WeeklyCalendarColors = BbangZipWeeklyCalendarDefaults.colors(),
    typography: WeeklyCalendarTypography = BbangZipWeeklyCalendarDefaults.typography(),
    onDateSelected: (LocalDate) -> Unit = {},
    onClickMenu: () -> Unit = {},
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
            onClickMenu = onClickMenu,
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

@Composable
private fun WeeklyCalendarHeader(
    currentDisplayWeekViewStartDate: LocalDate,
    selectedDate: LocalDate,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    onClickMenu: () -> Unit,
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
            modifier = Modifier.noRippleClickable(onClick = onClickMenu),
            tint = colors.headerNavigationIconColor,
        )
    }
}

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
