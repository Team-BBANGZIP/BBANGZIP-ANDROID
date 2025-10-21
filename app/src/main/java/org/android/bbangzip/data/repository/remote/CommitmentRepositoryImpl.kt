package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.datasource.remote.CommitmentRemoteDataSource
import org.android.bbangzip.domain.model.CommitmentMessage
import org.android.bbangzip.domain.repository.CommitmentRepository
import javax.inject.Inject

class CommitmentRepositoryImpl
    @Inject
    constructor(
        private val commitmentRemoteDataSource: CommitmentRemoteDataSource
    )
    : CommitmentRepository {
    override suspend fun submitCommitmentMessage(commitmentMessage: String): Result<CommitmentMessage> =
        runCatching {
            val response = commitmentRemoteDataSource.postTodoCommitment(commitmentMessage = commitmentMessage)

            val data = response.data ?: throw IllegalStateException("Data가 존재하지 않습니다.")

            data.toCommitmentMessage()
        }

}