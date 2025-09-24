package org.android.bbangzip.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestOnboardingDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("platform")
    val platform: String,
    @SerialName("profileImageKey")
    val profileImageKey: Int
)