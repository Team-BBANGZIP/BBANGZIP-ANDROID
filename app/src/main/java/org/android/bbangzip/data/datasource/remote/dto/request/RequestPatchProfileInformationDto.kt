package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPatchProfileInformationDto(
    @SerialName("profileImageKey")
    val profileImageKey: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("commitmentMessage")
    val commitmentMessage: String,
)
