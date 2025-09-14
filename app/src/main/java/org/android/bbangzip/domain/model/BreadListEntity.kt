package org.android.bbangzip.domain.model

data class BreadListEntity(
    val totalCount: Int,
    val breadList: List<BreadInfoEntity>,
)

class BreadInfoEntity(
    val breadId: Int,
    val breadName: String,
    val isUnlocked: Boolean,
    val requiredCount: Int,
)
