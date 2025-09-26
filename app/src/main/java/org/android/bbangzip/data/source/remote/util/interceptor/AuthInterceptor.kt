package org.android.bbangzip.data.source.remote.util.interceptor

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.android.bbangzip.domain.repository.local.UserRepository
import javax.inject.Inject

class AuthInterceptor
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            val accessToken =
                runBlocking {
                    userRepository.userPreferenceFlow
                        .map { it.accessToken }
                        .firstOrNull()
                }
            val isLogin =
                runBlocking {
                    userRepository.userPreferenceFlow
                        .map { it.isLogin }
                        .firstOrNull()
                }

            val authRequest =
                if (isLogin == true) {
                    originalRequest.newBuilder()
                        .addHeader(ACCESS_TOKEN, "$accessToken").build()
                } else {
                    originalRequest
                }
            val response = chain.proceed(authRequest)

            when (response.code) {
                EXPIRE_TOKEN_CODE -> {
                    // TODO 토큰 재발급 api 연동
                }
            }
            return response
        }

        companion object {
            const val ACCESS_TOKEN = "Authorization"
            const val EXPIRE_TOKEN_CODE = 401
        }
    }
