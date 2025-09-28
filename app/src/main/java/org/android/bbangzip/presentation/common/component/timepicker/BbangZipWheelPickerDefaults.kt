package org.android.bbangzip.presentation.common.component.timepicker

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

/**
 * [BbangZipWheelPicker] 컴포저블에서 사용되는 색상들을 나타내는 데이터클래스
 *
 * [BbangZipWheelPickerDefaults.colors]를 사용하여 인스턴스를 생성할 수 있습니다.
 *
 * @property selectedItemTextColor 선택된 아이템의 텍스트 색상입니다.
 * @property unselectedItemTextColor 선택되지 않은 아이템의 텍스트 색상입니다.
 * @property indicatorBackgroundColor 중앙의 선택 표시기의 배경색입니다.
 */
@Immutable
data class WheelPickerColors(
    val selectedItemTextColor: Color,
    val unselectedItemTextColor: Color,
    val indicatorBackgroundColor: Color,
) {
    /**
     * 아이템의 선택 상태와 알파 값을 기반으로 텍스트 색상을 계산하는 함수
     *
     * @param isSelected 아이템이 현재 선택되었는지 여부입니다.
     * @param alpha 선택되지 않은 경우 텍스트 색상에 적용할 알파 투명도 값입니다.
     * @return 아이템 텍스트의 [Color] 값입니다.
     */
    internal fun itemTextColor(
        isSelected: Boolean,
        alpha: Float,
    ): Color {
        return if (isSelected) selectedItemTextColor else unselectedItemTextColor.copy(alpha)
    }
}

/**
 * [BbangZipWheelPicker] 컴포저블에서 사용되는 타이포그래피를 나타내는 데이터 클래스
 *
 * [BbangZipWheelPickerDefaults.typography]를 사용하여 인스턴스를 생성할 수 있습니다.
 *
 * @property selectedItemTypography 선택된 아이템의 텍스트 스타일입니다.
 * @property unselectedItemTypography 선택되지 않은 아이템의 텍스트 스타일입니다.
 */
@Immutable
data class WheelPickerTypography(
    val selectedItemTypography: TextStyle,
    val unselectedItemTypography: TextStyle,
) {
    /**
     * 아이템의 선택 상태에 따라 적절한 텍스트 스타일을 제공합니다.
     *
     * @param isSelected 아이템이 현재 선택되었는지 여부입니다.
     * @return 아이템의 [TextStyle]입니다.
     */
    internal fun itemTypography(isSelected: Boolean): TextStyle {
        return if (isSelected) selectedItemTypography else unselectedItemTypography
    }
}

/**
 * [BbangZipWheelPicker] 컴포저블의 기본값 및 팩토리 함수들을 포함하는 `object`
 */
object BbangZipWheelPickerDefaults {
    /**
     * 중앙으로부터의 거리에 따라 선택되지 않은 아이템의 알파 값을 계산하는 데 사용되는 계수
     *
     * 공식: `max(UnselectedItemMinAlpha, 1f - (distanceToCenterNormalized - 1) * UnselectedItemAlphaFactor)`
     */
    const val UNSELECTED_ITEM_ALPHA_FACTOR: Float = 0.4f

    /**
     * 선택되지 않은 아이템이 가질 수 있는 최소 알파 값
     */
    const val UNSELECTED_ITEM_MIN_ALPHA: Float = 0f

    /**
     * Am/Pm 피커의 weight 값
     */
    const val AM_PM_WEIGHT: Float = 125f / 335f

    /**
     * Hour 피커의 weight 값
     */
    const val HOUR_WEIGHT: Float = 95f / 335f

    /**
     * Minute 피커의 weight 값
     */
    const val MINUTE_WEIGHT: Float = 115f / 335f

    /**
     * [BbangZipWheelPicker]에서 사용되는 아이템 기본 높이
     */
    val DefaultItemHeight: Dp = 32.dp

    /**
     * [BbangZipWheelPicker]에서 사용되는 인디케이터 기본 높이
     */
    val IndicatorHeight: Dp = 44.dp

    val IndicatorCornerRadius: Dp = 10.dp

    /**
     * 선택 표시기의 모양
     *
     * [IndicatorCornerRadius]를 크기로 사용합니다.
     */
    val IndicatorShape: Shape = RoundedCornerShape(size = IndicatorCornerRadius)

    /**
     * [BbangZipWheelPicker]에서 사용되는 기본 색상들을 나타내는 [WheelPickerColors]를 생성하는 함수
     */
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

    /**
     * [BbangZipWheelPicker]에서 사용되는 기본 타이포그래피를 나타내는 [WheelPickerTypography]를 생성하는 함수
     */
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
