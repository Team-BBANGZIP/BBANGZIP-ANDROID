package org.android.bbangzip.presentation.ui.todo

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.calendar.BbangZipWeeklyCalendar
import org.android.bbangzip.presentation.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.component.taskbox.BbangZipTaskBox
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.Todo
import org.android.bbangzip.presentation.ui.todo.model.ListItem
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import timber.log.Timber
import java.time.LocalTime

@Composable
fun colorMapper(color: String) = when(color) {
    "BbangZipTheme.color.todoRed1_EA7152" -> BbangZipTheme.color.todoRed1_EA7152
    "BbangZipTheme.color.todoRed2_F09C86" -> BbangZipTheme.color.todoRed2_F09C86
    "BbangZipTheme.color.todoYellow1_FED45C" -> BbangZipTheme.color.todoYellow1_FED45C
    "BbangZipTheme.color.todoYellow2_F6DDAF" -> BbangZipTheme.color.todoYellow2_F6DDAF
    "BbangZipTheme.color.todoGreen1_7A946D" -> BbangZipTheme.color.todoGreen1_7A946D
    "BbangZipTheme.color.todoGreen2_A2B499" -> BbangZipTheme.color.todoGreen2_A2B499
    "BbangZipTheme.color.todoBlue1_5C62AC" -> BbangZipTheme.color.todoBlue1_5C62AC
    "BbangZipTheme.color.todoBlue2_8D91C5" -> BbangZipTheme.color.todoBlue2_8D91C5
    "BbangZipTheme.color.todoPurple1_8F63E9" -> BbangZipTheme.color.todoPurple1_8F63E9
    "BbangZipTheme.color.todoPurple2_B79FE8" -> BbangZipTheme.color.todoPurple2_B79FE8
    else -> BbangZipTheme.color.todoRed1_EA7152
}
private const val LIST_HEADER_COUNT = 1
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    motivationMessage: String,
    totalTodoCount: Int,
    completedTodoCount: Int,
    onListChanged: (List<Category>) -> Unit = {},
    onTodoCheckedChanged: (todoId: Int, categoryId: Int, isChecked: Boolean) -> Unit = { _, _, _ -> },
) {
    val flatList =
        remember(categories) {
            categories.flatMap { category ->
                val categoryItem = ListItem.CategoryItem(category)
                val todoItems =
                    category.todos.mapIndexed { index, todo ->
                        ListItem.TodoItem(
                            todo = todo,
                            category = category,
                            isLastInCategory = index == category.todos.size - 1,
                        )
                    }
                listOf(categoryItem) + todoItems
            }.toMutableStateList()
        }

    val localDensity = LocalDensity.current
    var draggingItemId by remember { mutableStateOf<String?>(null) }
    var targetIndex by remember { mutableStateOf<Int?>(null) }
    val itemBounds = remember { mutableStateMapOf<String, Rect>() }
    val itemSpacingPx = with(localDensity) { 4.dp.toPx() }

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var autoScrollJob by remember { mutableStateOf<Job?>(null) }
    var columnHeight by remember { mutableIntStateOf(0) }

    var draggedItem by remember { mutableStateOf<ListItem.TodoItem?>(null) }
    var ghostOffset by remember { mutableStateOf(Offset.Zero) }
    var initialDragTouchPoint by remember { mutableStateOf(Offset.Zero) }

    val listHeaderCount = 1

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
                .systemBarsPadding(),
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

                            if (longPress != null) {
                                val pressedIndex =
                                    lazyListState.layoutInfo.visibleItemsInfo
                                        .firstOrNull {
                                            val itemTopY = it.offset
                                            val itemBottomY = it.offset + it.size
                                            down.position.y >= itemTopY && down.position.y <= itemBottomY
                                        }
                                        ?.index ?: return@awaitEachGesture

                                if (pressedIndex < listHeaderCount) return@awaitEachGesture

                                val pressedFlatListIndex = pressedIndex - listHeaderCount

                                val pressedItem = flatList.getOrNull(pressedFlatListIndex)
                                if (pressedItem !is ListItem.TodoItem) return@awaitEachGesture

                                val pressedVisibleItem =
                                    lazyListState.layoutInfo.visibleItemsInfo.first { it.index == pressedIndex }
                                val itemRectInViewport = pressedVisibleItem.offset

                                draggingItemId = pressedItem.id
                                targetIndex = pressedIndex
                                draggedItem = pressedItem

                                initialDragTouchPoint =
                                    down.position - Offset(0f, itemRectInViewport.toFloat())
                                ghostOffset = down.position - initialDragTouchPoint

                                val newTargetIndex =
                                    updateTargetIndex(
                                        lazyListState = lazyListState,
                                        flatList = flatList,
                                        ghostOffset = ghostOffset,
                                        initialDragTouchPoint = initialDragTouchPoint,
                                        draggingItemId = draggingItemId,
                                        currentTargetIndex = targetIndex,
                                        listHeaderCount = listHeaderCount,
                                    )

                                if (newTargetIndex != targetIndex) {
                                    targetIndex = newTargetIndex
                                }

                                drag(pointerId = longPress.id) { change ->
                                    change.consume()
                                    ghostOffset +=
                                        Offset(
                                            x = change.position.x - change.previousPosition.x,
                                            y = change.position.y - change.previousPosition.y,
                                        )

                                    val newTargetIndex =
                                        updateTargetIndex(
                                            lazyListState = lazyListState,
                                            flatList = flatList,
                                            ghostOffset = ghostOffset,
                                            initialDragTouchPoint = initialDragTouchPoint,
                                            draggingItemId = draggingItemId,
                                            currentTargetIndex = targetIndex,
                                            listHeaderCount = listHeaderCount,
                                        )

                                    if (newTargetIndex != targetIndex) {
                                        targetIndex = newTargetIndex
                                    }

                                    val scrollThreshold = with(localDensity) { 50.dp.toPx() }

                                    val minSpeed = 5f
                                    val maxSpeed = 30f

                                    if (ghostOffset.y < scrollThreshold) {
                                        if (autoScrollJob?.isActive != true) {
                                            autoScrollJob?.cancel()
                                            autoScrollJob =
                                                coroutineScope.launch {
                                                    while (isActive) {
                                                        val currentPointerY =
                                                            ghostOffset.y + initialDragTouchPoint.y
                                                        val intensity =
                                                            ((scrollThreshold - currentPointerY) / scrollThreshold).coerceIn(
                                                                0f,
                                                                1f,
                                                            )
                                                        val speed =
                                                            -(minSpeed + (maxSpeed - minSpeed) * intensity)

                                                        val newTargetIndexInScroll =
                                                            updateTargetIndex(
                                                                lazyListState = lazyListState,
                                                                flatList = flatList,
                                                                ghostOffset = ghostOffset,
                                                                initialDragTouchPoint = initialDragTouchPoint,
                                                                draggingItemId = draggingItemId,
                                                                currentTargetIndex = targetIndex,
                                                                listHeaderCount = listHeaderCount,
                                                            )
                                                        if (newTargetIndexInScroll != targetIndex) {
                                                            targetIndex = newTargetIndexInScroll
                                                        }

                                                        Timber.d("speed: $speed")
                                                        lazyListState.scrollBy(speed)
                                                        delay(8)
                                                    }
                                                }
                                        }
                                    } else if (ghostOffset.y + initialDragTouchPoint.y > columnHeight - scrollThreshold) {
                                        if (autoScrollJob?.isActive != true) {
                                            autoScrollJob?.cancel()
                                            autoScrollJob =
                                                coroutineScope.launch {
                                                    while (isActive) {
                                                        val currentPointerY =
                                                            ghostOffset.y + initialDragTouchPoint.y
                                                        val intensity =
                                                            ((currentPointerY - (columnHeight - scrollThreshold)) / scrollThreshold).coerceIn(
                                                                0f,
                                                                1f,
                                                            )
                                                        val speed =
                                                            minSpeed + (maxSpeed - minSpeed) * intensity

                                                        val newTargetIndexInScroll =
                                                            updateTargetIndex(
                                                                lazyListState = lazyListState,
                                                                flatList = flatList,
                                                                ghostOffset = ghostOffset,
                                                                initialDragTouchPoint = initialDragTouchPoint,
                                                                draggingItemId = draggingItemId,
                                                                currentTargetIndex = targetIndex,
                                                                listHeaderCount = listHeaderCount,
                                                            )
                                                        if (newTargetIndexInScroll != targetIndex) {
                                                            targetIndex = newTargetIndexInScroll
                                                        }

                                                        lazyListState.scrollBy(speed)
                                                        delay(8)
                                                    }
                                                }
                                        }
                                    } else {
                                        autoScrollJob?.cancel()
                                    }
                                }

                                autoScrollJob?.cancel()
                                val finalTargetIndex = targetIndex
                                if (finalTargetIndex != null) {
                                    val currentIdx =
                                        flatList.indexOfFirst { it.id == draggingItemId }
                                    if (currentIdx != -1 && currentIdx != finalTargetIndex && finalTargetIndex in flatList.indices) {
                                        val movedItem = flatList.removeAt(currentIdx)
                                        flatList.add(finalTargetIndex, movedItem)
                                        synchronizeListState(flatList)
                                        val newCategories =
                                            reconstructCategoriesFromFlatList(flatList)
                                        onListChanged(newCategories)
                                        Timber.d("TodoScreen $newCategories")
                                    }
                                }
                                draggingItemId = null
                                targetIndex = null
                                draggedItem = null
                            }
                        }
                    },
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item {
                MotivationMessageBox(
                    motivationMessage = motivationMessage,
                )

                BbangZipWeeklyCalendar(
                    modifier = Modifier,
                )

                Gap(height = 20.dp)

                CompleteTodoCounter(
                    completedTodoCount = completedTodoCount,
                    totalTodoCount = totalTodoCount,
                )
            }
            itemsIndexed(
                items = flatList,
                key = { _, item -> item.id },
            ) { index, item ->
                when (item) {
                    is ListItem.CategoryItem -> {
                        val currentDraggingItemIndex =
                            remember(draggingItemId, flatList.toList()) {
                                draggingItemId?.let { id -> flatList.indexOfFirst { it.id == id } }
                            }

                        val animatedShiftTarget =
                            remember(draggingItemId, targetIndex, item.id, flatList.toList()) {
                                calculateAnimatedShift(
                                    draggingItemId = draggingItemId,
                                    currentDraggingItemIndex = currentDraggingItemIndex,
                                    targetIndex = targetIndex,
                                    currentItemId = item.id,
                                    taskList = flatList.map { it.id },
                                    itemBounds = itemBounds,
                                    itemSpacing = itemSpacingPx,
                                )
                            }

                        val animatedShiftY =
                            if (draggingItemId == null) {
                                0f
                            } else {
                                animateFloatAsState(
                                    targetValue = animatedShiftTarget,
                                    animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic),
                                    label = "animatedShiftY_${item.id}",
                                ).value
                            }

                        Column(
                            modifier =
                                Modifier
                                    .padding(horizontal = 20.dp)
                                    .onGloballyPositioned { coordinates ->
                                        val newBound = coordinates.boundsInParent()
                                        if (itemBounds[item.id] != newBound) {
                                            itemBounds[item.id] = newBound
                                        }
                                    }
                                    .graphicsLayer {
                                        translationY = animatedShiftY
                                    },
                        ) {
                            Gap(
                                height = if (index == 0) 4.dp else 16.dp,
                            )

                            BbangZipCategoryChip(
                                categoryColor = colorMapper(item.category.categoryColor),
                                categoryName = item.category.categoryName,
                            )
                        }
                    }

                    is ListItem.TodoItem -> {
                        val isDragging = item.id == draggingItemId
                        val currentDraggingItemIndex =
                            remember(draggingItemId, flatList.toList()) {
                                draggingItemId?.let { id -> flatList.indexOfFirst { it.id == id } }
                            }

                        val animatedShiftTarget =
                            remember(draggingItemId, targetIndex, item.id, flatList.toList()) {
                                calculateAnimatedShift(
                                    draggingItemId = draggingItemId,
                                    currentDraggingItemIndex = currentDraggingItemIndex,
                                    targetIndex = targetIndex,
                                    currentItemId = item.id,
                                    taskList = flatList.map { it.id },
                                    itemBounds = itemBounds,
                                    itemSpacing = itemSpacingPx,
                                )
                            }

                        val animatedShiftY =
                            if (draggingItemId == null) {
                                0f
                            } else {
                                animateFloatAsState(
                                    targetValue = animatedShiftTarget,
                                    animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic),
                                    label = "animatedShiftY_${item.id}",
                                ).value
                            }

                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .onGloballyPositioned { coordinates ->
                                        val newBound = coordinates.boundsInParent()
                                        if (itemBounds[item.id] != newBound) {
                                            itemBounds[item.id] = newBound
                                        }
                                    }
                                    .graphicsLayer {
                                        translationY = animatedShiftY
                                        alpha = if (isDragging) 0f else 1f
                                    },
                        ) {
                            BbangZipTaskBox(
                                task = item.todo.content,
                                isCompleted = item.todo.isCompleted,
                                onCheckBoxClick = { isChecked ->
                                    onTodoCheckedChanged(item.todo.todoId, item.category.categoryId, isChecked)
                                },
                                isLast = item.isLastInCategory,
                                startTime = item.todo.startTime,
                                categoryColor = colorMapper(item.category.categoryColor),
                            )
                        }
                    }
                }
            }
        }

        draggedItem?.let { item ->
            val itemRect = itemBounds[item.id]
            Box(
                modifier =
                    Modifier
                        .offset(
                            x = with(localDensity) { ghostOffset.x.toDp() + 20.dp },
                            y = with(localDensity) { ghostOffset.y.toDp() },
                        )
                        .width(with(localDensity) { itemRect?.width?.toDp() } ?: Dp.Unspecified)
                        .background(BbangZipTheme.color.componentStrong_F6F6F5),
            ) {
                BbangZipTaskBox(
                    task = item.todo.content,
                    isCompleted = item.todo.isCompleted,
                    onCheckBoxClick = {},
                    isLast = item.isLastInCategory,
                    startTime = item.todo.startTime,
                    categoryColor = colorMapper(item.category.categoryColor),
                )
            }
        }
    }
}

@Composable
private fun CompleteTodoCounter(
    completedTodoCount: Int,
    totalTodoCount: Int,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_bread_default_14),
                contentDescription = null,
                tint = BbangZipTheme.color.labelAlternative_A29D96,
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_default_24),
                contentDescription = null,
                tint = BbangZipTheme.color.componentIvory_FDFDFD,
                modifier = Modifier.size(14.dp),
            )
        }

        Gap(width = 6.dp)

        Text(
            text = "$completedTodoCount / $totalTodoCount",
            style = BbangZipTheme.typography.body4Medium,
            color = BbangZipTheme.color.labelAlternative_A29D96,
        )
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
    if (draggingItemId == null || currentDraggingItemIndex == null || targetIndex == null) {
        return 0f
    }
    if (currentItemId == draggingItemId) {
        return 0f
    }

    val currentItemActualIndex = taskList.indexOf(currentItemId)
    val draggingItemHeight = itemBounds[draggingItemId]?.height ?: 0f

    val shiftAmount = draggingItemHeight + itemSpacing

    return when {
        currentDraggingItemIndex < targetIndex && currentItemActualIndex in (currentDraggingItemIndex + 1)..targetIndex -> -shiftAmount
        currentDraggingItemIndex > targetIndex && currentItemActualIndex in targetIndex until currentDraggingItemIndex -> shiftAmount
        else -> 0f
    }
}

private fun synchronizeListState(
    list: MutableList<ListItem>,
) {
    var currentCategory: ListItem.CategoryItem? = null

    for (i in list.indices) {
        when (val item = list[i]) {
            is ListItem.CategoryItem -> {
                currentCategory = item
            }
            is ListItem.TodoItem -> {
                currentCategory?.let { catItem ->
                    val correctCategory = catItem.category
                    var needsUpdate = false
                    var updatedItem = item

                    if (item.category.categoryId != correctCategory.categoryId) {
                        updatedItem = updatedItem.copy(category = correctCategory)
                        needsUpdate = true
                    }

                    val isNowLast = (i == list.size - 1) || (list[i + 1] is ListItem.CategoryItem)
                    if (item.isLastInCategory != isNowLast) {
                        updatedItem = updatedItem.copy(isLastInCategory = isNowLast)
                        needsUpdate = true
                    }

                    if (needsUpdate) {
                        list[i] = updatedItem
                    }
                }
            }
        }
    }
}

private fun reconstructCategoriesFromFlatList(flatList: List<ListItem>): List<Category> {
    val newCategories = mutableListOf<Category>()
    var currentTodos = mutableListOf<Todo>()
    var currentCategory: Category? = null

    flatList.forEach { item ->
        when (item) {
            is ListItem.CategoryItem -> {
                currentCategory?.let {
                    newCategories.add(it.copy(todos = currentTodos.toList()))
                }
                currentCategory = item.category
                currentTodos = mutableListOf()
            }
            is ListItem.TodoItem -> {
                currentTodos.add(item.todo)
            }
        }
    }

    currentCategory?.let {
        newCategories.add(it.copy(todos = currentTodos.toList()))
    }

    return newCategories
}

private fun updateTargetIndex(
    lazyListState: LazyListState,
    flatList: List<ListItem>,
    ghostOffset: Offset,
    initialDragTouchPoint: Offset,
    draggingItemId: String?,
    currentTargetIndex: Int?,
    listHeaderCount: Int,
): Int {
    val ghostCenterY = ghostOffset.y + initialDragTouchPoint.y
    var newTargetIndex = currentTargetIndex ?: flatList.indexOfFirst { it.id == draggingItemId }

    lazyListState.layoutInfo.visibleItemsInfo
        .firstOrNull { visibleItem ->
            val itemTopY = visibleItem.offset
            val itemBottomY = itemTopY + visibleItem.size
            ghostCenterY >= itemTopY && ghostCenterY <= itemBottomY
        }
        ?.let {
            val flatListIndex = it.index - listHeaderCount
            if (flatListIndex >= 0) {
                newTargetIndex = flatListIndex
            }
        }

    val firstDraggableItemInfo = lazyListState.layoutInfo.visibleItemsInfo.find { it.index > listHeaderCount }
    if (firstDraggableItemInfo != null && ghostCenterY < firstDraggableItemInfo.offset) {
        newTargetIndex = 0
    }

    if (newTargetIndex == 0 && flatList.getOrNull(0) is ListItem.CategoryItem) {
        return 1
    }

    return newTargetIndex
}

@Composable
fun MotivationMessageBox(
    motivationMessage: String,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(BbangZipTheme.color.secondaryStrong_F2EAE4)
                .clipToBounds(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .align(Alignment.CenterStart),
        ) {
            Text(
                text = motivationMessage,
                color = BbangZipTheme.color.labelNormal_6B6560,
                style = BbangZipTheme.typography.body4Medium,
            )
        }
        Image(
            painter = painterResource(R.drawable.img_smile_bread),
            contentDescription = "smile_bread",
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 19.dp)
                    .offset(y = 13.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListPreview() {
    BBANGZIPANDROIDTheme {
        val exampleCategories =
            listOf(
                Category(
                    categoryId = 1,
                    categoryName = "제과제빵점",
                    categoryColor = "Red",
                    todos =
                        listOf(
                            Todo(
                                todoId = 11,
                                content = "두줄 \n 두줄",
                                isCompleted = true,
                                startTime = LocalTime.of(11, 0),
                            ),
                            Todo(
                                todoId = 12,
                                content = "제과제빵점_한줄_실패",
                                isCompleted = false,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 13,
                                content = "제과제빵점_한줄_완료",
                                isCompleted = true,
                                startTime = null,
                            ),
                        ),
                ),
                Category(
                    categoryId = 2,
                    categoryName = "경제학개론",
                    categoryColor = "Blue",
                    todos =
                        listOf(
                            Todo(
                                todoId = 21,
                                content = "경제학개론_한줄_완료",
                                isCompleted = true,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 22,
                                content = "경제학개론 \n 두줄_실패",
                                isCompleted = false,
                                startTime = LocalTime.of(11, 0),
                            ),
                        ),
                ),
                Category(
                    categoryId = 3,
                    categoryName = "운동",
                    categoryColor = "Green",
                    todos =
                        listOf(
                            Todo(
                                todoId = 31,
                                content = "헬스장 가기",
                                isCompleted = false,
                                startTime = LocalTime.of(18, 0),
                            ),
                            Todo(
                                todoId = 32,
                                content = "저녁 유산소 30분",
                                isCompleted = true,
                                startTime = LocalTime.of(19, 30),
                            ),
                        ),
                ),
                Category(
                    categoryId = 4,
                    categoryName = "스터디",
                    categoryColor = "Yellow",
                    todos =
                        listOf(
                            Todo(
                                todoId = 41,
                                content = "알고리즘 문제 풀이",
                                isCompleted = true,
                                startTime = LocalTime.of(20, 0),
                            ),
                            Todo(
                                todoId = 42,
                                content = "코틀린 스터디 준비",
                                isCompleted = false,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 43,
                                content = "CS 스터디 복습",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 0),
                            ),
                        ),
                ),
                Category(
                    categoryId = 5,
                    categoryName = "개인 프로젝트",
                    categoryColor = "Red",
                    todos =
                        listOf(
                            Todo(
                                todoId = 51,
                                content = "UI 디자인 검토",
                                isCompleted = false,
                                startTime = null,
                            ),
                            Todo(
                                todoId = 52,
                                content = "백엔드 API 연동",
                                isCompleted = false,
                                startTime = LocalTime.of(14, 0),
                            ),
                        ),
                ),
                Category(
                    categoryId = 6,
                    categoryName = "새로운 카테고리",
                    categoryColor = "Blue",
                    todos =
                        listOf(
                            Todo(
                                todoId = 61,
                                content = "새로운 할 일 1",
                                isCompleted = false,
                                startTime = LocalTime.of(9, 0),
                            ),
                            Todo(
                                todoId = 62,
                                content = "새로운 할 일 2",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 30),
                            ),
                        ),
                ),
                Category(
                    categoryId = 7,
                    categoryName = "영화",
                    categoryColor = "Red",
                    todos =
                        listOf(
                            Todo(
                                todoId = 71,
                                content = "귀멸의 칼날",
                                isCompleted = false,
                                startTime = LocalTime.of(9, 0),
                            ),
                            Todo(
                                todoId = 72,
                                content = "좀비딸",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 30),
                            ),
                            Todo(
                                todoId = 73,
                                content = "F1",
                                isCompleted = true,
                                startTime = LocalTime.of(10, 30),
                            ),
                        ),
                ),
            )
        var todos by remember { mutableStateOf(exampleCategories) }
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
        ) {
            TodoScreen(
                categories = todos,
                motivationMessage = "나만의 다짐을 적어보세요",
                totalTodoCount = todos.sumOf { it.todos.size },
                completedTodoCount = todos.flatMap { it.todos }.count { it.isCompleted },
                onListChanged = { newCategories ->
                    todos = newCategories
                },
                onTodoCheckedChanged = { todoId, categoryId, isCompleted ->
                    todos =
                        todos.map { category ->
                            if (category.categoryId == categoryId) {
                                category.copy(
                                    todos =
                                        category.todos.map { todo ->
                                            if (todo.todoId == todoId) {
                                                todo.copy(isCompleted = isCompleted)
                                            } else {
                                                todo
                                            }
                                        },
                                )
                            } else {
                                category
                            }
                        }
                },
            )
        }
        Timber.d("TodoListPreview $todos")
    }
}
