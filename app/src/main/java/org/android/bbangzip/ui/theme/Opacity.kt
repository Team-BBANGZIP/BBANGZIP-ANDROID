package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class BbangZipOpacity(
    val opacity5: Float,
    val opacity10: Float,
    val opacity20: Float,
    val opacity30: Float,
    val opacity40: Float,
    val opacity50: Float,
    val opacity60: Float,
    val opacity70: Float,
    val opacity80: Float,
    val opacity90: Float,
    val opacity100: Float,
)

val defaultBbangZipOpacity =
    BbangZipOpacity(
        opacity5 = 0.05f,
        opacity10 = 0.1f,
        opacity20 = 0.2f,
        opacity30 = 0.3f,
        opacity40 = 0.4f,
        opacity50 = 0.5f,
        opacity60 = 0.6f,
        opacity70 = 0.7f,
        opacity80 = 0.8f,
        opacity90 = 0.9f,
        opacity100 = 1f,
    )

val LocalBbangZipOpacity = staticCompositionLocalOf { defaultBbangZipOpacity }
