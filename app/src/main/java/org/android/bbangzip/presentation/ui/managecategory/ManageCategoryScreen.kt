package org.android.bbangzip.presentation.ui.managecategory

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.common.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.common.type.CategoryColor
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import timber.log.Timber

private const val LIST_HEADER_COUNT = 2

data class CategoryItem(
    val id: Long,
    val name: String,
    val color: String,
    val isStopped: Boolean
)

@Composable
fun ManageCategoryScreen(
    categories: List<CategoryItem>,
    modifier: Modifier = Modifier,
    onTopBarTrailingIconClick: () -> Unit = {},
    onTopBarLeadingIconClick: () -> Unit = {},
    onCategoryChipClick: () -> Unit = {},
    onCategoryMove: (Int, Int) -> Unit = { _, _ -> },
){
    Timber.tag("dragEnd").d(categories.toString())
    val localDensity = LocalDensity.current

    val lazyListState = rememberLazyListState()

    var draggingItem by remember { mutableStateOf<CategoryItem?>(null) }
    var draggingItemIndex by remember { mutableStateOf<Int?>(null) }
    var fakeOffset by remember { mutableStateOf(Offset.Zero) }
    var targetIndex by remember { mutableStateOf<Int?>(null) }
    var itemBound by remember {mutableStateOf<Rect?>(null)}

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BbangZipTheme.color.staticWhite_FFFFFF)
            .statusBarsPadding()
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(categories){
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        val longPress = awaitLongPressOrCancellation(down.id)

                        if(longPress != null){
                            val pressedLazyColumnItem =
                                lazyListState.layoutInfo.visibleItemsInfo
                                    .firstOrNull {
                                        val itemTopY = it.offset
                                        val itemBottomY = it.offset + it.size
                                        down.position.y >= itemTopY && down.position.y <= itemBottomY
                                    } ?: return@awaitEachGesture

                            val pressedLazyColumnIndex = pressedLazyColumnItem.index
                            draggingItemIndex = pressedLazyColumnIndex - LIST_HEADER_COUNT
                            val pressedItemOfCategories = categories.getOrNull(draggingItemIndex!!)

                            fakeOffset = Offset(0f, pressedLazyColumnItem.offset.toFloat())
                            draggingItem = pressedItemOfCategories

                            try {
                                drag(pointerId = longPress.id){ change ->
                                    change.consume()
                                    fakeOffset +=
                                        Offset(
                                            x = change.position.x - change.previousPosition.x,
                                            y = change.position.y - change.previousPosition.y,
                                        )

                                    targetIndex =
                                        updateTargetIndex(
                                            lazyListState = lazyListState,
                                            touchPointY = change.position.y,
                                            currentTargetIndex = targetIndex,
                                        )
                                }
                            }finally {
                                Timber.tag("dragEnd").d("$targetIndex")
                                targetIndex?.let {
                                    if (draggingItemIndex != it && it in categories.indices) {
                                        onCategoryMove(
                                            draggingItemIndex!!,
                                            it,
                                        )
                                    }
                                }
                                draggingItem = null
                                targetIndex = null
                            }
                        }
                    }
                },
            state = lazyListState,
        ) {
            stickyHeader {
                BbangZipBaseTopBar(
                    title = stringResource(R.string.manage_category_screen_title),
                    titleColor = BbangZipTheme.color.labelNormal_6B6560,
                    titleStyle = BbangZipTheme.typography.title2Medium,
                    leadingIcon = R.drawable.ic_arrow_left_24,
                    leadingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
                    trailingIcon = R.drawable.ic_plus_bold_24,
                    trailingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
                )
            }

            item{ Gap(height = 22.dp) }

            items(count = categories.size, key = { index -> categories[index].id }) { index ->
                val category = categories[index]
                val animatedShiftTarget = calculateAnimatedShift(
                    currentItemIndex = index,
                    currentDraggingItemIndex = draggingItemIndex,
                    targetIndex = targetIndex,
                    itemBound = itemBound,
                )

                val animatedShiftY =
                    if (draggingItem == null) {
                        0f
                    } else {
                        animateFloatAsState(
                            targetValue = animatedShiftTarget,
                            animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic),
                        ).value
                    }

                BbangZipCategoryChip(
                    categoryColor = CategoryColor.fromString(category.color).color,
                    categoryName = category.name,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .padding(vertical = 10.dp)
                        .graphicsLayer(
                            translationY = animatedShiftY,
                            alpha = if(category.id == draggingItem?.id) 0f else 1f
                        )
                        .onGloballyPositioned{ coordinates ->
                            itemBound = coordinates.boundsInParent()
                        },
                    isTrailingIconVisible = false
                )
            }
        }

        draggingItem?.let { item ->
            Box(
                modifier =
                    Modifier
                        .padding(start = 20.dp)
                        .padding(vertical = 10.dp)
                        .offset(
                            x = with(localDensity) { fakeOffset.x.toDp() },
                            y = with(localDensity) { fakeOffset.y.toDp() },
                        )
                        .background(
                            color = BbangZipTheme.color.componentStrong_F6F6F5,
                            shape = RoundedCornerShape(32.dp)
                        ),
            ) {
                BbangZipCategoryChip(
                    categoryColor = CategoryColor.fromString(item.color).color,
                    categoryName = item.name,
                    isTrailingIconVisible = false
                )
            }
        }
    }
}

private fun calculateAnimatedShift(
    currentItemIndex: Int,
    currentDraggingItemIndex: Int?,
    targetIndex: Int?,
    itemBound: Rect?,
    itemSpacing: Float = 0f,
): Float {
    if (currentDraggingItemIndex == null || targetIndex == null) {
        return 0f
    }
    if (currentItemIndex == currentDraggingItemIndex) {
        return 0f
    }

    val draggingItemHeight = itemBound?.height ?: 0f
    val shiftAmount = draggingItemHeight + itemSpacing

    return when {
        currentDraggingItemIndex < targetIndex && currentItemIndex in (currentDraggingItemIndex + 1)..targetIndex -> -shiftAmount
        currentDraggingItemIndex > targetIndex && currentItemIndex in targetIndex until currentDraggingItemIndex -> shiftAmount
        else -> 0f
    }
}

private fun updateTargetIndex(
    lazyListState: LazyListState,
    touchPointY: Float,
    currentTargetIndex: Int?,
): Int {
    var newTargetIndex = currentTargetIndex ?: -1

    lazyListState.layoutInfo.visibleItemsInfo
        .firstOrNull { visibleItem ->
            val itemTopY = visibleItem.offset
            val itemBottomY = itemTopY + visibleItem.size
            touchPointY >= itemTopY && touchPointY <= itemBottomY
        }
        ?.let {
            val listIndex = it.index - LIST_HEADER_COUNT
            if (listIndex >= 0) {
                newTargetIndex = listIndex
            }
        }

    val firstDraggableItemInfo = lazyListState.layoutInfo.visibleItemsInfo.find { it.index >= LIST_HEADER_COUNT }
    if (firstDraggableItemInfo != null && touchPointY < firstDraggableItemInfo.offset) {
        newTargetIndex = 0
    }

    return newTargetIndex
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManageCategoryScreenPreview(
){
    val categories = remember{
        mutableStateListOf(
            CategoryItem(
                id = 1,
                name = "제 과제 빵점",
                color = "RED1",
                isStopped = false
            ),
            CategoryItem(
                id = 2,
                name = "SOPT",
                color = "YELLOW1",
                isStopped = false
            ),
            CategoryItem(
                id = 3,
                name = "솝트대학교",
                color = "BLUE1",
                isStopped = false
            ),
        )
    }
    BBANGZIPANDROIDTheme {
        ManageCategoryScreen(
            categories = categories,
            onCategoryMove = { from, to ->
                categories.add(to, categories.removeAt(from))
            }
        )
    }
}