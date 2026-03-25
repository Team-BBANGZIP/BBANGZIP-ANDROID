package org.android.bbangzip.presentation.common.util.extension

import org.android.bbangzip.presentation.common.component.timepicker.AmPm
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
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
    return this.with(TemporalAdjusters.previousOrSame(startDayOfWeek))
}

/**
 * LocalDate를 "yyyy-MM-dd" 형식의 표준 ISO 문자열로 변환합니다.
 */
fun LocalDate.toYyyyMmDdString(): String {
    return this.format(DateTimeFormatter.ISO_LOCAL_DATE)
}
