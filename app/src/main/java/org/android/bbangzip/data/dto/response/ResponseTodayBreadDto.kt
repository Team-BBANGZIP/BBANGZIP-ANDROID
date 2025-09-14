package org.android.bbangzip.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.TodayBreadCountEntity

@Serializable
data class ResponseTodayBreadDto(
    @SerialName("todayBakedCount")
    val todayBakedCount: Int,
) {
    fun toTodayBreadEntity() =
        TodayBreadCountEntity(
            todayBreadCount = todayBakedCount,
        )
}
