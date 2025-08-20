package org.android.bbangzip.presentation.component.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.max

@Composable
fun BbangZipWheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialIndex: Int = 0,
    paddingItemsCount: Int = 3,
    itemHeight: Dp = 32.dp,
    alignment: Alignment.Horizontal,
    onItemSelected: (index: Int, item: String) -> Unit
) {
    val visibleItemsCount = remember{
        paddingItemsCount * 2 + 1
    }

    val density = LocalDensity.current
    val itemHeightPx = remember(itemHeight) { with(density) { itemHeight.toPx() } }

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
    var selectedIndex by remember(correctedInitialIndex) { mutableIntStateOf(correctedInitialIndex) }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        selectedIndex = listState.firstVisibleItemIndex
        onItemSelected(selectedIndex, items[selectedIndex])
    }

    Box(
        modifier = modifier
            .height(itemHeight * visibleItemsCount)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Indicator()

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
                val itemText = paddedItems[paddedIndex]
                val distanceToCenterNormalized = abs(paddedIndex - targetPaddedIndexForInitial).toFloat()

                val itemAlpha = remember(distanceToCenterNormalized) { max(0f, 1f - (distanceToCenterNormalized-1) * 0.4f) }

                val isSelected = (actualIndex in items.indices && actualIndex == selectedIndex)

                Box(
                    modifier = Modifier
                        .height(itemHeight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemText,
                        style = if(isSelected) BbangZipTheme.typography.picker1SemiBold
                            else BbangZipTheme.typography.picker2SemiBold,
                        color = if (isSelected) BbangZipTheme.color.labelStrong_463D34
                            else BbangZipTheme.color.labelAssistive_C9C7C5.copy(alpha = itemAlpha),
                    )
                }
            }
        }
    }
}

@Composable
private fun Indicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(BbangZipTheme.color.componentStrong_F6F6F5)
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
        onItemSelected = { index, item ->
            Timber.d("Selected index: $index, item: $item")
        }
    )
}
