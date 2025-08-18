package org.android.bbangzip.presentation.component.timepicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.android.bbangzip.ui.theme.BbangZipTheme
import kotlin.math.abs
import kotlin.math.max

data class PickerStyle(
    val textStyle: TextStyle,
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val selectionIndicatorColor: Color
)

@Composable
fun defaultPickerStyle(): PickerStyle = PickerStyle(
    textStyle = LocalTextStyle.current,
    selectedTextColor = Color.Black,
    unselectedTextColor = BbangZipTheme.color.labelAssistive_C9C7C5,
    selectionIndicatorColor = Color.LightGray.copy(alpha = 0.5f)
)

internal fun LazyListState.closestItemIndex(paddingItemsCount: Int, itemsSize: Int): Int? {
    if (layoutInfo.visibleItemsInfo.isEmpty() || itemsSize == 0) return null
    val viewportCenterPx = layoutInfo.viewportSize.height / 2f
    return layoutInfo.visibleItemsInfo
        .minByOrNull { abs((it.offset + it.size / 2f) - viewportCenterPx) }
        ?.index?.let { targetPaddedIndex ->
            (targetPaddedIndex - paddingItemsCount).coerceIn(0, itemsSize - 1)
        }
}

internal suspend fun LazyListState.scrollToAndConfirmSettledItem(
    targetPaddedIndex: Int,
    scrollOffsetToCenterItem: Int
) {
    scrollToItem(targetPaddedIndex, scrollOffset = -scrollOffsetToCenterItem)
}

/**
 * iOS 스타일의 Wheel Picker 컴포넌트입니다.
 * 항목 목록을 수직으로 표시하며, 사용자는 스크롤하여 항목을 선택할 수 있습니다.
 * 선택된 항목은 중앙에 위치하며 시각적으로 강조됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier].
 * @param items 피커에 표시될 문자열 항목의 목록입니다.
 * @param initialIndex 초기에 선택될 항목의 인덱스입니다. 기본값은 0입니다.
 * @param visibleItemsCount 피커에 한 번에 보여질 항목의 수입니다. 홀수로 지정해야 중앙 항목이 명확해집니다. 기본값은 5입니다.
 * @param itemHeight 각 항목의 높이입니다. 기본값은 40.dp입니다.
 * @param pickerStyle 스타일 관련 속성들을 그룹화한 [PickerStyle] 객체입니다.
 * @param alignment LazyColumn 내부 아이템들의 수평 정렬입니다.
 * @param onItemSelected 사용자가 스크롤을 멈추고 항목이 최종적으로 선택되었을 때 호출되는 콜백입니다.
 *                     선택된 항목의 인덱스와 문자열 값을 전달합니다.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialIndex: Int = 0,
    visibleItemsCount: Int = 7,
    itemHeight: Dp = 32.dp,
    pickerStyle: PickerStyle = defaultPickerStyle(),
    alignment: Alignment.Horizontal,
    onItemSelected: (index: Int, item: String) -> Unit
) {
    val visibleItemsCount = remember{
        if(visibleItemsCount % 2 == 0) visibleItemsCount + 1 else visibleItemsCount
    }

    val density = LocalDensity.current
    val itemHeightPx = remember(itemHeight) { with(density) { itemHeight.toPx() } }

    val paddingItemsCount = remember(visibleItemsCount) { (visibleItemsCount - 1) / 2 }
    val paddedItems = remember(items, paddingItemsCount) {
        List(paddingItemsCount) { "" } + items + List(paddingItemsCount) { "" }
    }

    val correctedInitialIndex = remember(initialIndex, items.size) {
        initialIndex.coerceIn(0, items.size)
    }

    val targetPaddedIndexForInitial = remember(correctedInitialIndex, paddingItemsCount) {
        correctedInitialIndex + paddingItemsCount
    }
    val scrollOffsetToCenterItem = remember(itemHeightPx, visibleItemsCount) {
        ((itemHeightPx * visibleItemsCount) / 2f - itemHeightPx / 2f).toInt()
    }

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = targetPaddedIndexForInitial,
        initialFirstVisibleItemScrollOffset = -scrollOffsetToCenterItem
    )
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    var confirmedSelectedIndex by remember(correctedInitialIndex) { mutableIntStateOf(correctedInitialIndex) }

    LaunchedEffect(items, correctedInitialIndex, paddingItemsCount, scrollOffsetToCenterItem, itemHeightPx, listState) {
        if (items.isNotEmpty()) {
            val targetPaddedIdx = correctedInitialIndex + paddingItemsCount
            listState.scrollToAndConfirmSettledItem(targetPaddedIdx, scrollOffsetToCenterItem)
            if (confirmedSelectedIndex != correctedInitialIndex) {
                confirmedSelectedIndex = correctedInitialIndex
            }
        }
    }

    LaunchedEffect(listState, items, paddingItemsCount) {
        snapshotFlow { listState.isScrollInProgress }
            .filter { !it && items.isNotEmpty() }
            .map { listState.closestItemIndex(paddingItemsCount, items.size) }
            .distinctUntilChanged()
            .onEach { closestActualIndex ->
                closestActualIndex?.let { actualIndex ->
                    val closestItemInfo = listState.layoutInfo.visibleItemsInfo
                        .find { (it.index - paddingItemsCount).coerceIn(0, items.size -1) == actualIndex }

                    closestItemInfo?.let {
                        val viewportCenterPx = listState.layoutInfo.viewportSize.height / 2f
                        val desiredItemStartOffset = viewportCenterPx - (it.size / 2f)
                        val scrollDelta = it.offset - desiredItemStartOffset
                        if (abs(scrollDelta) > 0.1f) {
                            listState.animateScrollBy(-scrollDelta)
                        }
                    }

                    if (confirmedSelectedIndex != actualIndex) {
                        confirmedSelectedIndex = actualIndex
                        onItemSelected(actualIndex, items[actualIndex])
                    }
                }
            }
            .launchIn(this)
    }


    val visuallyCenteredPaddedIndex by remember {
        derivedStateOf {
            if (items.isEmpty() || listState.layoutInfo.visibleItemsInfo.isEmpty()) {
                targetPaddedIndexForInitial
            } else {
                val viewportCenterPx = listState.layoutInfo.viewportSize.height / 2f
                listState.layoutInfo.visibleItemsInfo
                    .minByOrNull { abs((it.offset + it.size / 2f) - viewportCenterPx) }
                    ?.index ?: targetPaddedIndexForInitial
            }
        }
    }

    Box(
        modifier = modifier
            .height(itemHeight * visibleItemsCount)
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 10.dp))
                .align(Alignment.Center)
                .background(BbangZipTheme.color.componentStrong_F6F6F5)
        )
        LazyColumn(
            state = listState,
            flingBehavior = snapBehavior,
            horizontalAlignment = alignment,
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = paddedItems.size,
                key = { paddedIndex ->
                    val actualIndex = paddedIndex - paddingItemsCount
                    items.getOrNull(actualIndex)?.let { "item_${it}_$actualIndex" } ?: "padding_$paddedIndex"
                }
            ) { paddedIndex ->
                val actualIndex = paddedIndex - paddingItemsCount
                val itemText = items.getOrNull(actualIndex) ?: ""
                val distanceToCenterNormalized = abs(paddedIndex - visuallyCenteredPaddedIndex).toFloat()

                val itemAlpha = remember(distanceToCenterNormalized) { max(0f, 1f - (distanceToCenterNormalized-1) * 0.4f) }

                val isConfirmedSelected = (actualIndex == confirmedSelectedIndex && actualIndex in items.indices)

                Box(
                    modifier = Modifier
                        .height(itemHeight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemText,
                        style = if(isConfirmedSelected) BbangZipTheme.typography.picker1SemiBold
                            else BbangZipTheme.typography.picker2SemiBold,
                        color = if (isConfirmedSelected) BbangZipTheme.color.labelStrong_463D34
                            else BbangZipTheme.color.labelAssistive_C9C7C5.copy(alpha = itemAlpha),
                    )
                }
            }
        }
    }
}
