package org.android.bbangzip.data.source.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCompleteTimerDto(
    @SerialName("count")
    val count: Int,
)