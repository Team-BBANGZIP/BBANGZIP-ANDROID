package org.android.bbangzip.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BBANGZIPANDROIDTheme(
    content: @Composable () -> Unit,
) {
    val bbangZipTypography = defaultBbangZipTypography
    val bbangZipColor = defaultBbangZipColor
    val bbangZipOpacity = defaultBbangZipOpacity
    val bbangZipBrush = defaultBbangZipBrush

    MaterialTheme(
        content = {
            CompositionLocalProvider(
                LocalBbangZipTypography provides bbangZipTypography,
                LocalBbangZipColor provides bbangZipColor,
                LocalBbangZipOpacity provides bbangZipOpacity,
                LocalBbangZipBrush provides bbangZipBrush,
            ) {
                content()
            }
        },
    )
}
