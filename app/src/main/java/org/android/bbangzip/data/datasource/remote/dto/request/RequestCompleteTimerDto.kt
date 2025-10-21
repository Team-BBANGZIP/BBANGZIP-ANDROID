package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestCompleteTimerDto(
    val targetDate: String,
    val count: Int,
)
