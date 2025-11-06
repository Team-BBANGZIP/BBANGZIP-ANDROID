package org.android.bbangzip.domain.model

data class UserTokenInfo(
    val accessToken: String,
    val refreshToken: String,
    val isSignUpComplete: Boolean,
)
