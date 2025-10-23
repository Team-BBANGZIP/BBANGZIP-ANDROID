package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalTimeSerializer
import java.time.LocalTime

@Serializable
data class RequestPatchTodoTimeDto(
//    @Serializable(with = LocalTimeSerializer::class)
    val startTime: String?,
)
