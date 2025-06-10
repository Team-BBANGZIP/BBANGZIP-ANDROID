package org.android.bbangzip.presentation.component.taskbox

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import java.time.LocalTime
import java.util.UUID
import kotlin.math.abs

data class TaskItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val color: Color,
    val isCompleted: Boolean = false,
    val startTime: LocalTime? = null,
)

data class TaskCategory(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val color: Color,
    val tasks: SnapshotStateList<TaskItem> = mutableStateListOf(),
)

@Composable
fun BbangzipDraggableList(
    taskList: SnapshotStateList<TaskItem>,
    modifier: Modifier = Modifier,
) {
    // 현재 드래그 중인 아이템의 ID. 드래그 중이 아니면 null
    var draggingItemId by remember { mutableStateOf<String?>(null) }
    // 드래그 중인 아이템의 Y축 이동량
    var dragOffsetY by remember { mutableFloatStateOf(0f) }
    // 드래그 중인 아이템이 드롭될 예상 위치(taskList에서의 인덱스)
    var targetIndex by remember { mutableStateOf<Int?>(null) }

    // 각 아이템의 화면상 경계(Rect) 정보를 저장하는 맵. 키는 아이템의 ID입니다.
    // 두 줄 이상의 텍스트가 존재하므로 각 아이템의 정확한 위치와 크기를 파악하는 데 사용
    val itemBounds = remember { mutableStateMapOf<String, Rect>() }

    // LazyColumn에서 아이템 간의 간격(Px)
    val itemSpacingPx = with(LocalDensity.current) { 4.dp.toPx() }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        itemsIndexed(
            items = taskList,
            key = { _, item -> item.id },
        ) { index, item ->
            // 현재 아이템(item)이 드래그 중인 아이템(draggingItemId)인지 여부를 판단
            val isDragging = item.id == draggingItemId

            // 현재 드래그 중인 아이템이 있다면, taskList에서의 실제 인덱스 (없으면 null)
            val currentDraggingItemIndex =
                remember(draggingItemId, taskList.toList()) {
                    draggingItemId?.let { id -> taskList.indexOfFirst { it.id == id } }
                }

            // 드래그 발생 시, 드래그되지 않는 다른 아이템들이 밀려나는 애니메이션을 위한 Y축 오프셋 목표값을 계산
            // draggingItemId, currentTargetIndex, item.id, taskList 내용이 변경될 때 재계산
            val animatedShiftTarget =
                remember(draggingItemId, targetIndex, item.id, taskList.toList()) {
                    calculateAnimatedShift(
                        draggingItemId = draggingItemId,
                        currentDraggingItemIndex = currentDraggingItemIndex,
                        targetIndex = targetIndex,
                        currentItemId = item.id,
                        taskList = taskList.map { it.id },
                        itemBounds = itemBounds,
                        itemSpacing = itemSpacingPx,
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
                        label = "animatedShiftY_${item.id}",
                    ).value
                }

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            val newBound = coordinates.boundsInParent()
                            // 실제 변경이 있을 때만 업데이트
                            if (itemBounds[item.id] != newBound) {
                                itemBounds[item.id] = newBound
                            }
                        }
                        .graphicsLayer {
                            translationY = if (isDragging) dragOffsetY else animatedShiftY
                            shadowElevation = if (isDragging) 8.dp.toPx() else 0f
                            alpha = if (isDragging) 0.95f else 1f
                        }
                        .zIndex(if (isDragging) 1f else 0f)
                        .pointerInput(item.id) {
                            // key를 item.id로 하여 제스처 리스너가 해당 아이템에 고정되도록 함
                            val currentItemIdForPointer = item.id

                            detectDragGesturesAfterLongPress(
                                onDragStart = { offset ->
                                    // taskList에서 현재 터치된 아이템의 실제 인덱스를 찾음
                                    val actualPressedItemIndexInList = taskList.indexOfFirst { it.id == currentItemIdForPointer }

                                    if (actualPressedItemIndexInList != -1) {
                                        // ID로 드래그 아이템 설정
                                        draggingItemId = currentItemIdForPointer
                                        // 초기 타겟은 자기 자신
                                        targetIndex = actualPressedItemIndexInList
                                        // 드래그 오프셋 초기화
                                        dragOffsetY = 0f
                                    }
                                },
                                onDragEnd = {
                                    val finalDraggingItemId = draggingItemId
                                    val finalTargetIndex = targetIndex

                                    if (finalDraggingItemId != null && finalTargetIndex != null) {
                                        val currentDraggingIdxInList = taskList.indexOfFirst { it.id == finalDraggingItemId }
                                        if (currentDraggingIdxInList != -1 &&
                                            currentDraggingIdxInList != finalTargetIndex &&
                                            finalTargetIndex in taskList.indices
                                        ) {
                                            val movedItem = taskList.removeAt(currentDraggingIdxInList)
                                            taskList.add(finalTargetIndex, movedItem)
                                        }
                                    }

                                    // 상태 초기화
                                    draggingItemId = null
                                    targetIndex = null
                                    dragOffsetY = 0f
                                },
                                onDragCancel = {
                                    // 상태 초기화
                                    draggingItemId = null
                                    targetIndex = null
                                    dragOffsetY = 0f
                                },
                                onDrag = { change, dragAmount ->
                                    val currentDraggingItemId = draggingItemId
                                    if (currentDraggingItemId == null) {
                                        change.consume()
                                        return@detectDragGesturesAfterLongPress
                                    }
                                    change.consume()
                                    dragOffsetY += dragAmount.y

                                    val currentDraggingItemIndex = taskList.indexOfFirst { it.id == currentDraggingItemId }
                                    if (currentDraggingItemIndex == -1) {
                                        draggingItemId = null
                                        targetIndex = null
                                        dragOffsetY = 0f
                                        return@detectDragGesturesAfterLongPress
                                    }

                                    val draggingItemBounds = itemBounds[currentDraggingItemId]

                                    val draggingItemCenterY = draggingItemBounds?.center?.y!! + dragOffsetY

                                    // 기본값은 현재 위치
                                    var newTargetIndex = currentDraggingItemIndex
                                    var minDistanceToCenter = Float.MAX_VALUE

                                    taskList.forEachIndexed { index, task ->
                                        val taskRect = itemBounds[task.id]
                                        if (taskRect == null) {
                                            return@forEachIndexed
                                        }

                                        // 드래그 중인 아이템의 중심이 다른 아이템의 영역 중간을 넘어섰는지 판단
                                        val isOverlapping = draggingItemCenterY > taskRect.top && draggingItemCenterY < taskRect.bottom
                                        val distance = abs(draggingItemCenterY - taskRect.center.y)

                                        if (isOverlapping) {
                                            if (distance < minDistanceToCenter) {
                                                minDistanceToCenter = distance
                                                newTargetIndex = index
                                            }
                                        } else {
                                            // 아래로 드래그 중
                                            if (currentDraggingItemIndex < index) {
                                                if (draggingItemCenterY > taskRect.center.y && distance < minDistanceToCenter) {
                                                    minDistanceToCenter = distance
                                                    newTargetIndex = index
                                                }
                                            } else if (currentDraggingItemIndex > index) {
                                                // 위로 드래그 중
                                                if (draggingItemCenterY < taskRect.center.y && distance < minDistanceToCenter) {
                                                    minDistanceToCenter = distance
                                                    newTargetIndex = index
                                                }
                                            }
                                        }
                                    }

                                    if (newTargetIndex != targetIndex && newTargetIndex in taskList.indices) {
                                        targetIndex = newTargetIndex
                                    }
                                },
                            )
                        },
            ) {
                BbangZipTaskBox(
                    task = item.text,
                    categoryColor = item.color,
                    isCompleted = item.isCompleted,
                    startTime = item.startTime,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

private fun calculateAnimatedShift(
    draggingItemId: String?,
    currentDraggingItemIndex: Int?,
    targetIndex: Int?,
    currentItemId: String,
    taskList: List<String>,
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

    val currentItemActualIndex = taskList.indexOf(currentItemId)

    // 드래그 중인 아이템의 실제 높이를 가져오거나, 없으면 기본 높이 사용
    val draggingItemHeight = itemBounds[draggingItemId]?.height
    val shiftAmount = draggingItemHeight!! + itemSpacing

    return when {
        // 드래그 아이템이 아래로 이동 중이고, 현재 아이템이 그 사이에 있다면 위로 이동
        currentDraggingItemIndex < targetIndex && currentItemActualIndex in (currentDraggingItemIndex + 1)..targetIndex -> -shiftAmount
        // 드래그 아이템이 위로 이동 중이고, 현재 아이템이 그 사이에 있다면 아래로 이동
        currentDraggingItemIndex > targetIndex && currentItemActualIndex in targetIndex until currentDraggingItemIndex -> shiftAmount
        else -> 0f
    }
}

@Preview(showBackground = true)
@Composable
fun BbangzipDraggableListPreview() {
    BBANGZIPANDROIDTheme {
        BbangzipDraggableList(
            taskList =
                remember {
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
                        TaskItem(
                            text = "경제학 과제 제출",
                            color = Color.Yellow,
                            isCompleted = true,
                        ),
                        TaskItem(
                            text = "PPT 32p~36p 암기",
                            color = Color.Yellow,
                        ),
                    )
                },
        )
    }
}
