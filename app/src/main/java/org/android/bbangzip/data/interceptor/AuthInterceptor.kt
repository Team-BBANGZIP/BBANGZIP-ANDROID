package org.android.bbangzip.data.interceptor

import android.app.Application
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.android.bbangzip.BuildConfig
import org.android.bbangzip.data.dto.response.ResponseReissueDto
import org.android.bbangzip.data.util.base.BaseResponse
import org.android.bbangzip.domain.repository.local.UserLocalRepository
import javax.inject.Inject

class AuthInterceptor
    @Inject
    constructor(
        private val json: Json,
        private val userLocalRepository: UserLocalRepository,
        private val context: Application,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            val accessToken =
                runBlocking {
                    userLocalRepository.userPreferenceFlow
                        .map { it.accessToken }
                        .firstOrNull()
                }
            val isLogin =
                runBlocking {
                    userLocalRepository.userPreferenceFlow
                        .map { it.isLogin }
                        .firstOrNull()
                }
            val refreshToken =
                runBlocking {
                    userLocalRepository.userPreferenceFlow
                        .map { it.refreshToken }
                        .firstOrNull()
                }

            val authRequest =
                if (isLogin == true) {
                    originalRequest.newBuilder()
                        .addHeader(AUTHORIZATION, "$accessToken").build()
                } else {
                    originalRequest
                }
            val response = chain.proceed(authRequest)

            when (response.code) {
                EXPIRE_TOKEN_CODE -> {
                    response.close()

                    val refreshTokenRequest =
                        originalRequest.newBuilder().get()
                            .url("${BuildConfig.BASE_URL}v1/auth/re-issue")
                            .post("".toRequestBody())
                            .addHeader(AUTHORIZATION, refreshToken ?: "")
                            .build()

                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                    if (refreshTokenResponse.isSuccessful) {
                        val responseRefresh =
                            json.decodeFromString<BaseResponse<ResponseReissueDto>>(
                                refreshTokenResponse.body?.string()
                                    ?: throw IllegalStateException("\"refreshTokenResponse is null $refreshTokenResponse\""),
                            )

                        runBlocking {
                            with(userLocalRepository) {
                                setAccessToken(BEARER + responseRefresh.data.accessToken)
                                setRefreshToken(BEARER + responseRefresh.data.refreshToken)
                            }
                        }

                        refreshTokenResponse.close()

                        val newRequest = newAuthBuilder(originalRequest)
                        return chain.proceed(newRequest)
                    } else {
                        with(context) {
                            CoroutineScope(Dispatchers.Main).launch {
                                startActivity(
                                    Intent.makeRestartActivityTask(packageManager.getLaunchIntentForPackage(packageName)?.component),
                                )
                            }

                            runBlocking {
                                with(userLocalRepository) {
                                    clearRefreshToken()
                                    clearAccessToken()
                                }
                            }
                        }
                    }
                }
            }
            return response
        }

    private fun newAuthBuilder(originalRequest: Request): Request {
        val accessToken = runBlocking { userLocalRepository.userPreferenceFlow.map { it.accessToken }.firstOrNull() }
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
