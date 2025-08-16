package org.android.bbangzip.presentation.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private const val HEADER_DATE_PATTERN = "yyyy년 MMMM"

@Immutable
private data class MonthlyCalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
)

@Composable
fun MonthlyCalendar(
    modifier: Modifier = Modifier,
    initialYearMonth: YearMonth = YearMonth.now(),
    onDateSelected: (LocalDate) -> Unit = {},
) {
    var currentYearMonth by remember { mutableStateOf(initialYearMonth) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val today = LocalDate.now()

    val daysInMonth by remember(currentYearMonth, today) {
        derivedStateOf {
            generateMonthDays(currentYearMonth, today)
        }
    }

    LaunchedEffect(selectedDate) {
        onDateSelected(selectedDate)
    }

    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        CalendarHeader(
            yearMonth = currentYearMonth,
            onPreviousMonth = { currentYearMonth = currentYearMonth.minusMonths(1) },
            onNextMonth = { currentYearMonth = currentYearMonth.plusMonths(1) },
        )

        Gap(height = 20.dp)

        DayOfWeekHeader()

        Gap(height = 6.dp)

        CalendarGrid(
            days = daysInMonth,
            selectedDate = selectedDate,
            onDateClick = { day ->
                if (day.isCurrentMonth) {
                    selectedDate = day.date
                }
            },
        )
    }
}

@Composable
private fun CalendarHeader(
    yearMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
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
            style = BbangZipTheme.typography.subTitle1Medium,
            color = BbangZipTheme.color.labelNeutral_706A63,
        )

        Gap(width = 20.dp)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left_24),
            contentDescription = stringResource(R.string.calendar_previous_month_description),
            modifier =
                Modifier
                    .noRippleClickable(onClick = onPreviousMonth),
            tint = BbangZipTheme.color.labelAlternative_A29D96,
        )

        Gap(width = 20.dp)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = stringResource(R.string.calendar_next_month_description),
            modifier =
                Modifier
                    .noRippleClickable(onClick = onNextMonth),
            tint = BbangZipTheme.color.labelAlternative_A29D96,
        )
    }
}

@Composable
private fun DayOfWeekHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        val daysOfWeek = remember { getDaysOfWeekStartingFrom() }

        for (dayOfWeek in daysOfWeek) {
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                style = BbangZipTheme.typography.label4Regular,
                color = BbangZipTheme.color.labelAssistive_C9C7C5,
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    days: List<MonthlyCalendarDay>,
    selectedDate: LocalDate,
    onDateClick: (MonthlyCalendarDay) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 7),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 9.dp),
    ) {
        items(
            items = days,
            key = { day -> day.date.toEpochDay() },
        ) { day ->
            CalendarDayCell(
                day = day,
                isSelected = day.date == selectedDate,
                onClick = { onDateClick(day) },
            )
        }
    }
}

@Composable
private fun CalendarDayCell(
    day: MonthlyCalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor =
        when {
            isSelected -> BbangZipTheme.color.labelAlternative_A29D96
            day.isToday -> BbangZipTheme.color.backgroundAlternative_FAF6F3
            else -> Color.Transparent
        }

    val textColor =
        when {
            isSelected -> BbangZipTheme.color.staticWhite_FFFFFF
            !day.isCurrentMonth -> BbangZipTheme.color.labelAssistive_C9C7C5
            else -> BbangZipTheme.color.labelAlternative_A29D96
        }

    Box(
        modifier =
            Modifier
                .aspectRatio(1f)
                .clip(CircleShape)
                .noRippleClickable(
                    enabled = day.isCurrentMonth,
                    onClick = onClick,
                )
                .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = BbangZipTheme.typography.label4Regular,
        )
    }
}

private fun generateMonthDays(
    yearMonth: YearMonth,
    today: LocalDate,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
): List<MonthlyCalendarDay> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    val indexOfStartDayOfWeek = firstDayOfMonth.dayOfWeek.value - startDayOfWeek.value

    val days = mutableListOf<MonthlyCalendarDay>()

    // 이전 달의 날짜 추가
    for (i in 0 until indexOfStartDayOfWeek) {
        val date = firstDayOfMonth.minusDays((indexOfStartDayOfWeek - i).toLong())
        days.add(MonthlyCalendarDay(date = date, isCurrentMonth = false, isToday = date.isEqual(today)))
    }

    // 현재 달의 날짜 추가
    var currentDate = firstDayOfMonth
    while (!currentDate.isAfter(lastDayOfMonth)) {
        days.add(MonthlyCalendarDay(date = currentDate, isCurrentMonth = true, isToday = currentDate.isEqual(today)))
        currentDate = currentDate.plusDays(1)
    }

    // 다음 달의 날짜 추가
    val remainingCells = 42 - days.size
    for (i in 1..remainingCells) {
        val date = lastDayOfMonth.plusDays(i.toLong())
        days.add(MonthlyCalendarDay(date = date, isCurrentMonth = false, isToday = date.isEqual(today)))
    }

    return days
}

private fun getDaysOfWeekStartingFrom(startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY) =
    DayOfWeek.entries.run {
        val daysList = this.toList()
        val startIndex = startDayOfWeek.ordinal
        daysList.subList(startIndex, daysList.size) + daysList.subList(0, startIndex)
    }

@Preview(showBackground = true)
@Composable
fun MonthlyCalendarPreview() {
    BBANGZIPANDROIDTheme {
        MonthlyCalendar(onDateSelected = {
            println("Selected date: $it")
        })
    }
}
