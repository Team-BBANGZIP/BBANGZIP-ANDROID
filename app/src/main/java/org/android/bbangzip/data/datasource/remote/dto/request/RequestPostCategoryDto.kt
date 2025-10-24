package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestPostCategoryDto(
    val name: String,
    val color: String,
)
