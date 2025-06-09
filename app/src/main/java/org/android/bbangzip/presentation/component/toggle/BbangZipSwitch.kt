package org.android.bbangzip.presentation.component.toggle

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.component.toggle.model.SwitchColors
import org.android.bbangzip.presentation.util.extension.dropShadow
import org.android.bbangzip.presentation.util.extension.innerShadow
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BbangZipSwitch(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    switchColors: SwitchColors = BbangZipSwitchDefaults.colors(),
    containerPadding: Dp = BbangZipSwitchDefaults.CONTAINER_PADDING,
) {
    BoxWithConstraints(modifier = modifier) {
        val switchWidth = maxWidth

        val dimensions =
            remember(switchWidth, containerPadding) {
                calculateSwitchDimensions(switchWidth, containerPadding)
            }

        val transition = updateTransition(targetState = isChecked)

        // thumb 색상 애니메이션
        val thumbColor by transition.animateColor { isChecked ->
            if (isChecked) switchColors.checkedThumbColor else switchColors.uncheckedThumbColor
        }

        // 배경색 애니메이션
        val containerColor by transition.animateColor { isChecked ->
            if (isChecked) switchColors.checkedContainerColor else switchColors.uncheckedContainerColor
        }

        // thumb 이동 애니메이션
        val thumbOffset by transition.animateDp { isChecked ->
            if (isChecked) dimensions.maxThumbOffset else 0.dp
        }

        Box(
            modifier =
                Modifier
                    .width(switchWidth)
                    .height(dimensions.switchHeight)
                    .clip(dimensions.switchShape())
                    .background(containerColor)
                    .innerShadow(
                        shape = dimensions.switchShape(),
                        color = Color(0xFF505459).copy(alpha = 0.08f),
                        blur = 4.dp,
                        offsetX = 0.dp,
                        offsetY = 1.dp,
                        spread = 0.dp,
                    )
                    .noRippleClickable(
                        onClick = onCheckedChange,
                    )
                    .padding(containerPadding),
        ) {
            Box(
                modifier =
                    Modifier
                        .size(dimensions.thumbDiameter)
                        .offset { IntOffset(x = thumbOffset.roundToPx(), y = 0) }
                        .clip(dimensions.thumbShape())
                        .innerShadow(
                            shape = dimensions.thumbShape(),
                            color = Color(0xFFEDEDED).copy(alpha = 0.3f),
                            blur = 1.dp,
                            offsetX = 0.dp,
                            offsetY = 1.dp,
                            spread = 0.dp,
                        )
                        .dropShadow(
                            shape = dimensions.thumbShape(),
                            color = Color(0xFF5C636D).copy(alpha = 0.12f),
                            blur = 3.dp,
                            offsetX = 1.dp,
                            offsetY = (-1).dp,
                            spread = 0.dp,
                        )
                        .background(thumbColor),
            )
        }
    }
}

// 토글 스위치 치수 계산 함수
private fun calculateSwitchDimensions(
    switchWidth: Dp,
    switchPadding: Dp,
): SwitchDimensions {
    // 스위치 가용가능 너비: 스위치 전체 너비에서 패딩을 뺀 값
    val availableWidth = switchWidth - (switchPadding * 2)

    // thumb의 지름 계산: (전체 너비 - 패딩*2) / 2
    val thumbDiameter = availableWidth / 2

    // thumb의 borderRadius : 원을 나타내고 싶으므로 반지름과 동일
    val thumbBorderRadius = thumbDiameter / 2

    // 스위치 높이 계산: thumb 지름 + (패딩 * 2)
    val switchHeight = thumbDiameter + (switchPadding * 2)

    // 스위치의 borderRadius : thumb 반지름 + switchPadding
    val switchBorderRadius = switchHeight / 2

    // thumb 최대 offset
    val maxThumbOffset = availableWidth - thumbDiameter

    return SwitchDimensions(
        availableWidth = availableWidth,
        thumbDiameter = thumbDiameter,
        thumbCornerRadius = thumbBorderRadius,
        switchHeight = switchHeight,
        switchCornerRadius = switchBorderRadius,
        maxThumbOffset = maxThumbOffset,
    )
}

// 스위치 치수를 담는 데이터 클래스
private data class SwitchDimensions(
    val availableWidth: Dp,
    val thumbDiameter: Dp,
    val thumbCornerRadius: Dp,
    val switchHeight: Dp,
    val switchCornerRadius: Dp,
    val maxThumbOffset: Dp,
) {
    fun thumbShape() = RoundedCornerShape(thumbCornerRadius)

    fun switchShape() = RoundedCornerShape(switchCornerRadius)
}

@Preview(showBackground = true)
@Composable
private fun AnimatedToggleButtonPreview() {
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(false) }
    var isChecked3 by remember { mutableStateOf(false) }

    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp),
        ) {
            BbangZipSwitch(
                isChecked = isChecked1,
                onCheckedChange = { isChecked1 = !isChecked1 },
            )

            BbangZipSwitch(
                isChecked = isChecked2,
                onCheckedChange = { isChecked2 = !isChecked2 },
                switchColors = BbangZipSwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = Color.Red,
                ),
                modifier = Modifier.fillMaxWidth(0.5f),
            )

            BbangZipSwitch(
                isChecked = isChecked3,
                onCheckedChange = { isChecked3 = !isChecked3 },
                modifier = Modifier.fillMaxWidth(0.25f),
                switchColors = BbangZipSwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = Color.Red,
                    checkedContainerColor = Color.Blue,
                    uncheckedContainerColor = Color.Yellow,
                )
            )
        }
    }
}
