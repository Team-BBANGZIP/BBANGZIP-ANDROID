package org.android.bbangzip.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BBANGZIPANDROIDTheme(
    content: @Composable () -> Unit,
) {
    val bbangZipTypography = defaultBbangZipTypography

    MaterialTheme(
        content = {
            CompositionLocalProvider(
                LocalBbangZipTypography provides bbangZipTypography
            ) {
                content()
            }
        },
    )
}
