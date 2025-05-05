package org.android.bbangzip.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BBANGZIPANDROIDTheme(
    content: @Composable () -> Unit,
) {
    val bbangZipColor = defaultBbangZipColor
    val bbangZipOpacity = defaultBbangZipOpacity
    val bbangZipBrush = defaultBbangZipBrush

    MaterialTheme(
        content = {
            CompositionLocalProvider(
                LocalBbangZipColor provides bbangZipColor,
                LocalBbangZipOpacity provides bbangZipOpacity,
                LocalBbangZipBrush provides bbangZipBrush,
            ) {
                content()
            }
        },
    )
}
