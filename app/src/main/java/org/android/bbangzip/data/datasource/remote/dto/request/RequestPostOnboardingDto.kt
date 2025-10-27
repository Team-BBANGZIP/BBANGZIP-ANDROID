package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostOnboardingDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("platform")
    val platform: String,
    @SerialName("profileImageKey")
    val profileImageKey: Int,
)
