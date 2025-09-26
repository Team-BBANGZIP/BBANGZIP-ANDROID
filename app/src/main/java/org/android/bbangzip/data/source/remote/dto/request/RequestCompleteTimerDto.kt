package org.android.bbangzip.data.source.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCompleteTimerDto(
    @SerialName("targetDate")
    val targetDate: String,
    @SerialName("count")
    val count: Int,
)
