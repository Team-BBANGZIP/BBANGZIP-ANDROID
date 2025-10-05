package org.android.bbangzip.presentation.common.component.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun BbangZipPreviewWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    BBANGZIPANDROIDTheme {
        Surface(
            color = BbangZipTheme.color.backgroundAlternative_FAF6F3,
            modifier = modifier
        ) {
            content()
        }
    }
}