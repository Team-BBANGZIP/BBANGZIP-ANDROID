package org.android.bbangzip.data.datasource.remote.dto.response

import org.android.bbangzip.domain.model.CommitmentMessage

data class ResponsePostCommitmentDto(
    val commitmentMessage: String,
) {
    fun toCommitmentMessage() =
        CommitmentMessage(
            commitmentMessage = commitmentMessage,
        )
}
