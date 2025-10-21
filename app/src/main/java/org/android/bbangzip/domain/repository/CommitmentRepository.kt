package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.CommitmentMessage

interface CommitmentRepository {
    suspend fun postCommitmentMessage(
        commitmentMessage: String
    ): Result<CommitmentMessage>
}