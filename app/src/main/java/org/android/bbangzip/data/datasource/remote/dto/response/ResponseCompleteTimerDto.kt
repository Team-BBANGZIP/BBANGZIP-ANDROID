package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.BreadCount

@Serializable
data class ResponseCompleteTimerDto(
    val count: Int,
) {
    fun toBreadCount(): BreadCount =
        BreadCount(count = count)
}
