package org.android.bbangzip.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun BBANGZIPANDROIDTheme(
    content: @Composable () -> Unit,
) {
    // 상태 표시줄 아이콘 색상 어둡게 변경
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
        }
    }

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
