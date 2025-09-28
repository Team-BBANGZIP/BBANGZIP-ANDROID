package org.android.bbangzip.presentation.common.util.extension

import org.android.bbangzip.presentation.common.component.timepicker.AmPm
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalTime.formatTimeWithAmPm(): String {
    val formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.ENGLISH)
    return format(formatter)
}

fun LocalTime.to12HourText(): Int =
    if (this.hour == 0) {
        12
    } else if (this.hour > 12) {
        this.hour - 12
    } else {
        this.hour
    }

fun LocalTime.toAmPmText(): String = if (this.hour < 12) AmPm.AM.displayText else AmPm.PM.displayText

fun LocalDate.startOfWeek(startDayOfWeek: DayOfWeek): LocalDate {
    return this.with(startDayOfWeek)
}
