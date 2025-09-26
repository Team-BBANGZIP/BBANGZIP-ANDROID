package org.android.bbangzip.domain.model

data class BreadList(
    val totalCount: Int,
    val breadList: List<BreadInfo>,
)

class BreadInfo(
    val breadId: Long,
    val breadName: String,
    val isUnlocked: Boolean,
    val requiredCount: Int,
)
