package org.android.bbangzip.data.auth.interceptor

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.android.bbangzip.BuildConfig
import org.android.bbangzip.UserPreferences
import org.android.bbangzip.data.datasource.remote.dto.response.ResponseGetReissueDto
import org.android.bbangzip.data.datasource.remote.util.base.BaseResponse
import org.android.bbangzip.data.datasource.remote.util.constant.ApiConstants
import org.android.bbangzip.domain.repository.UserDefaultRepository
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val json: Json,
    private val userDefaultRepository: UserDefaultRepository,
    private val authEventManager: AuthEventManager
) : Interceptor {
    private fun getUserPreferences(): UserPreferences? {
        return runBlocking {
            userDefaultRepository.userPreferenceFlow.firstOrNull()
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val userPreferences = getUserPreferences()
        val accessToken = userPreferences?.accessToken
        val refreshToken = userPreferences?.refreshToken
        val isLogin = userPreferences?.isLogin

        val authRequest =
            if (isLogin == true && accessToken != null) {
                originalRequest.newBuilder()
                    .addHeader(AUTHORIZATION, accessToken).build()
            } else {
                originalRequest
            }

        val response = chain.proceed(authRequest)

        when (response.code) {
            EXPIRE_TOKEN_CODE -> {
                response.close()
                val refreshTokenRequest =
                    originalRequest.newBuilder().get()
                        .url("${BuildConfig.BASE_URL}${ApiConstants.VERSIONS}/${ApiConstants.AUTH}/${ApiConstants.REISSUE}")
                        .post("".toRequestBody())
                        .addHeader(AUTHORIZATION, refreshToken ?: "")
                        .build()

                val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                if (refreshTokenResponse.isSuccessful) {
                    val responseRefresh =
                        json.decodeFromString<BaseResponse<ResponseGetReissueDto>>(
                            refreshTokenResponse.body?.string() ?: throw IllegalStateException("\"refreshTokenResponse is null $refreshTokenResponse\""),
                        )

                    runBlocking {
                        with(userDefaultRepository) {
                            setAccessToken(BEARER + responseRefresh.data?.accessToken)
                            setRefreshToken(BEARER + responseRefresh.data?.refreshToken)
                        }
                    }

                    refreshTokenResponse.close()

                    val newRequest = newAuthBuilder(originalRequest)
                    return chain.proceed(newRequest)
                } else {
                    runBlocking {
                        authEventManager.emitEvent(AuthEvent.ForceLogout)

                        with(userDefaultRepository) {
                            clearRefreshToken()
                            clearAccessToken()
                        }
                    }
                }
            }
        }
        return response
    }

    private fun newAuthBuilder(originalRequest: Request): Request {
        val accessToken = getUserPreferences()?.accessToken
        return originalRequest.newBuilder()
            .addHeader(AUTHORIZATION, accessToken ?: "")
            .build()
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val EXPIRE_TOKEN_CODE = 401
        const val BEARER = "Bearer"
    }
}