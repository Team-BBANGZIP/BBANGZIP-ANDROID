package org.android.bbangzip.presentation.ui.managecategory

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.waitForUpOrCancellation
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.common.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.common.type.AutoScrollDirection
import org.android.bbangzip.presentation.common.type.CategoryColor
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.presentation.common.util.scroll.calculateScrollSpeed
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import timber.log.Timber

private const val LIST_HEADER_COUNT = 2
private val AUTO_SCROLL_THRESHOLD = 50.dp
private const val AUTO_SCROLL_DELAY = 8L

@Composable
fun ManageCategoryScreen(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    onTopBarTrailingIconClick: () -> Unit = {},
    onTopBarLeadingIconClick: () -> Unit = {},
    onCategoryChipClick: (Category) -> Unit = {},
    onCategoryDragEnd: (Int, Int) -> Unit = { _, _ -> },
) {
    val localDensity = LocalDensity.current

    val lazyListState = rememberLazyListState()

    // 자동 스크롤 변수
    val coroutineScope = rememberCoroutineScope()
    var autoScrollJob by remember { mutableStateOf<Job?>(null) }
    var columnHeight by remember { mutableIntStateOf(0) }
    val scrollThreshold = remember { with(localDensity) { AUTO_SCROLL_THRESHOLD.toPx() } }

    // 드래그앤 드랍 변수
    var draggingItem by remember { mutableStateOf<Category?>(null) }
    var draggingItemIndex by remember { mutableStateOf<Int?>(null) }
    var fakeOffset by remember { mutableStateOf(Offset.Zero) }
    var targetIndex by remember { mutableStateOf<Int?>(null) }
    var itemBound by remember { mutableStateOf<Rect?>(null) }
    var currentTouchPointY by remember { mutableStateOf(0f) }
    val itemSpacingPx = with(localDensity) { 20.dp.toPx() }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(BbangZipTheme.color.staticWhite_FFFFFF)
                .statusBarsPadding(),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        columnHeight = coordinates.size.height
                    }
                    .pointerInput(categories) {
                        awaitEachGesture {
                            val down = awaitFirstDown(requireUnconsumed = false)
                            val longPress = awaitLongPressOrCancellation(down.id)

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

                            if (longPress != null) {
                                fakeOffset = Offset(0f, pressedLazyColumnItem.offset.toFloat())
                                draggingItem = pressedItemOfCategories

                                try {
                                    drag(pointerId = longPress.id) { change ->
                                        change.consume()
                                        currentTouchPointY = change.position.y
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
                                        val scrollDirection =
                                            when {
                                                change.position.y < scrollThreshold -> AutoScrollDirection.UP
                                                change.position.y > columnHeight - scrollThreshold -> AutoScrollDirection.DOWN
                                                else -> AutoScrollDirection.NONE
                                            }
                                        if (scrollDirection != AutoScrollDirection.NONE) {
                                            if (autoScrollJob?.isActive != true) {
                                                autoScrollJob =
                                                    coroutineScope.launch {
                                                        while (isActive) {
                                                            val speed =
                                                                calculateScrollSpeed(
                                                                    direction = scrollDirection,
                                                                    touchPointY = currentTouchPointY,
                                                                    columnHeight = columnHeight,
                                                                    scrollThreshold = scrollThreshold,
                                                                )

                                                            targetIndex =
                                                                updateTargetIndex(
                                                                    lazyListState = lazyListState,
                                                                    touchPointY = currentTouchPointY,
                                                                    currentTargetIndex = targetIndex,
                                                                )

                                                            lazyListState.scrollBy(speed)

                                                            Timber.tag("autoScroll").d("targetIndex: $targetIndex")

                                                            delay(AUTO_SCROLL_DELAY)
                                                        }
                                                    }
                                            }
                                        } else {
                                            autoScrollJob?.cancel()
                                        }
                                    }
                                } finally {
                                    autoScrollJob?.cancel()
                                    targetIndex?.let {
                                        if (draggingItemIndex != it && it in categories.indices) {
                                            onCategoryDragEnd(
                                                draggingItemIndex!!,
                                                it,
                                            )
                                        }
                                    }
                                    draggingItem = null
                                    targetIndex = null
                                }
                            }else {
                                pressedItemOfCategories?.let{onCategoryChipClick(it)}
                            }
                        }
                    },
            state = lazyListState,
        ) {
            item {
                BbangZipBaseTopBar(
                    title = stringResource(R.string.manage_category_screen_title),
                    titleColor = BbangZipTheme.color.labelNormal_6B6560,
                    titleStyle = BbangZipTheme.typography.title2Medium,
                    leadingIcon = R.drawable.ic_arrow_left_24,
                    leadingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
                    trailingIcon = R.drawable.ic_plus_bold_24,
                    trailingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
                    onLeadingIconClick = onTopBarLeadingIconClick,
                    onTrailingIconClick = onTopBarTrailingIconClick,
                )
            }

            item { Gap(height = 22.dp) }

            items(count = categories.size, key = { index -> categories[index].id }) { index ->
                val category = categories[index]
                val animatedShiftTarget =
                    calculateAnimatedShift(
                        currentItemIndex = index,
                        currentDraggingItemIndex = draggingItemIndex,
                        targetIndex = targetIndex,
                        itemBound = itemBound,
                        itemSpacing = itemSpacingPx,
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
                    isClickable = false,
                    modifier =
                        Modifier
                            .padding(start = 20.dp)
                            .padding(vertical = 10.dp)
                            .graphicsLayer(
                                translationY = animatedShiftY,
                                alpha = if (category.id == draggingItem?.id) 0f else 1f,
                            )
                            .onGloballyPositioned { coordinates ->
                                itemBound = coordinates.boundsInParent()
                            },
                    isTrailingIconVisible = false,
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
                            shape = RoundedCornerShape(32.dp),
                        ),
            ) {
                BbangZipCategoryChip(
                    categoryColor = CategoryColor.fromString(item.color).color,
                    categoryName = item.name,
                    isTrailingIconVisible = false,
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
    Timber.tag("autoScroll").d("firstDraggableItemInfo: ${firstDraggableItemInfo?.index}")
    if (firstDraggableItemInfo != null && touchPointY < firstDraggableItemInfo.offset) {
        newTargetIndex = 0
    }

    Timber.tag("autoScroll").d("newTargetIndex: $newTargetIndex")

    return newTargetIndex
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManageCategoryScreenPreview() {
    val categories =
        remember {
            mutableStateListOf(
                Category(id = 1, name = "아침 루틴", color = "RED1", isStopped = false),
                Category(id = 2, name = "SOPT 안드로이드 파트", color = "YELLOW1", isStopped = false),
                Category(id = 3, name = "운동", color = "BLUE1", isStopped = true),
                Category(id = 4, name = "취미 생활", color = "GREEN1", isStopped = false),
                Category(id = 5, name = "사이드 프로젝트", color = "PURPLE1", isStopped = false),
                Category(id = 9, name = "여행 계획", color = "RED2", isStopped = true),
                Category(id = 10, name = "블로그 글쓰기", color = "YELLOW2", isStopped = false),
                Category(id = 11, name = "가족", color = "BLUE2", isStopped = false),
                Category(id = 12, name = "친구 약속", color = "GREEN2", isStopped = true),
                Category(id = 13, name = "업무", color = "PURPLE2", isStopped = false),
                Category(id = 17, name = "대학 과제", color = "RED1", isStopped = false),
                Category(id = 18, name = "자격증 공부", color = "YELLOW1", isStopped = true),
                Category(id = 19, name = "새로운 기술 학습", color = "BLUE1", isStopped = false),
                Category(id = 21, name = "취준", color = "GREEN1", isStopped = false),
                Category(id = 22, name = "개발", color = "PURPLE1", isStopped = false),
                Category(id = 23, name = "휴식", color = "RED1", isStopped = false),
                Category(id = 24, name = "액티비티", color = "YELLOW1", isStopped = false),
                Category(id = 25, name = "공부", color = "GREEN1", isStopped = false),
                Category(id = 26, name = "뉴스", color = "BLUE1", isStopped = false),
            )
        }
    BBANGZIPANDROIDTheme {
        ManageCategoryScreen(
            categories = categories,
            onCategoryDragEnd = { from, to ->
                categories.add(to, categories.removeAt(from))
            },
        )
    }
}
