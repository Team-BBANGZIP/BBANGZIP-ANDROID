package org.android.bbangzip.data.source.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDummyDto(
    @SerialName("dummy")
    val dummy: String,
)
