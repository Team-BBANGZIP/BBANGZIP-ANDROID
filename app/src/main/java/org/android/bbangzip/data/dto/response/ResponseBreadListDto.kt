package org.android.bbangzip.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.BreadInfoEntity
import org.android.bbangzip.domain.model.BreadListEntity
import kotlin.collections.map

@Serializable
data class ResponseBreadListDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("breadList")
    val breadList: List<BreadInfoDto>,
) {
    fun toBreadListEntity() =
        BreadListEntity(
            totalCount = totalCount,
            breadList = breadList.map { it.toBreadInfoEntity() },
        )
}

@Serializable
data class BreadInfoDto(
    @SerialName("breadId")
    val breadId: Int,
    @SerialName("breadName")
    val breadName: String,
    @SerialName("isUnlocked")
    val isUnlocked: Boolean,
    @SerialName("requiredCount")
    val requiredCount: Int,
) {
    fun toBreadInfoEntity() =
        BreadInfoEntity(
            breadId = breadId,
            breadName = breadName,
            isUnlocked = isUnlocked,
            requiredCount = requiredCount,
        )
}
