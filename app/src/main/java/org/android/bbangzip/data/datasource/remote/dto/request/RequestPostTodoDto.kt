package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalDateSerializer
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalTimeSerializer
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class RequestPostTodoDto(
    val categoryId: Long,
    val content: String,
    @Serializable(with = LocalDateSerializer::class)
    val targetDate: LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val startTime: LocalTime?,
)
