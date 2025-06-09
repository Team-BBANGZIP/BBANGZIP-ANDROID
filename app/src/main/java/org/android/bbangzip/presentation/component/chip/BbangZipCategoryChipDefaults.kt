package org.android.bbangzip.presentation.component.chip

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme

object BbangZipCategoryChipDefaults {
    val CHIP_SHAPE = RoundedCornerShape(32.dp)
    val CHIP_PADDING = PaddingValues(horizontal = 10.dp, vertical = 7.dp)

    val CONTENT_GAP = 6.dp

    val DOT_SIZE = 11.dp

    val ICON_SIZE = 18.dp

    @Composable
    fun containerColor(): Color = BbangZipTheme.color.secondaryLight_FAF6F3

    @Composable
    fun categoryNameColor(): Color = BbangZipTheme.color.labelStrong_463D34

    @Composable
    fun iconColor(): Color = BbangZipTheme.color.labelAlternative_A29D96

    @Composable
    fun categoryNameStyle(): TextStyle = BbangZipTheme.typography.label3SemiBold
}
