package org.android.bbangzip.data.repository.fake

import org.android.bbangzip.domain.model.CommitmentMessage
import org.android.bbangzip.domain.repository.CommitmentRepository
import javax.inject.Inject

class FakeCommitmentRepository @Inject constructor() : CommitmentRepository {
    override suspend fun submitCommitmentMessage(commitmentMessage: String): Result<CommitmentMessage> {
        return Result.success(CommitmentMessage(commitmentMessage = commitmentMessage))
    }
}
