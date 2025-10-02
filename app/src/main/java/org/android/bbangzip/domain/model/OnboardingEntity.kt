package org.android.bbangzip.domain.model

import org.android.bbangzip.data.dto.request.RequestOnboardingDto

data class OnboardingEntity(
    val nickname: String,
    val img: Int,
) {
    fun toOnboardingInfoDto() =
        RequestOnboardingDto(
            nickname = nickname,
            profileImageKey = img,
            platform = "KAKAO",
        )
}
