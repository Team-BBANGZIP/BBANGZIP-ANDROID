package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostCommitmentDto
import org.android.bbangzip.data.datasource.remote.service.UserService
import javax.inject.Inject

class CommitmentRemoteDataSource
    @Inject
    constructor(
        private val userService: UserService,
    ) {
        suspend fun postTodoCommitment(
            commitmentMessage: String,
        ) = userService.postTodoCommitment(
            requestTodoCommitmentDto =
                RequestPostCommitmentDto(
                    commitmentMessage = commitmentMessage,
                ),
        )
    }
