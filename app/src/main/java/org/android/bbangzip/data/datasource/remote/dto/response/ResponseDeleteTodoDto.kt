package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDeleteTodoDto(
    val completedCount: Int,
    val totalCount: Int
)
