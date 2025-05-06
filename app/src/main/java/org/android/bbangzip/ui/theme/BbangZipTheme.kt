package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object BbangZipTheme {
    val typography: BbangZipTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalBbangZipTypography.current

    val color: BbangZipColor
        @Composable
        @ReadOnlyComposable
        get() = LocalBbangZipColor.current

    val opacity: BbangZipOpacity
        @Composable
        @ReadOnlyComposable
        get() = LocalBbangZipOpacity.current

    val brush: BbangZipBrush
        @Composable
        @ReadOnlyComposable
        get() = LocalBbangZipBrush.current
}
