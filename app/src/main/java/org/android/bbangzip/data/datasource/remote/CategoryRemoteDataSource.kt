package org.android.bbangzip.data.datasource.remote

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchCategoryDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchCategoryOrderDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostCategoryDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseCategoryDto
import org.android.bbangzip.data.datasource.remote.service.CategoryService
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import javax.inject.Inject

class CategoryRemoteDataSource
    @Inject
    constructor(
        private val categoryService: CategoryService,
    ) {
        suspend fun getCategories(): BaseResponse<List<ResponseCategoryDto>> =
            categoryService.getCategories()

        suspend fun postCategory(
            name: String,
            color: String,
        ): BaseResponse<ResponseCategoryDto> =
            categoryService.postCategory(
                requestPostCategoryDto =
                    RequestPostCategoryDto(
                        name = name,
                        color = color,
                    ),
            )

        suspend fun patchCategory(
            categoryId: Long,
            name: String?,
            color: String?,
            isStopped: Boolean?,
        ): BaseResponse<ResponseCategoryDto> =
            categoryService.patchCategory(
                categoryId = categoryId,
                requestPatchCategoryDto =
                    RequestPatchCategoryDto(
                        name = name,
                        color = color,
                        isStopped = isStopped,
                    ),
            )

        suspend fun patchCategoryOrder(
            categoryOrder: List<Long>,
        ): BaseResponse<Unit> =
            categoryService.patchCategoryOrder(
                requestPatchCategoryOrderDto =
                    RequestPatchCategoryOrderDto(
                        categoryOrder = categoryOrder,
                    ),
            )

        suspend fun deleteCategory(
            categoryId: Long,
        ): BaseResponse<Unit> =
            categoryService.deleteCategory(
                categoryId = categoryId,
            )
    }
