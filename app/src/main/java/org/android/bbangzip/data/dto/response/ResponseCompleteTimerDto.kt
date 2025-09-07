package org.android.bbangzip.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.CompleteBreadCountEntity

@Serializable
data class ResponseCompleteTimerDto(
    @SerialName("count")
    val count: Int,
){
    fun toCompleteTimerEntity() =
        CompleteBreadCountEntity(
            count = count,
        )
}
