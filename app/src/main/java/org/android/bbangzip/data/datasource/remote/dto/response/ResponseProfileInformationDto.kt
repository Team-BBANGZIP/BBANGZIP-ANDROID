package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.ProfileInformation

@Serializable
data class ResponseProfileInformationDto(
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("profileImageKey")
    val profileImageKey: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("commitmentMessage")
    val commitmentMessage: String,
) {
    fun toProfileInformation() =
        ProfileInformation(
            profileImageUrl = profileImageUrl,
            profileImageKey = profileImageKey,
            nickname = nickname,
            commitmentMessage = commitmentMessage,
        )
}
