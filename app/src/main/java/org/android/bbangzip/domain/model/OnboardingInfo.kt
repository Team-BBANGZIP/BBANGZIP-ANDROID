package org.android.bbangzip.domain.model

import org.android.bbangzip.data.datasource.remote.dto.request.RequestPostOnboardingDto

data class OnboardingInfo(
    val nickname: String,
    val img: Int,
) {
    fun toRequestPostOnboardingDto() =
        RequestPostOnboardingDto(
            nickname = nickname,
            profileImageKey = img,
            platform = "KAKAO",
        )
}
