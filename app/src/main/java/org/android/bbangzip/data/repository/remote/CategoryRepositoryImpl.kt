package org.android.bbangzip.data.repository.remote

import org.android.bbangzip.data.datasource.remote.CategoryRemoteDataSource
import org.android.bbangzip.domain.model.Category
import org.android.bbangzip.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl
    @Inject
    constructor(
        private val categoryRemoteDataSource: CategoryRemoteDataSource,
    ): CategoryRepository{
    override suspend fun getCategories(): Result<List<Category>> =
        runCatching {
            val response = categoryRemoteDataSource.getCategories()
            val data = response.data ?: throw IllegalStateException("category data가 존재하지 않습니다.")
            data.map{ it.toCategory() }
        }


    override suspend fun addCategory(name: String, color: String): Result<Category> =
        runCatching {
            val response = categoryRemoteDataSource.postCategory(
                name = name,
                color = color
            )
            val data = response.data ?: throw IllegalStateException("category data가 존재하지 않습니다.")
            data.toCategory()
        }
    override suspend fun modifyCategory(
        categoryId: Long,
        name: String?,
        color: String?,
        isStopped: Boolean?
    ): Result<Category> =
        runCatching {
            val response = categoryRemoteDataSource.patchCategory(
                categoryId = categoryId,
                name = name,
                color = color,
                isStopped = isStopped
                )
            val data = response.data ?: throw IllegalStateException("category data가 존재하지 않습니다.")
            data.toCategory()
        }

    override suspend fun reorderCategories(categoryOrder: List<Long>): Result<Any> =
        runCatching {
            categoryRemoteDataSource.patchCategoryOrder(categoryOrder)
        }

    override suspend fun deleteCategory(categoryId: Long): Result<Any> =
        runCatching {
            categoryRemoteDataSource.deleteCategory(categoryId)
        }
}