package org.android.bbangzip.presentation.common.util.extension

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getBbangZipTimerDate(): String {
    val nowInSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    val adjustedDateTime = nowInSeoul.minusHours(5)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return adjustedDateTime.format(formatter)
}
