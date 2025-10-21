package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.CommitmentMessage

interface CommitmentRepository {
    suspend fun submitCommitmentMessage(
        commitmentMessage: String
    ): Result<CommitmentMessage>
}