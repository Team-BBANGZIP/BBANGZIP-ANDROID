package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.TodoListInfo

interface TodoRepository {
    suspend fun getTodoList(date: String): Result<TodoListInfo>
}