package org.android.bbangzip.presentation.component.taskbox

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import java.time.LocalTime
import kotlin.math.abs

@Composable
fun BbangzipDraggableListExample() {
    val categories =
        remember {
            mutableStateListOf(
                TaskCategory(
                    name = "제과 제빵점",
                    color = Color.Red,
                    tasks =
                        mutableStateListOf(
                            TaskItem(
                                text = "Lo-Fi Wireframe 회의 진행중입니다. 두줄 텍스트일 경우 이렇게 표기합니다.",
                                color = Color.Red,
                                isCompleted = true,
                                startTime = LocalTime.now(),
                            ),
                            TaskItem(
                                text = "HI-Fi Wireframe 확정",
                                color = Color.Red,
                            ),
                            TaskItem(
                                text = "Lo-Fi Wireframe 확정",
                                color = Color.Red,
                                isCompleted = true,
                            ),
                        ),
                ),
                TaskCategory(
                    name = "경제학개론",
                    color = Color.Black,
                    tasks =
                        mutableStateListOf(
                            TaskItem(
                                text = "경제학 과제 제출",
                                color = Color.Black,
                                isCompleted = true,
                            ),
                            TaskItem(
                                text = "PPT 32p~36p 암기",
                                color = Color.Black,
                            ),
                        ),
                ),
            )
        }

    var draggingItemId by remember { mutableStateOf<String?>(null) }
    var draggingCategoryId by remember { mutableStateOf<String?>(null) }
    var targetCategoryId by remember { mutableStateOf<String?>(null) }
    var targetIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffsetY by remember { mutableFloatStateOf(0f) }
    val itemBounds = remember { mutableStateMapOf<String, Rect>() }

    // UI 예: 세로 스크롤 가능한 Column, 각 카테고리는 제목과 LazyColumn으로 표시
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = categories,
            key = { it.id },
        ) { category ->

            val headerId = "category_header_${category.id}"
            val idsWithHeader = listOf(headerId) + category.tasks.map { it.id }

            val allItemIds =
                remember(categories) {
                    categories.flatMap { category ->
                        listOf("category_header_${category.id}") + category.tasks.map { it.id }
                    }
                }

            val currentDraggingItemIndex =
                remember(draggingItemId, category.tasks.toList()) {
                    draggingItemId?.let { allItemIds.indexOf(it) }
                }

            val targetCategoryHeaderId = "category_header_$targetCategoryId"

            val headerAnimatedShift =
                remember(draggingItemId, targetIndex, category.tasks.toList()) {
                    calculateAnimatedShift(
                        draggingItemId = draggingItemId,
                        currentDraggingItemIndex = currentDraggingItemIndex,
                        targetIndex =
                            targetIndex?.let {
                                val indexInAll = allItemIds.indexOf(targetCategoryHeaderId) + 1 + it
                                indexInAll
                            },
                        currentItemId = headerId,
                        allVisibleItems = allItemIds,
                        itemBounds = itemBounds,
                        itemSpacing = 8f,
                    )
                }

            val headerAnimatedY =
                if (draggingItemId == null) {
                    0f
                } else {
                    animateFloatAsState(
                        targetValue = headerAnimatedShift,
                        animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic),
                        label = "animatedShiftY_header_${category.id}",
                    ).value
                }

            Box(
                modifier =
                    Modifier
                        .onGloballyPositioned { coordinates ->
                            val newBounds = coordinates.boundsInParent()
                            // 실제 변경이 있을 때만 업데이트 (불필요한 리컴포지션 방지)
                            if (itemBounds[headerId] != newBounds) {
                                itemBounds[headerId] = newBounds
                            }
                        }
                        .graphicsLayer {
                            translationY = headerAnimatedY
                        },
            ) {
                Text(text = category.name, color = category.color, modifier = Modifier.padding(8.dp))
            }

            category.tasks.forEachIndexed { index, task ->
                val isCurrentlyDraggingThisItem = task.id == draggingItemId

                // 현재 드래그 중인 아이템이 있다면, taskList에서의 실제 인덱스 (없으면 null)
                val currentDraggingItemIndex =
                    remember(draggingItemId, category.tasks.toList()) {
                        draggingItemId?.let { id -> idsWithHeader.indexOf(id) }
                    }

                val animatedShiftTarget =
                    remember(draggingItemId, targetIndex, task.id, category.tasks.toList()) {
                        calculateAnimatedShift(
                            draggingItemId = draggingItemId,
                            currentDraggingItemIndex = currentDraggingItemIndex,
                            targetIndex =
                                targetIndex?.let {
                                    val indexInAll = allItemIds.indexOf(targetCategoryHeaderId) + 1 + it
                                    indexInAll
                                },
                            currentItemId = task.id,
                            allVisibleItems = allItemIds,
                            itemBounds = itemBounds,
                            itemSpacing = 8f,
                        )
                    }
                // animatedShiftTarget 값으로 부드럽게 애니메이션되는 Y축 오프셋
                // 드래그가 끝났다면 애니메이션 없이 바로 제자리로
                val animatedShiftY =
                    if (draggingItemId == null) {
                        0f
                    } else {
                        animateFloatAsState(
                            targetValue = animatedShiftTarget,
                            animationSpec =
                                tween(
                                    durationMillis = 300,
                                    easing = EaseInOutCubic,
                                ),
                            label = "animatedShiftY_${task.id}",
                        ).value
                    }

                Box(
                    modifier =
                        Modifier
                            .onGloballyPositioned { coords ->
                                itemBounds[task.id] = coords.boundsInParent()
                            }
                            .graphicsLayer {
                                translationY = if (isCurrentlyDraggingThisItem) dragOffsetY else animatedShiftY
                                shadowElevation = if (isCurrentlyDraggingThisItem) 8.dp.toPx() else 0f
                                alpha = if (isCurrentlyDraggingThisItem) 0.95f else 1f
                            }
                            .pointerInput(task.id) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = {
                                        draggingItemId = task.id
                                        draggingCategoryId = category.id
                                        targetCategoryId = category.id
                                        targetIndex = index
                                        dragOffsetY = 0f
                                    },
                                    onDrag = { change, dragAmount ->
                                        val currentDrgId = draggingItemId
                                        if (currentDrgId == null) {
                                            change.consume()
                                            return@detectDragGesturesAfterLongPress
                                        }

                                        change.consume()
                                        dragOffsetY += dragAmount.y

                                        val draggingItemIndex = idsWithHeader.indexOf(currentDrgId)

                                        val draggingTaskBound = itemBounds[currentDrgId] ?: return@detectDragGesturesAfterLongPress
                                        val draggingTaskCenterY = draggingTaskBound.center.y

                                        var newTargetIndexFound = draggingItemIndex
                                        var minDistanceToCenter = Float.MAX_VALUE

                                        categories.forEachIndexed { categoryIndex, category ->
                                            val headerId = "category_header_${category.id}"
                                            val headerRect = itemBounds[headerId]
                                            if (headerRect != null) {
                                                if (draggingTaskCenterY < headerRect.center.y) {
                                                    if (categoryIndex > 0) {
                                                        val previousCategory = categories[categoryIndex - 1]
                                                        targetCategoryId = previousCategory.id
                                                        targetIndex = previousCategory.tasks.size
                                                    } else {
                                                        targetCategoryId = category.id
                                                        targetIndex = 0.coerceAtMost(category.tasks.size)
                                                    }
                                                    return@forEachIndexed
                                                }
                                            }

                                            category.tasks.forEachIndexed { index, task ->
                                                val taskRect = itemBounds[task.id] ?: return@forEachIndexed
                                                val isOverlapping = draggingTaskCenterY > taskRect.top && draggingTaskCenterY < taskRect.bottom
                                                val distance = abs(draggingTaskCenterY - taskRect.center.y)

                                                if (isOverlapping) {
                                                    if (distance < minDistanceToCenter) {
                                                        minDistanceToCenter = distance
                                                        targetCategoryId = category.id
                                                        newTargetIndexFound = index
                                                    }
                                                } else {
                                                    if (draggingItemIndex < index) {
                                                        if (draggingTaskCenterY > taskRect.center.y && distance < minDistanceToCenter) {
                                                            minDistanceToCenter = distance
                                                            targetCategoryId = category.id
                                                            newTargetIndexFound = index
                                                        }
                                                    } else if (draggingItemIndex > index) {
                                                        if (draggingTaskCenterY < taskRect.center.y && distance < minDistanceToCenter) {
                                                            minDistanceToCenter = distance
                                                            targetCategoryId = category.id
                                                            newTargetIndexFound = index
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    onDragEnd = {
                                        // 이동 처리: 원래 카테고리에서 제거 후 대상 카테고리에 삽입
                                        if (draggingItemId != null && draggingCategoryId != null && targetCategoryId != null && targetIndex != null) {
                                            val fromCategory = categories.find { it.id == draggingCategoryId }
                                            val toCategory = categories.find { it.id == targetCategoryId }
                                            if (fromCategory != null && toCategory != null) {
                                                val movedItemIndex = fromCategory.tasks.indexOfFirst { it.id == draggingItemId }
                                                if (movedItemIndex != -1) {
                                                    val movedItem = fromCategory.tasks.removeAt(movedItemIndex)
                                                    toCategory.tasks.add(targetIndex!!, movedItem)
                                                }
                                            }
                                        }
                                        draggingItemId = null
                                        draggingCategoryId = null
                                        targetCategoryId = null
                                        targetIndex = null
                                        dragOffsetY = 0f
                                    },
                                    onDragCancel = {
                                        draggingItemId = null
                                        draggingCategoryId = null
                                        targetCategoryId = null
                                        targetIndex = null
                                        dragOffsetY = 0f
                                    },
                                )
                            },
                ) {
                    BbangZipTaskBox(
                        task = task.text,
                        categoryColor = task.color,
                        isCompleted = task.isCompleted,
                        startTime = task.startTime,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

private fun calculateAnimatedShift(
    draggingItemId: String?,
    currentDraggingItemIndex: Int?,
    targetIndex: Int?,
    currentItemId: String,
    allVisibleItems: List<String>,
    itemBounds: Map<String, Rect>,
    itemSpacing: Float,
): Float {
    // 드래그 중이 아니면 오프셋 없음
    if (draggingItemId == null || currentDraggingItemIndex == null || targetIndex == null) {
        return 0f
    }

    // 드래그 중인 아이템 자신은 이 애니메이션의 대상이 아님
    if (currentItemId == draggingItemId) {
        return 0f
    }

    val currentItemAbsoluteIndex = allVisibleItems.indexOf(currentItemId)
    if (currentItemAbsoluteIndex == -1) return 0f

    val draggingItemHeight = itemBounds[draggingItemId]?.height ?: return 0f
    val shiftAmount = draggingItemHeight + itemSpacing

    return when {
        currentDraggingItemIndex < targetIndex &&
            currentItemAbsoluteIndex in minOf(currentDraggingItemIndex, targetIndex) + 1..maxOf(currentDraggingItemIndex, targetIndex) -> -shiftAmount

        currentDraggingItemIndex > targetIndex &&
            currentItemAbsoluteIndex in targetIndex until currentDraggingItemIndex -> shiftAmount

        else -> 0f
    }
}

@Preview(showBackground = true)
@Composable
fun BbangzipDraggableListExamplePreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            BbangzipDraggableListExample()
        }
    }
}
