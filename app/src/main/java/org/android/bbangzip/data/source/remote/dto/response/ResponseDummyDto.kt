package org.android.bbangzip.data.source.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.DummyEntity

@Serializable
data class ResponseDummyDto(
    @SerialName("dummy")
    val dummyName: String,
) {
    fun toDummyEntity() =
        DummyEntity(
            dummyName = dummyName,
        )
}
