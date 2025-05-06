package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class BbangZipBrush(
    val backgroundAccentGradient: Brush,
)

val defaultBbangZipBrush =
    BbangZipBrush(
        backgroundAccentGradient =
            Brush.linearGradient(
                colors = listOf(Color(0xFFFFFFFF), Color(0xFFFAF6F3)),
            ),
    )

val LocalBbangZipBrush = staticCompositionLocalOf { defaultBbangZipBrush }
