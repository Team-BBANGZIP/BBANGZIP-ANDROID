package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.UserTokenInfo

@Serializable
data class ResponseGetUserDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isOnboardingComplete")
    val isSignUpComplete: Boolean,
) {
    fun toUserTokenInfo() =
        UserTokenInfo(
            accessToken = accessToken,
            refreshToken = refreshToken,
            isSignUpComplete = isSignUpComplete,
        )
}
