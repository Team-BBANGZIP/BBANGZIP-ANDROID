package org.android.bbangzip.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCompleteTimerDto(
    @SerialName("count")
    val count: Int,
)
