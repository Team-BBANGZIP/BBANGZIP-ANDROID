package org.android.bbangzip.data.repository.fake

import org.android.bbangzip.domain.model.Category
import org.android.bbangzip.domain.repository.CategoryRepository
import javax.inject.Inject

class FakeCategoryRepository
    @Inject
    constructor() : CategoryRepository {
        override suspend fun getCategories(): Result<List<Category>> {
            return Result.success(
                listOf(
                    Category(categoryId = 1, categoryName = "Fake Category 1", categoryColor = "RED1", isStopped = false, todos = emptyList()),
                    Category(categoryId = 2, categoryName = "Fake Category 2", categoryColor = "BLUE1", isStopped = false, todos = emptyList()),
                ),
            )
        }

        override suspend fun addCategory(
            name: String,
            color: String,
        ): Result<Category> {
            return Result.success(Category(categoryId = 3, categoryName = name, categoryColor = color, isStopped = false, todos = emptyList()))
        }

        override suspend fun modifyCategory(
            categoryId: Long,
            name: String?,
            color: String?,
            isStopped: Boolean?,
        ): Result<Category> {
            return Result.success(Category(categoryId = categoryId.toInt(), categoryName = name ?: "Modified", categoryColor = color ?: "RED1", isStopped = isStopped ?: false, todos = emptyList()))
        }

        override suspend fun reorderCategories(categoryOrder: List<Long>): Result<Unit> {
            return Result.success(Unit)
        }

        override suspend fun deleteCategory(categoryId: Long): Result<Unit> {
            return Result.success(Unit)
        }
    }
