package org.android.bbangzip.presentation.ui.todo

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.android.bbangzip.presentation.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.component.taskbox.BbangZipTaskBox
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import timber.log.Timber
import java.time.LocalTime
import kotlin.math.abs

val colorMapper =
    mapOf(
        "Red" to Color.Red,
        "Blue" to Color.Blue,
        "Green" to Color.Green,
        "Yellow" to Color.Yellow,
    )
val exampleList =
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
                mutableListOf(
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
                mutableListOf(
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

data class Category(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
    val todos: List<Todo>,
)

data class Todo(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: LocalTime? = null,
)

sealed interface ListItem {
    val id: String

    data class CategoryItem(
        val category: Category,
    ) : ListItem {
        override val id: String = "category_${category.categoryId}"
    }

    data class TodoItem(
        val todo: Todo,
        val category: Category,
        val isLastInCategory: Boolean,
    ) : ListItem {
        override val id: String = "todo_${todo.todoId}"
    }
}

@Composable
fun TodoList(
    categories: List<Category>,
    onListChanged: (List<Category>) -> Unit = {},
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
    var dragOffsetY by remember { mutableFloatStateOf(0f) }
    var targetIndex by remember { mutableStateOf<Int?>(null) }
    val itemBounds = remember { mutableStateMapOf<String, Rect>() }
    val itemSpacingPx = with(localDensity) { 4.dp.toPx() }

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var autoScrollJob by remember { mutableStateOf<Job?>(null) }
    var columnHeight by remember { mutableIntStateOf(0) }


    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .onGloballyPositioned { coordinates ->
                    columnHeight = coordinates.size.height
                }
        .pointerInput(Unit) {
            awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val longPress = awaitLongPressOrCancellation(down.id)

                    if (longPress != null) {
                        var dragStarted = false
                        val pressedIndex = lazyListState.layoutInfo.visibleItemsInfo
                            .firstOrNull {
                                val itemTopY = it.offset
                                val itemBottomY = it.offset + it.size
                                down.position.y >= itemTopY && down.position.y <= itemBottomY
                            }
                            ?.index ?: return@awaitEachGesture

                        val pressedItem = flatList.getOrNull(pressedIndex)
                        if (pressedItem is ListItem.TodoItem) {
                            dragStarted = true
                            draggingItemId = pressedItem.id
                            targetIndex = pressedIndex
                            dragOffsetY = 0f
                        }

                        if (dragStarted) {
                            drag(longPress.id) { change ->
                                change.consume()
                                dragOffsetY += change.position.y - change.previousPosition.y

                                val currentDraggingItemId = draggingItemId!!
                                val draggingItemBounds = itemBounds[currentDraggingItemId]!!
                                val draggingItemCenterY = draggingItemBounds.center.y + dragOffsetY
                                var newTargetIndex = flatList.indexOfFirst { it.id == currentDraggingItemId }
                                var minDistance = Float.MAX_VALUE
                                flatList.forEachIndexed { i, listItem ->
                                    itemBounds[listItem.id]?.let {
                                        val distance = abs(draggingItemCenterY - it.center.y)
                                        if (draggingItemCenterY > it.top && draggingItemCenterY < it.bottom) {
                                            if (distance < minDistance) {
                                                minDistance = distance
                                                newTargetIndex = i
                                            }
                                        }
                                    }
                                }
                                if (newTargetIndex != targetIndex) {
                                    targetIndex = newTargetIndex
                                }

                                val draggingItemHeight = draggingItemBounds.height
                                val draggingItemCurrentTop = draggingItemBounds.top + dragOffsetY
                                val draggingItemCurrentBottom = draggingItemCurrentTop + draggingItemHeight
                                val scrollThreshold = with(localDensity) { 80.dp.toPx() }

                                if (draggingItemCurrentTop < scrollThreshold) {
                                    if (autoScrollJob?.isActive != true) {
                                        autoScrollJob?.cancel()
                                        autoScrollJob = coroutineScope.launch {
                                            while (isActive) {
                                                val scrolled = lazyListState.scrollBy(-30f)
                                                dragOffsetY += scrolled
                                                delay(16)
                                            }
                                        }
                                    }
                                } else if (draggingItemCurrentBottom > columnHeight - scrollThreshold) {
                                    if (autoScrollJob?.isActive != true) {
                                        autoScrollJob?.cancel()
                                        autoScrollJob = coroutineScope.launch {
                                            while (isActive) {
                                                val scrolled = lazyListState.scrollBy(30f)
                                                dragOffsetY += scrolled
                                                delay(16)
                                            }
                                        }
                                    }
                                } else {
                                    autoScrollJob?.cancel()
                                }
                            }

                            autoScrollJob?.cancel()
                            val finalDraggingItemId = draggingItemId
                            val finalTargetIndex = targetIndex
                            if (finalDraggingItemId != null && finalTargetIndex != null) {
                                val currentIdx = flatList.indexOfFirst { it.id == finalDraggingItemId }
                                if (currentIdx != -1 && currentIdx != finalTargetIndex && finalTargetIndex in flatList.indices) {
                                    val movedItem = flatList.removeAt(currentIdx)
                                    flatList.add(finalTargetIndex, movedItem)
                                    synchronizeListState(flatList)
                                    val newCategories = reconstructCategoriesFromFlatList(flatList)
                                    onListChanged(newCategories)
                                }
                            }
                            draggingItemId = null
                            targetIndex = null
                            dragOffsetY = 0f
                        }
                    }
            }
        },
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
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
                        Gap(height = 20.dp)
                        BbangZipCategoryChip(
                            categoryColor = colorMapper.getValue(item.category.categoryColor),
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
                                .onGloballyPositioned { coordinates ->
                                    val newBound = coordinates.boundsInParent()
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
                    ) {
                        BbangZipTaskBox(
                            task = item.todo.content,
                            isCompleted = item.todo.isCompleted,
                            isLast = item.isLastInCategory,
                            startTime = item.todo.startTime,
                            categoryColor = colorMapper.getValue(item.category.categoryColor),
                        )
                    }
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

@Preview(showBackground = true)
@Composable
fun TodoListPreview() {
    BBANGZIPANDROIDTheme {
        var todos by remember { mutableStateOf(exampleList) }
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
        ) {
            TodoList(
                categories = todos,
                onListChanged = { newCategories ->
                    todos = newCategories
                },
            )
        }
        Timber.d("TodoListPreview $todos")
    }
}
