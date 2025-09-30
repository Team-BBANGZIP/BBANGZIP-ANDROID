package org.android.bbangzip.presentation.common.component.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.common.component.toggle.model.SegmentedButtonColors
import org.android.bbangzip.ui.theme.BbangZipTheme

object BbangZipSegmentedButtonDefaults {
    val CONTAINER_PADDING = 1.dp
    val CONTAINER_CORNER_RADIUS = 32.dp

    val INDICATOR_VERTICAL_PADDING = 6.dp

    @Composable
    fun optionStyle() = BbangZipTheme.typography.body4Medium

    @Composable
    fun colors(
        selectedOptionColor: Color = BbangZipTheme.color.staticWhite_FFFFFF,
        unselectedOptionColor: Color = BbangZipTheme.color.primaryNormal_897869,
        containerColor: Color = BbangZipTheme.color.secondaryStrong_F2EAE4,
        indicatorColor: Color = BbangZipTheme.color.primaryNormal_897869,
    ): SegmentedButtonColors =
        remember(
            selectedOptionColor,
            unselectedOptionColor,
            containerColor,
            indicatorColor,
        ) {
            SegmentedButtonColors(
                selectedOptionColor = selectedOptionColor,
                unselectedOptionColor = unselectedOptionColor,
                containerColor = containerColor,
                indicatorColor = indicatorColor,
            )
        }
}
