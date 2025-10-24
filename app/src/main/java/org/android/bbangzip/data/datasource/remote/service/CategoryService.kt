package org.android.bbangzip.data.datasource.remote.service

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchCategoryDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPatchCategoryOrderDto
import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostCategoryDto
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseCategoryDto
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.API
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.CATEGORY
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants.VERSIONS
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryService {
    @GET("$API/$VERSIONS/$CATEGORY")
    suspend fun getCategories(): BaseResponse<List<ResponseCategoryDto>>

    @POST("$API/$VERSIONS/$CATEGORY")
    suspend fun postCategory(
        @Body requestPostCategoryDto: RequestPostCategoryDto
    ): BaseResponse<ResponseCategoryDto>

    @PATCH("$API/$VERSIONS/$CATEGORY/{categoryId}")
    suspend fun patchCategory(
        @Path("categoryId") categoryId: Long,
        @Body requestPatchCategoryDto: RequestPatchCategoryDto
    ): BaseResponse<ResponseCategoryDto>

    @PATCH("$API/$VERSIONS/$CATEGORY/order")
    suspend fun patchCategoryOrder(
        @Body requestPatchCategoryOrderDto: RequestPatchCategoryOrderDto
    ): BaseResponse<Unit>

    @DELETE("$API/$VERSIONS/$CATEGORY/{categoryId}")
    suspend fun deleteCategory(
        @Path("categoryId") categoryId: Long
    ): BaseResponse<Any>
}