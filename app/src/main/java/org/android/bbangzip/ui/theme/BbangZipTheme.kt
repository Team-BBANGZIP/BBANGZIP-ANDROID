package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object BbangZipTheme {
    val typography: BbangZipTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalBbangZipTypography.current
}
