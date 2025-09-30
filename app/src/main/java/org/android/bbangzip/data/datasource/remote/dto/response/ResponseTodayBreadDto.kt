package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTodayBreadDto(
    @SerialName("todayBakedCount")
    val todayBakedCount: Int,
)
