package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush

@Immutable
data class BbangZipBrush(
    val backgroundAccentGradient: Brush,
)

val defaultBbangZipBrush =
    BbangZipBrush(
        backgroundAccentGradient =
            Brush.linearGradient(
                colors = listOf(Common0, Apricot1),
            ),
    )

val LocalBbangZipBrush = staticCompositionLocalOf { defaultBbangZipBrush }
