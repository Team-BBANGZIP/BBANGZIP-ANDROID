package org.android.bbangzip.presentation.util.extension

import androidx.compose.animation.with
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalTime.formatTimeWithAmPm(): String {
    val formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.ENGLISH)
    return format(formatter)
}

fun LocalDate.startOfWeek(startDayOfWeek: DayOfWeek): LocalDate {
    return this.with(startDayOfWeek)
}
