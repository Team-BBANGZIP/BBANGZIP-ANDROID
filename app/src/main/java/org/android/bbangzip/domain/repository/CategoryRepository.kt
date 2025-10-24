package org.android.bbangzip.domain.repository

import org.android.bbangzip.domain.model.Category


interface CategoryRepository{
    suspend fun getCategories(): Result<List<Category>>

    suspend fun addCategory(
        name: String,
        color: String,
    ): Result<Category>

    suspend fun modifyCategory(
        categoryId: Long,
        name: String?,
        color: String?,
        isStopped: Boolean?,
    ): Result<Category>

    suspend fun reorderCategories(
        categoryOrder: List<Long>
    ): Result<Any>

    suspend fun deleteCategory(
        categoryId: Long,
    ): Result<Any>
}
