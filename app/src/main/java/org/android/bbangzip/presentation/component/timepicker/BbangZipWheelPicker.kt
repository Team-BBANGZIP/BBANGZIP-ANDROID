package org.android.bbangzip.presentation.component.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max

private const val DEFAULT_INITIAL_INDEX = 0

/**
 * 사용자 정의 가능한 iOS 스타일의 휠 피커(Wheel Picker) 컴포저블
 *
 * 아이템 리스트를 스크롤하여 하나의 아이템을 선택할 수 있도록 합니다.
 *
 * @param items 표시할 문자열 아이템 리스트입니다.
 * @param onItemSelected 아이템이 선택되었을 때 호출되는 콜백 함수입니다. 선택된 아이템의 인덱스와 문자열 값을 전달받습니다.
 * @param paddingItemsCount 아이템 리스트의 위아래에 추가될 빈 아이템의 개수입니다. 이는 스크롤 시 시각적인 패딩 효과를 줍니다.
 * @param colors 휠 피커의 색상 설정을 담고 있는 [WheelPickerColors] 객체입니다.
 * @param typography 휠 피커의 텍스트 스타일 설정을 담고 있는 [WheelPickerTypography] 객체입니다.
 * @param modifier 컴포저블에 적용할 [Modifier]입니다.
 * @param initialIndex 초기에 선택될 아이템의 인덱스입니다. 기본값은 [DEFAULT_INITIAL_INDEX] (0)입니다.
 * @param itemHeight 각 아이템의 높이입니다. 기본값은 [BbangZipWheelPickerDefaults.DefaultItemHeight]입니다.
 * @param alignment 아이템 텍스트의 가로 정렬 방식입니다. 기본값은 [Alignment.CenterHorizontally]입니다.
 */
@Composable
fun BbangZipWheelPicker(
    items: List<String>,
    onItemSelected: (index: Int, item: String) -> Unit,
    paddingItemsCount: Int,
    colors: WheelPickerColors,
    typography: WheelPickerTypography,
    modifier: Modifier = Modifier,
    initialIndex: Int = DEFAULT_INITIAL_INDEX,
    itemHeight: Dp = BbangZipWheelPickerDefaults.DefaultItemHeight,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    indicatorShape: Shape = BbangZipWheelPickerDefaults.IndicatorShape
) {
    val visibleItemsCount =
        remember(paddingItemsCount) {
            paddingItemsCount * 2 + 1
        }

    val density = LocalDensity.current
    val itemHeightPx = remember(itemHeight, density) { with(density) { itemHeight.toPx() } }

    val paddedItems =
        remember(items, paddingItemsCount) {
            List(paddingItemsCount) { "" } + items + List(paddingItemsCount) { "" }
        }

    val correctedInitialIndex =
        remember(initialIndex, items.size) {
            initialIndex.coerceIn(0, items.size)
        }

    val targetPaddedIndexForInitial =
        remember(correctedInitialIndex, paddingItemsCount) {
            correctedInitialIndex + paddingItemsCount
        }
    val scrollOffsetToCenterItem =
        remember(itemHeightPx, visibleItemsCount) {
            calculateCenterOffset(itemHeightPx, visibleItemsCount)
        }

    val listState =
        rememberLazyListState(
            initialFirstVisibleItemIndex = targetPaddedIndexForInitial,
            initialFirstVisibleItemScrollOffset = -scrollOffsetToCenterItem,
        )
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    var selectedIndex by remember(correctedInitialIndex) { mutableIntStateOf(correctedInitialIndex) }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        selectedIndex = listState.firstVisibleItemIndex
        onItemSelected(selectedIndex, items[selectedIndex])
    }

    Box(
        modifier =
            modifier
                .height(itemHeight * visibleItemsCount)
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Indicator(
            backgroundColor = colors.indicatorBackgroundColor,
            indicatorShape = indicatorShape,
            )

        LazyColumn(
            state = listState,
            flingBehavior = snapBehavior,
            horizontalAlignment = alignment,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                count = paddedItems.size,
                key = { paddedIndex ->
                    val actualIndex = paddedIndex - paddingItemsCount
                    items.getOrNull(actualIndex) ?: "padding_$paddedIndex"
                },
            ) { paddedIndex ->
                val actualIndex = paddedIndex - paddingItemsCount
                val itemText = paddedItems[paddedIndex]
                val distanceToCenterNormalized = abs(paddedIndex - (selectedIndex + paddingItemsCount)).toFloat()

                val itemAlpha = remember(distanceToCenterNormalized) { max(BbangZipWheelPickerDefaults.UNSELECTED_ITEM_MIN_ALPHA, 1f - (distanceToCenterNormalized - 1) * BbangZipWheelPickerDefaults.UNSELECTED_ITEM_ALPHA_FACTOR) }

                val isSelected = (actualIndex in items.indices && actualIndex == selectedIndex)

                Box(
                    modifier =
                        Modifier
                            .height(itemHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = itemText,
                        style = typography.itemTypography(isSelected = isSelected),
                        color = colors.itemTextColor(isSelected = isSelected, alpha = itemAlpha),
                    )
                }
            }
        }
    }
}

private fun calculateCenterOffset(
    itemHeightPx: Float,
    visibleItemsCount: Int,
): Int = ((itemHeightPx * visibleItemsCount) / 2f - itemHeightPx / 2f).toInt()

/**
 * 휠 피커의 중앙 선택 영역을 나타내는 인디케이터 컴포저블입니다.
 *
 * @param backgroundColor 인디케이터의 배경색입니다.
 * @param modifier 컴포저블에 적용할 [Modifier]입니다.
 */
@Composable
private fun Indicator(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    indicatorShape: Shape = BbangZipWheelPickerDefaults.IndicatorShape
) {
    Box(
        modifier =
            modifier
                .height(height = BbangZipWheelPickerDefaults.IndicatorHeight)
                .fillMaxWidth()
                .clip(shape = indicatorShape)
                .background(color = backgroundColor),
    )
}

@Preview(showBackground = true)
@Composable
fun BbangZipWheelPickerPreview() {
    val items = List(20) { "Item ${it + 1}" }

    BbangZipWheelPicker(
        items = items,
        initialIndex = 5,
        paddingItemsCount = 3,
        itemHeight = 32.dp,
        alignment = Alignment.CenterHorizontally,
        onItemSelected = { index, item -> },
        colors = BbangZipWheelPickerDefaults.colors(),
        typography = BbangZipWheelPickerDefaults.typography(),
    )
}
