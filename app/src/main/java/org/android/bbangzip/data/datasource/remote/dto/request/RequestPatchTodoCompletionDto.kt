package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestPatchTodoCompletionDto(
    val isCompleted: Boolean,
)
