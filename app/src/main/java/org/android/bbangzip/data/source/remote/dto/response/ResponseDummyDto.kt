package org.android.bbangzip.data.source.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.Dummy

@Serializable
data class ResponseDummyDto(
    @SerialName("dummy")
    val dummyName: String,
) {
    fun toDummyEntity() =
        Dummy(
            dummyName = dummyName,
        )
}
