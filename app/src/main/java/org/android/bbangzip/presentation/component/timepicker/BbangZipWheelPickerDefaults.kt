package org.android.bbangzip.presentation.component.timepicker

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme

@Immutable
data class WheelPickerColors(
    val selectedItemTextColor: Color,
    val unselectedItemTextColor: Color,
    val indicatorBackgroundColor: Color,
) {
    internal fun itemTextColor(
        isSelected: Boolean,
        alpha: Float,
    ): Color {
        return if (isSelected) selectedItemTextColor else unselectedItemTextColor.copy(alpha)
    }
}

@Immutable
data class WheelPickerTypography(
    val selectedItemTypography: TextStyle,
    val unselectedItemTypography: TextStyle,
) {
    internal fun itemTypography(isSelected: Boolean): TextStyle {
        return if (isSelected) selectedItemTypography else unselectedItemTypography
    }
}

object BbangZipWheelPickerDefaults {
    const val UNSELECTED_ITEM_ALPHA_FACTOR: Float = 0.4f
    const val UNSELECTED_ITEM_MIN_ALPHA: Float = 0f

    const val AM_PM_WEIGHT: Float = 125f / 335f
    const val HOUR_WEIGHT: Float = 95f / 335f
    const val MINUTE_WEIGHT: Float = 115f / 335f

    val DefaultItemHeight: Dp = 32.dp
    val IndicatorHeight: Dp = 44.dp

    val IndicatorCornerRadius: Dp = 10.dp
    val IndicatorShape: Shape = RoundedCornerShape(size = IndicatorCornerRadius)

    @Composable
    fun colors(
        selectedItemTextColor: Color = BbangZipTheme.color.labelStrong_463D34,
        unselectedItemTextColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        indicatorBackgroundColor: Color = BbangZipTheme.color.componentStrong_F6F6F5,
    ): WheelPickerColors =
        remember(
            selectedItemTextColor,
            unselectedItemTextColor,
            indicatorBackgroundColor,
        ) {
            WheelPickerColors(
                selectedItemTextColor = selectedItemTextColor,
                unselectedItemTextColor = unselectedItemTextColor,
                indicatorBackgroundColor = indicatorBackgroundColor,
            )
        }

    @Composable
    fun typography(
        selectedItemTypography: TextStyle = BbangZipTheme.typography.picker1SemiBold,
        unselectedItemTypography: TextStyle = BbangZipTheme.typography.picker2SemiBold,
    ): WheelPickerTypography =
        remember(
            selectedItemTypography,
            unselectedItemTypography,
        ) {
            WheelPickerTypography(
                selectedItemTypography = selectedItemTypography,
                unselectedItemTypography = unselectedItemTypography,
            )
        }
}
