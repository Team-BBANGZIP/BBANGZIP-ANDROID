package org.android.bbangzip.data.source.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTodayBreadDto(
    @SerialName("todayBakedCount")
    val todayBakedCount: Int,
)
