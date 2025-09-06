package org.android.bbangzip.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTodayBreadDto(
    @SerialName("todayBakedCount")
    val todayBreadCount: Int,
)
