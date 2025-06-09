package org.android.bbangzip.presentation.component.toggle

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.component.toggle.model.SegmentedButtonColors
import org.android.bbangzip.presentation.util.extension.dropShadow
import org.android.bbangzip.presentation.util.extension.innerShadow
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BbangZipSegmentedButton(
    options: List<String>,
    indexOfSelectedOption: Int,
    onOptionSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    colors: SegmentedButtonColors = BbangZipSegmentedButtonDefaults.colors(),
    containerPadding: Dp = BbangZipSegmentedButtonDefaults.CONTAINER_PADDING,
    indicatorVerticalPadding: Dp = BbangZipSegmentedButtonDefaults.INDICATOR_VERTICAL_PADDING,
    containerCornerRadius: Dp = BbangZipSegmentedButtonDefaults.CONTAINER_CORNER_RADIUS,
) {
    val localDensity = LocalDensity.current

    val indicatorCornerRadius = containerCornerRadius - containerPadding

    val containerShape = RoundedCornerShape(containerCornerRadius)

    BoxWithConstraints(
        modifier =
            modifier
                .background(
                    color = colors.containerColor,
                    shape = containerShape,
                )
                .innerShadow(
                    shape = containerShape,
                    color = Color(0xFF535752).copy(alpha = 0.08f),
                    blur = 4.dp,
                    offsetX = 0.dp,
                    offsetY = 1.dp,
                    spread = 0.dp,
                )
                .padding(containerPadding),
    ) {
        val itemWidth = maxWidth / options.size

        // 인디케이터 애니메이션
        val indicatorOffset by animateDpAsState(
            targetValue = itemWidth * indexOfSelectedOption,
        )

        var buttonHeight by remember { mutableStateOf(0.dp) }

        if (buttonHeight > 0.dp) {
            Indicator(
                itemWidth = itemWidth,
                height = buttonHeight,
                indicatorOffset = indicatorOffset,
                indicatorColor = colors.indicatorColor,
                indicatorCornerRadius = indicatorCornerRadius,
            )
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        buttonHeight =
                            with(localDensity) {
                                coordinates.size.height.toDp()
                            }
                    },
        ) {
            options.forEachIndexed { index, option ->
                val isSelected = index == indexOfSelectedOption

                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(vertical = indicatorVerticalPadding)
                            .noRippleClickable { onOptionSelect(index) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option,
                        color =
                            animateColorAsState(
                                targetValue = if (isSelected) colors.selectedOptionColor else colors.unselectedOptionColor,
                            ).value,
                        style = BbangZipSegmentedButtonDefaults.optionStyle(),
                    )
                }
            }
        }
    }
}

@Composable
private fun Indicator(
    itemWidth: Dp,
    height: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
    indicatorCornerRadius: Dp,
) {
    val indicatorShape = RoundedCornerShape(indicatorCornerRadius)

    Box(
        modifier =
            Modifier
                .width(itemWidth)
                .height(height)
                .offset { IntOffset(indicatorOffset.roundToPx(), 0) }
                .background(
                    color = indicatorColor,
                    shape = indicatorShape,
                )
                .innerShadow(
                    shape = indicatorShape,
                    color = Color(0xFFEDEDED).copy(alpha = 0.3f),
                    blur = 1.dp,
                    offsetX = 0.dp,
                    offsetY = 1.dp,
                    spread = 0.dp,
                )
                .dropShadow(
                    shape = indicatorShape,
                    color = Color(0xFF5C636D).copy(alpha = 0.12f),
                    blur = 3.dp,
                    offsetX = 1.dp,
                    offsetY = (-1).dp,
                    spread = 0.dp,
                ),
    )
}

@Preview(showBackground = true)
@Composable
fun BbangZipToggleSegmentedButtonPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            var selectedIndex by remember { mutableIntStateOf(0) }

            var selectedThreeOptionsIndex by remember { mutableIntStateOf(0) }

            BbangZipSegmentedButton(
                options = listOf("30분", "60분"),
                indexOfSelectedOption = selectedIndex,
                onOptionSelect = { index -> selectedIndex = index },
                modifier = Modifier.fillMaxWidth(0.25f),
            )

            BbangZipSegmentedButton(
                options = listOf("Option 1", "Option 2", "Option 3"),
                indexOfSelectedOption = selectedThreeOptionsIndex,
                onOptionSelect = { index -> selectedThreeOptionsIndex = index },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
