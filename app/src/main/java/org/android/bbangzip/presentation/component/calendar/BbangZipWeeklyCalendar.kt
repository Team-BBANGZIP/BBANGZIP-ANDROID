package org.android.bbangzip.presentation.component.calendar

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private const val PAGER_PAGE_COUNT = Int.MAX_VALUE

@Stable
private data class WeeklyCalendarDay(
    val date: LocalDate,
    val isToday: Boolean,
)

@Composable
fun BbangZipWeeklyCalendar(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    onDateSelected: (LocalDate) -> Unit = {},
    onClickMenu: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    val today = LocalDate.now()
    var selectedDate by remember(initialDate) { mutableStateOf(initialDate) }

    val initialPagerIndex = remember { PAGER_PAGE_COUNT / 2 }
    val pagerState =
        rememberPagerState(
            initialPage = initialPagerIndex,
            pageCount = { PAGER_PAGE_COUNT },
        )

    val firstDayOfInitialPagerWeek =
        remember(initialDate, startDayOfWeek) {
            initialDate.with(startDayOfWeek)
        }

    val currentDisplayWeekStartDate by remember {
        derivedStateOf {
            val weeksOffset = pagerState.currentPage - initialPagerIndex
            firstDayOfInitialPagerWeek.plusWeeks(weeksOffset.toLong())
        }
    }

    LaunchedEffect(selectedDate) {
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
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            },
            onNextWeek = {
                if (pagerState.currentPage < PAGER_PAGE_COUNT - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
        )

        Gap(height = 20.dp)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { pageIndex ->
            val weeksOffset = pageIndex - initialPagerIndex

            val firstDayForThisPage =
                remember(firstDayOfInitialPagerWeek, weeksOffset) {
                    firstDayOfInitialPagerWeek.plusWeeks(weeksOffset.toLong())
                }

            val currentPageWeekDays by remember(firstDayForThisPage, today) {
                derivedStateOf {
                    generateWeekDaysList(firstDayForThisPage, today)
                }
            }

            WeekRow(
                days = currentPageWeekDays,
                selectedDate = selectedDate,
                onDateClick = { day ->
                    selectedDate = day.date
                },
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
) {
    val displayFormatter = remember { DateTimeFormatter.ofPattern("yyyy년 MMMM", Locale.getDefault()) }
    val lastDayOfCurrentWeek = currentDisplayWeekViewStartDate.plusDays(6)

    val displayDateForMonth =
        if (currentDisplayWeekViewStartDate.month != lastDayOfCurrentWeek.month) {
            if (selectedDate.month != currentDisplayWeekViewStartDate.month &&
                selectedDate.month == lastDayOfCurrentWeek.month
            ) {
                selectedDate
            } else {
                currentDisplayWeekViewStartDate
            }
        } else {
            currentDisplayWeekViewStartDate
        }
    val displayText = displayDateForMonth.format(displayFormatter)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = displayText,
            style = BbangZipTheme.typography.subTitle1Medium,
            color = BbangZipTheme.color.labelNeutral_706A63,
        )
        Gap(width = 20.dp)
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left_24),
            contentDescription = "이전 주",
            modifier = Modifier.noRippleClickable(onClick = onPreviousWeek),
            tint = BbangZipTheme.color.labelAlternative_A29D96,
        )
        Gap(width = 20.dp)
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = "다음 주",
            modifier = Modifier.noRippleClickable(onClick = onNextWeek),
            tint = BbangZipTheme.color.labelAlternative_A29D96,
        )
        Gap()
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger_menu_default_24),
            contentDescription = "메뉴",
            modifier = Modifier.noRippleClickable(onClick = onClickMenu),
            tint = BbangZipTheme.color.labelAlternative_A29D96,
        )
    }
}

@Composable
private fun WeekRow(
    days: List<WeeklyCalendarDay>,
    selectedDate: LocalDate,
    onDateClick: (WeeklyCalendarDay) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        days.forEach { day ->
            DayCell(
                dayData = day,
                isSelected = day.date == selectedDate,
                onClick = { onDateClick(day) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun DayCell(
    dayData: WeeklyCalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) BbangZipTheme.color.secondaryStrong_F2EAE4 else Color.Transparent
    val dateTextColor = if (isSelected) BbangZipTheme.color.labelNormal_6B6560 else BbangZipTheme.color.labelAlternative_A29D96
    val dateTextStyle = if (isSelected) BbangZipTheme.typography.label1SemiBold else BbangZipTheme.typography.label2Regular

    Box(
        modifier =
            modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .noRippleClickable(onClick = onClick)
                .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = dayData.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = dateTextColor,
                style = dateTextStyle,
            )

            Gap(height = 8.dp)

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
    return List(7) { i ->
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
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            BbangZipWeeklyCalendar(
                onDateSelected = {
                    Log.d("BbangZipWeeklyCalendar", "onDateSelected: $it")
                },
            )
        }
    }
}
