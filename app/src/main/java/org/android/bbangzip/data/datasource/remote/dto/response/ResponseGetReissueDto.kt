package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.ReissueToken

@Serializable
data class ResponseGetReissueDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
) {
    fun toReissueToken() =
        ReissueToken(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
}
