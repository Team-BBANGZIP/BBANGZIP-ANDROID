package org.android.bbangzip.presentation.util.extension

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Int.pixelsToDp() = LocalDensity.current.run { this@pixelsToDp.toDp() }

@Composable
fun ColumnScope.Gap(height: Dp? = null) {
    if (height == null) {
        Spacer(modifier = Modifier.weight(1f))
    } else {
        Spacer(modifier = Modifier.height(height))
    }
}

@Composable
fun RowScope.Gap(width: Dp? = null) {
    if (width == null) {
        Spacer(modifier = Modifier.weight(1f))
    } else {
        Spacer(modifier = Modifier.width(width))
    }
}

@Composable
fun LazyItemScope.Gap(
    width: Dp = 0.dp,
    height: Dp = 0.dp,
) {
    if (width == 0.dp) {
        Spacer(modifier = Modifier.height(height))
    } else if (height == 0.dp) {
        Spacer(modifier = Modifier.width(width))
    }
}
