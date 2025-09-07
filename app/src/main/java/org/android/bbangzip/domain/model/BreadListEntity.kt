package org.android.bbangzip.domain.model

import kotlinx.serialization.SerialName

data class BreadListEntity(
    val totalCount: Int,
    val breadList: List<BreadInfoEntity>
)

class BreadInfoEntity(
    val breadId: Int,
    val breadName: String,
    val isUnlocked: Boolean,
    val requiredCount: Int,
)
