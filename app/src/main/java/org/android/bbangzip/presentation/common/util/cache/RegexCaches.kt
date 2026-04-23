package org.android.bbangzip.presentation.common.util.cache

object RegexCaches {
    // 1. \p{So} : 일반적인 기호 및 이모지 차단
    // 2. \p{Cn} : 할당되지 않은 깨진 문자 차단
    // 3. \x{10000}-\x{10FFFF} : 이모지가 가장 많이 서식하는 확장 영역(Surrogate) 차단
    // 4. \u200D : 유령 문자(ZWJ) 차단
    // 5. \uFE0F : 이모지 변환자(Variation Selector) 차단
    // 6. \u20E3 : 키캡 이모지 차단
    val STRICT_NO_EMOJI_REGEX = Regex("[\\p{So}\\p{Cn}\\x{10000}-\\x{10FFFF}\\u200D\\uFE0F\\u20E3]+")
}
