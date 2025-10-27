package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostUserInfoDto(
    @SerialName("provider")
    val provider: String,
    @SerialName("role")
    val role: String,
    @SerialName("deviceName")
    val deviceName: String,
    @SerialName("deviceType")
    val deviceType: String,
    @SerialName("osType")
    val osType: String,
    @SerialName("osVersion")
    val osVersion: String,
    @SerialName("appVersion")
    val appVersion: String,
)