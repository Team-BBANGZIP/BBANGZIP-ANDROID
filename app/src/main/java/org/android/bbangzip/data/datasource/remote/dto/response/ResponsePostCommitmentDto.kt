package org.android.bbangzip.data.datasource.remote.dto.response

import kotlinx.serialization.Serializable
import org.android.bbangzip.domain.model.CommitmentMessage

@Serializable
data class ResponsePostCommitmentDto(
    val commitmentMessage: String,
) {
    fun toCommitmentMessage() =
        CommitmentMessage(
            commitmentMessage = commitmentMessage,
        )
}
