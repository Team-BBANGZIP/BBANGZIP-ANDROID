package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestPatchCategoryDto(
    val name: String?,
    val color: String?,
    val isStopped: Boolean?,
)
