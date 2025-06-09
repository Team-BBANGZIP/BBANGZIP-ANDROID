package org.android.bbangzip.presentation.util.extension

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalTime.formatTimeWithAmPm(): String {
    val formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.ENGLISH)
    return format(formatter)
}
