package org.android.bbangzip.data.datasource.remote.dto.request

import kotlinx.serialization.Serializable
import org.android.bbangzip.data.datasource.remote.util.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class RequestPostTodoRepeatDto(
    @Serializable(with = LocalDateSerializer::class)
    val targetDate: LocalDate,
)
