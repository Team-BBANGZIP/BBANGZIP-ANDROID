package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.BreadInfo
import org.android.bbangzip.domain.model.BreadList

@Serializable
data class ResponseBreadListDto(
    val totalCount: Int,
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
    val breadId: Long,
    val breadName: String,
    val isUnlocked: Boolean,
    val requiredCount: Int,
    val imageUrl: String,
) {
    fun toBreadInfo() =
        BreadInfo(
            breadId = breadId,
            breadName = breadName,
            isUnlocked = isUnlocked,
            requiredCount = requiredCount,
            imageUrl = imageUrl,
        )
}
