package org.android.bbangzip.data.source.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.BreadInfo
import org.android.bbangzip.domain.model.BreadList
import kotlin.collections.map

@Serializable
data class ResponseBreadListDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("breadList")
    val breadList: List<BreadInfoDto>,
) {
    fun toBreadList() =
        BreadList(
            totalCount = totalCount,
            breadList = breadList.map { it.toBreadInfo() },
        )
}

@Serializable
data class BreadInfoDto(
    @SerialName("breadId")
    val breadId: Long,
    @SerialName("breadName")
    val breadName: String,
    @SerialName("isUnlocked")
    val isUnlocked: Boolean,
    @SerialName("requiredCount")
    val requiredCount: Int,
) {
    fun toBreadInfo() =
        BreadInfo(
            breadId = breadId,
            breadName = breadName,
            isUnlocked = isUnlocked,
            requiredCount = requiredCount,
        )
}
