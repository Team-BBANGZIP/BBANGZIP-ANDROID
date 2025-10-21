package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.BreadCount

@Serializable
data class ResponseTodayBreadDto(
    val todayBakedCount: Int,
) {
    fun toBreadCount(): BreadCount =
        BreadCount(count = todayBakedCount)
}
