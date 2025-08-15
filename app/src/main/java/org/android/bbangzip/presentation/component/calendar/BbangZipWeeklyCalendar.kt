package org.android.bbangzip.presentation.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

@Stable
private data class WeeklyCalendarDay(
    val date: LocalDate,
    val isToday: Boolean
)

@Composable
fun BbangZipWeeklyCalendar(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit = {}
) {
    val today = remember { LocalDate.now() }
    val weekFields = remember { WeekFields.of(Locale.getDefault()) }

    var currentFirstDayOfWeek by remember {
        mutableStateOf(initialDate.with(weekFields.firstDayOfWeek))
    }
    var selectedDate by remember(initialDate) {
        mutableStateOf(initialDate)
    }

    LaunchedEffect(initialDate) {
        val newFirstDayOfWeek = initialDate.with(weekFields.firstDayOfWeek)
        if (newFirstDayOfWeek != currentFirstDayOfWeek) {
            currentFirstDayOfWeek = newFirstDayOfWeek
        }
        if (initialDate != selectedDate) {
            selectedDate = initialDate
        }
    }

    LaunchedEffect(selectedDate) {
        onDateSelected(selectedDate)
    }

    val weekDays by remember(currentFirstDayOfWeek, today) {
        derivedStateOf {
            generateWeekDaysList(currentFirstDayOfWeek, today)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        WeeklyCalendarHeader(
            currentWeekViewStartDate = currentFirstDayOfWeek,
            onPreviousWeek = { currentFirstDayOfWeek = currentFirstDayOfWeek.minusWeeks(1) },
            onNextWeek = { currentFirstDayOfWeek = currentFirstDayOfWeek.plusWeeks(1) },
            onClickMenu = {},
        )

        Gap(height = 20.dp)

        IntegratedWeekRow(
            days = weekDays,
            selectedDate = selectedDate,
            onDateClick = { day ->
                selectedDate = day.date
            }
        )
    }
}

@Composable
private fun WeeklyCalendarHeader(
    currentWeekViewStartDate: LocalDate,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    onClickMenu: () -> Unit,
) {
    val displayFormatter = remember { DateTimeFormatter.ofPattern("yyyy년 MMMM", Locale.getDefault()) }
    val displayText = currentWeekViewStartDate.format(displayFormatter)

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
            contentDescription = "이전 달",
            modifier = Modifier.noRippleClickable(onClick = onPreviousWeek),
            tint = BbangZipTheme.color.labelAlternative_A29D96
        )

        Gap(width = 20.dp)

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = "다음 달",
            modifier = Modifier.noRippleClickable(onClick = onNextWeek),
            tint = BbangZipTheme.color.labelAlternative_A29D96
        )

        Gap() // Spacer to push menu icon to the end

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger_menu_default_24),
            contentDescription = "메뉴",
            modifier = Modifier.noRippleClickable(onClick = onClickMenu),
            tint = BbangZipTheme.color.labelAlternative_A29D96
        )
    }
}

@Composable
private fun IntegratedWeekRow(
    days: List<WeeklyCalendarDay>,
    selectedDate: LocalDate,
    onDateClick: (WeeklyCalendarDay) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        days.forEach { day ->
            IntegratedDayCell(
                dayData = day,
                isSelected = day.date == selectedDate,
                onClick = { onDateClick(day) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun IntegratedDayCell(
    dayData: WeeklyCalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
            when {
                isSelected -> BbangZipTheme.color.secondaryStrong_F2EAE4
                else -> Color.Transparent
            }

    val dateTextColor =
            when {
                isSelected -> BbangZipTheme.color.labelNormal_6B6560
                else -> BbangZipTheme.color.labelAlternative_A29D96
            }

    val dateTextStyle =
        when {
            isSelected -> BbangZipTheme.typography.label1SemiBold
            else -> BbangZipTheme.typography.label4Regular
        }

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .noRippleClickable(onClick = onClick)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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


private fun generateWeekDaysList(startDateOfWeek: LocalDate, today: LocalDate): List<WeeklyCalendarDay> {
    val weekFields = WeekFields.of(Locale.getDefault())
    val firstDayOfActualWeek = startDateOfWeek.with(weekFields.firstDayOfWeek)

    return List(7) { i ->
        val date = firstDayOfActualWeek.plusDays(i.toLong())
        WeeklyCalendarDay(
            date = date,
            isToday = date.isEqual(today)
        )
    }
}

@Preview(showBackground = true, name = "BbangZip Weekly Calendar")
@Composable
fun WeeklyCalendarPreview() {
    BBANGZIPANDROIDTheme {
        BbangZipWeeklyCalendar(onDateSelected = {
            println("Weekly Selected date: $it")
        })
    }
}

