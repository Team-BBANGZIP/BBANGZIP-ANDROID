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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.AddTodoBottomSheet
import org.android.bbangzip.presentation.common.component.bottomsheet.CommitmentBottomSheet
import org.android.bbangzip.presentation.common.component.bottomsheet.TimePickerBottomSheet
import org.android.bbangzip.presentation.common.component.calendar.BbangZipWeeklyCalendar
import org.android.bbangzip.presentation.common.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.common.component.taskbox.BbangZipTaskBox
import org.android.bbangzip.presentation.common.model.Category
import org.android.bbangzip.presentation.common.model.ListItem
import org.android.bbangzip.presentation.common.model.Todo
import org.android.bbangzip.presentation.common.type.AutoScrollDirection
import org.android.bbangzip.presentation.common.type.CategoryColor
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.dropShadow
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.presentation.common.util.scroll.calculateScrollSpeed
import org.android.bbangzip.presentation.ui.todo.bottomsheet.ChangeDateBottomSheet
import org.android.bbangzip.presentation.ui.todo.bottomsheet.EditTodoBottomSheet
import org.android.bbangzip.presentation.ui.todo.bottomsheet.TodoSettingBottomSheet
import org.android.bbangzip.presentation.ui.todo.type.TodoSettingActionType
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

private const val LIST_HEADER_COUNT = 1
private const val AUTO_SCROLL_DELAY = 8L
private val AUTO_SCROLL_THRESHOLD = 50.dp
private val ITEM_SPACING = 4.dp

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    flatList: List<ListItem>,
    totalTodoCount: Int,
    completedTodoCount: Int,
    isMenuOpen: Boolean,
    textFieldCommitmentMessage: String,
    confirmedCommitmentMessage: String,
    isCommitmentBottomSheetVisible: Boolean,
    isTimePickerBottomSheetVisible: Boolean,
    isAddTodoBottomSheetVisible: Boolean,
    isTodoSettingBottomSheetVisible: Boolean,
    isEditTodoBottomSheetVisible: Boolean,
    isMonthlyCalendarBottomSheetVisible: Boolean,
    todoText: String,
    selectedStartTime: LocalTime?,
    selectedTodoItem: ListItem.TodoItem?,
    selectedMonthlyDate: LocalDate,
    isDateSavable: Boolean,
    isSundayStart: Boolean,
    selectedDate: LocalDate,
    onMenuClick: () -> Unit,
    onListItemMove: (from: Int, to: Int) -> Unit,
    onTodoCheckBoxClick: (todoId: Int, categoryId: Int, isChecked: Boolean) -> Unit,
    onAddTodoDone: () -> Unit,
    onTimeConfirmButtonClick: (startTime: LocalTime) -> Unit,
    onTimePickerBottomSheetDismissRequest: () -> Unit,
    onAddTodoBottomSheetDismissRequest: () -> Unit,
    onTodoSettingBottomSheetDismissRequest: () -> Unit,
    onTimePickerBottomSheetShowRequest: () -> Unit,
    onTimePickerBottomSheetClearButtonClick: () -> Unit,
    onMonthlyCalendarBottomSheetDismissRequest: () -> Unit,
    onDateSaveButtonClick: () -> Unit,
    onMonthlyDateSelect: (LocalDate) -> Unit,
    onTodoTextChange: (String) -> Unit,
    onCategoryChipClick: (Category) -> Unit,
    onCommitmentAreaClick: () -> Unit,
    onCommitmentDone: () -> Unit,
    onTextFieldCommitmentMessageChange: (String) -> Unit,
    onCommitmentBottomSheetDismissRequest: () -> Unit,
    onEditTodoBottomSheetDismissRequest: () -> Unit,
    onManageCategoryClick: () -> Unit = {},
    onAddCategoryClick: () -> Unit = {},
    onDateSelect: (LocalDate) -> Unit = {},
    onTodoItemMenuClick: (ListItem.TodoItem) -> Unit = {},
    onCopyTodoClick: () -> Unit = {},
    onDeleteTodoButtonClick: () -> Unit = {},
    onModifyTodoDateClick: () -> Unit = {},
    onModifyTodoNameButtonClick: () -> Unit = {},
    onModifyTodoStartTimeClick: () -> Unit = {},
    onMoveTodoToTomorrowClick: () -> Unit = {},
    onRepeatTodoClick: () -> Unit = {},
    onEditTodoDone: () -> Unit = {},
) {
    val localDensity = LocalDensity.current
    val itemSpacingPx = with(localDensity) { ITEM_SPACING.toPx() }
    val scrollThreshold = with(localDensity) { AUTO_SCROLL_THRESHOLD.toPx() }
    var columnHeight by remember { mutableIntStateOf(0) }
    val itemBounds = remember { mutableStateMapOf<String, Rect>() }

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var autoScrollJob by remember { mutableStateOf<Job?>(null) }

    var targetIndex by remember { mutableStateOf<Int?>(null) }
    var draggingItem by remember { mutableStateOf<ListItem.TodoItem?>(null) }
    var fakeOffset by remember { mutableStateOf(Offset.Zero) }
    var touchPointInItem by remember { mutableStateOf(Offset.Zero) }
    var touchPointY by remember { mutableFloatStateOf(0f) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
                .statusBarsPadding(),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        columnHeight = coordinates.size.height
                    }
                    .pointerInput(flatList) {
                        awaitEachGesture {
                            val down = awaitFirstDown(requireUnconsumed = false)
                            val longPress = awaitLongPressOrCancellation(down.id)

                            if (longPress != null) {
                                val pressedLazyColumnItem =
                                    lazyListState.layoutInfo.visibleItemsInfo
                                        .firstOrNull {
                                            val itemTopY = it.offset
                                            val itemBottomY = it.offset + it.size
                                            down.position.y >= itemTopY && down.position.y <= itemBottomY
                                        } ?: return@awaitEachGesture
                                val pressedLazyColumnIndex = pressedLazyColumnItem.index
                                val pressedFlatListIndex = pressedLazyColumnIndex - LIST_HEADER_COUNT
                                val pressedFlatListItem = flatList.getOrNull(pressedFlatListIndex)

                                if (pressedLazyColumnIndex < LIST_HEADER_COUNT || pressedFlatListItem !is ListItem.TodoItem) return@awaitEachGesture

                                targetIndex = pressedFlatListIndex
                                draggingItem = pressedFlatListItem

                                // 아이템 자체에서 클릭한 포인트
                                fakeOffset = Offset(0f, pressedLazyColumnItem.offset.toFloat())
                                touchPointInItem = down.position - fakeOffset

                                try {
                                    drag(pointerId = longPress.id) { change ->
                                        change.consume()
                                        fakeOffset +=
                                            Offset(
                                                x = change.position.x - change.previousPosition.x,
                                                y = change.position.y - change.previousPosition.y,
                                            )

                                        targetIndex =
                                            updateTargetIndex(
                                                lazyListState = lazyListState,
                                                flatList = flatList,
                                                touchPointY = touchPointY,
                                                currentTargetIndex = targetIndex,
                                            )

                                        touchPointY = fakeOffset.y + touchPointInItem.y

                                        val scrollDirection =
                                            when {
                                                fakeOffset.y < scrollThreshold -> AutoScrollDirection.UP
                                                touchPointY > columnHeight - scrollThreshold -> AutoScrollDirection.DOWN
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
                                                                    touchPointY = touchPointY,
                                                                    columnHeight = columnHeight,
                                                                    scrollThreshold = scrollThreshold,
                                                                )

                                                            lazyListState.scrollBy(speed)

                                                            targetIndex =
                                                                updateTargetIndex(
                                                                    lazyListState = lazyListState,
                                                                    flatList = flatList,
                                                                    touchPointY = touchPointY,
                                                                    currentTargetIndex = targetIndex,
                                                                )
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
                                        if (pressedFlatListIndex != -1 && pressedFlatListIndex != it && it in flatList.indices) {
                                            onListItemMove(pressedFlatListIndex, it)
                                        }
                                    }

                                    draggingItem = null
                                    targetIndex = null
                                }
                            }
                        }
                    },
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(ITEM_SPACING),
        ) {
            item {
                ListHeader(
                    commitmentMessage = confirmedCommitmentMessage,
                    isMenuOpen = isMenuOpen,
                    onMenuClick = onMenuClick,
                    onCommitmentAreaClick = onCommitmentAreaClick,
                    completedTodoCount = completedTodoCount,
                    totalTodoCount = totalTodoCount,
                    onManageCategoryClick = onManageCategoryClick,
                    onAddCategoryClick = onAddCategoryClick,
                    onDateSelect = onDateSelect,
                    isSundayStart = isSundayStart,
                    selectedDate = selectedDate,
                )
            }

            itemsIndexed(
                items = flatList,
                key = { _, item -> item.id },
            ) { index, item ->
                DraggableListItem(
                    item = item,
                    itemIndex = index,
                    isDragging = item.id == draggingItem?.id,
                    draggingItemId = draggingItem?.id,
                    flatList = flatList,
                    targetIndex = targetIndex,
                    itemBounds = itemBounds,
                    itemSpacingPx = itemSpacingPx,
                    onTodoCheckBoxClick = onTodoCheckBoxClick,
                    onCategoryClick = onCategoryChipClick,
                    onTodoItemMenuClick = onTodoItemMenuClick,
                    modifier =
                        Modifier.onGloballyPositioned { coordinates ->
                            itemBounds[item.id] = coordinates.boundsInParent()
                        },
                )
            }

            item {
                Gap(height = 24.dp)
            }
        }

        draggingItem?.let { item ->
            val itemRect = itemBounds[item.id]
            Box(
                modifier =
                    Modifier
                        .offset(
                            x = with(localDensity) { fakeOffset.x.toDp() + 20.dp },
                            y = with(localDensity) { fakeOffset.y.toDp() },
                        )
                        .width(with(localDensity) { itemRect?.width?.toDp() } ?: Dp.Unspecified)
                        .background(BbangZipTheme.color.componentStrong_F6F6F5),
            ) {
                BbangZipTaskBox(
                    task = item.todo.content,
                    isCompleted = item.todo.isCompleted,
                    isLast = item.isLastInCategory,
                    startTime = item.todo.startTime,
                    categoryColor = CategoryColor.fromString(item.category.color).color,
                )
            }
        }
        AddTodoBottomSheet(
            isBottomSheetVisible = isAddTodoBottomSheetVisible,
            onDismissRequest = onAddTodoBottomSheetDismissRequest,
            focusManager = focusManager,
            todo = todoText,
            onTodoChange = onTodoTextChange,
            onSettingTimeClick = onTimePickerBottomSheetShowRequest,
            startTime = selectedStartTime,
            onDoneAction = onAddTodoDone,
        )
        TimePickerBottomSheet(
            isBottomSheetVisible = isTimePickerBottomSheetVisible,
            onDismissRequest = onTimePickerBottomSheetDismissRequest,
            onCancleButtonClick = onTimePickerBottomSheetDismissRequest,
            onConfirmButtonClick = onTimeConfirmButtonClick,
            initialTime = selectedStartTime ?: LocalTime.of(12, 0),
            onClearButtonClick = onTimePickerBottomSheetClearButtonClick
        )
        CommitmentBottomSheet(
            isBottomSheetVisible = isCommitmentBottomSheetVisible,
            onDismissRequest = onCommitmentBottomSheetDismissRequest,
            focusManager = focusManager,
            commitmentMessage = textFieldCommitmentMessage,
            oncommitmentMessageChange = onTextFieldCommitmentMessageChange,
            onDoneAction = onCommitmentDone,
        )

        if (selectedTodoItem != null) {
            TodoSettingBottomSheet(
                isBottomSheetVisible = isTodoSettingBottomSheetVisible,
                onDismissRequest = onTodoSettingBottomSheetDismissRequest,
                todoName = selectedTodoItem.todo.content,
                categoryName = selectedTodoItem.category.name,
                isCompleted = selectedTodoItem.todo.isCompleted,
                startTime = selectedTodoItem.todo.startTime,
                onEditButtonClick = onModifyTodoNameButtonClick,
                onDeleteButtonClick = onDeleteTodoButtonClick,
                onActionRowClick = { actionType ->
                    when (actionType) {
                        TodoSettingActionType.START_TIME -> onModifyTodoStartTimeClick()
                        TodoSettingActionType.POSTPONE -> onMoveTodoToTomorrowClick()
                        TodoSettingActionType.DUPLICATE -> onCopyTodoClick()
                        TodoSettingActionType.CHANGE_DATE -> onModifyTodoDateClick()
                        TodoSettingActionType.REPEAT -> onRepeatTodoClick()
                    }
                },
            )
        }
        if (selectedTodoItem != null) {
            EditTodoBottomSheet(
                isBottomSheetVisible = isEditTodoBottomSheetVisible,
                onDismissRequest = onEditTodoBottomSheetDismissRequest,
                focusManager = focusManager,
                todo = todoText,
                onTodoChange = onTodoTextChange,
                onDoneAction = onEditTodoDone,
            )
        }
        ChangeDateBottomSheet(
            isBottomSheetVisible = isMonthlyCalendarBottomSheetVisible,
            onDismissRequest = onMonthlyCalendarBottomSheetDismissRequest,
            date = selectedMonthlyDate,
            onSaveButtonClick = onDateSaveButtonClick,
            onDateSelect = onMonthlyDateSelect,
            isDateSavable = isDateSavable,
            isSundayStart = isSundayStart,
        )
    }
}

private fun updateTargetIndex(
    lazyListState: LazyListState,
    flatList: List<ListItem>,
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
            val flatListIndex = it.index - LIST_HEADER_COUNT
            if (flatListIndex >= 0) {
                newTargetIndex = flatListIndex
            }
        }

    val firstDraggableItemInfo = lazyListState.layoutInfo.visibleItemsInfo.find { it.index >= LIST_HEADER_COUNT }
    if (firstDraggableItemInfo != null && touchPointY < firstDraggableItemInfo.offset) {
        newTargetIndex = 0
    }

    if (newTargetIndex == 0 && flatList.getOrNull(0) is ListItem.CategoryItem) {
        return 1
    }

    return newTargetIndex
}

private fun calculateAnimatedShift(
    draggingItemId: String?,
    currentDraggingItemIndex: Int?,
    targetIndex: Int?,
    currentItemId: String,
    flatListIds: List<String>,
    itemBounds: Map<String, Rect>,
    itemSpacing: Float,
): Float {
    if (draggingItemId == null || currentDraggingItemIndex == null || targetIndex == null) {
        return 0f
    }
    if (currentItemId == draggingItemId) {
        return 0f
    }

    val currentItemIndexInFlatList = flatListIds.indexOf(currentItemId)
    val draggingItemHeight = itemBounds[draggingItemId]?.height ?: 0f
    val shiftAmount = draggingItemHeight + itemSpacing

    return when {
        currentDraggingItemIndex < targetIndex && currentItemIndexInFlatList in (currentDraggingItemIndex + 1)..targetIndex -> -shiftAmount
        currentDraggingItemIndex > targetIndex && currentItemIndexInFlatList in targetIndex until currentDraggingItemIndex -> shiftAmount
        else -> 0f
    }
}

@Composable
private fun DraggableListItem(
    modifier: Modifier = Modifier,
    item: ListItem,
    itemIndex: Int,
    isDragging: Boolean,
    draggingItemId: String?,
    flatList: List<ListItem>,
    targetIndex: Int?,
    itemBounds: Map<String, Rect>,
    itemSpacingPx: Float,
    onTodoCheckBoxClick: (todoId: Int, categoryId: Int, isChecked: Boolean) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onTodoItemMenuClick: (ListItem.TodoItem) -> Unit,
) {
    val currentDraggingItemIndex =
        remember(draggingItemId, flatList) {
            draggingItemId?.let { id -> flatList.indexOfFirst { it.id == id } }
        }

    val animatedShiftTarget =
        remember(draggingItemId, targetIndex, item.id, flatList) {
            calculateAnimatedShift(
                draggingItemId = draggingItemId,
                currentDraggingItemIndex = currentDraggingItemIndex,
                targetIndex = targetIndex,
                currentItemId = item.id,
                flatListIds = flatList.map { it.id },
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
            modifier
                .padding(horizontal = 20.dp)
                .graphicsLayer {
                    translationY = animatedShiftY
                    alpha = if (isDragging) 0f else 1f
                },
    ) {
        when (item) {
            is ListItem.CategoryItem -> {
                Column {
                    Gap(height = if (itemIndex == 0) 4.dp else 16.dp)
                    BbangZipCategoryChip(
                        categoryColor = CategoryColor.fromString(item.category.color).color,
                        categoryName = item.category.name,
                        onClick = { onCategoryClick(item.category) },
                    )
                }
            }

            is ListItem.TodoItem -> {
                BbangZipTaskBox(
                    task = item.todo.content,
                    isCompleted = item.todo.isCompleted,
                    onCheckBoxClick = { isChecked ->
                        onTodoCheckBoxClick(item.todo.todoId, item.category.id, isChecked)
                    },
                    onMenuClick = {
                        onTodoItemMenuClick(item)
                    },
                    isLast = item.isLastInCategory,
                    startTime = item.todo.startTime,
                    categoryColor = CategoryColor.fromString(item.category.color).color,
                )
            }
        }
    }
}

@Composable
private fun ListHeader(
    commitmentMessage: String,
    isMenuOpen: Boolean,
    onMenuClick: () -> Unit,
    onCommitmentAreaClick: () -> Unit,
    completedTodoCount: Int,
    totalTodoCount: Int,
    onManageCategoryClick: () -> Unit,
    onAddCategoryClick: () -> Unit,
    onDateSelect: (LocalDate) -> Unit,
    isSundayStart: Boolean,
    selectedDate: LocalDate,
) {
    Column {
        CommitmentMessageBox(
            commitmentMessage = commitmentMessage,
            onCommitmentAreaClick = onCommitmentAreaClick,
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            BbangZipWeeklyCalendar(
                initialDate = selectedDate,
                startDayOfWeek = if (isSundayStart) DayOfWeek.SUNDAY else DayOfWeek.MONDAY,
                onDateSelected = onDateSelect,
                onMenuClick = onMenuClick,
            )
            if (isMenuOpen) {
                MenuPopup(
                    modifier =
                        Modifier
                            .offset(x = (-20).dp, y = 9.dp),
                    onDismissRequest = onMenuClick,
                    onManageCategoryClick = onManageCategoryClick,
                    onAddCategoryClick = onAddCategoryClick,
                )
            }
        }

        Gap(height = 20.dp)

        CompleteTodoCounter(
            completedTodoCount = completedTodoCount,
            totalTodoCount = totalTodoCount,
        )
    }
}

@Composable
fun CommitmentMessageBox(
    commitmentMessage: String,
    onCommitmentAreaClick: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .noRippleClickable(onClick = onCommitmentAreaClick)
                .background(BbangZipTheme.color.secondaryStrong_F2EAE4)
                .clipToBounds(),
    ) {
        Box(
            modifier =
                Modifier
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .fillMaxWidth(2 / 3f)
                    .align(Alignment.CenterStart),
        ) {
            Text(
                text = commitmentMessage,
                color = BbangZipTheme.color.labelNormal_6B6560.copy(0.7f),
                style = BbangZipTheme.typography.body4Medium,
            )
        }
        Image(
            painter = painterResource(R.drawable.img_smile_bread),
            contentDescription = stringResource(R.string.todo_smile_bread_description),
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 19.dp)
                    .offset(y = 13.dp),
        )
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
                contentDescription = stringResource(R.string.todo_bread_icon_description),
                tint = BbangZipTheme.color.labelAlternative_A29D96,
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_default_24),
                contentDescription = stringResource(R.string.todo_check_icon_description),
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

@Composable
private fun MenuPopup(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onManageCategoryClick: () -> Unit = {},
    onAddCategoryClick: () -> Unit = {},
) {
    Popup(
        alignment = Alignment.BottomEnd,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
    ) {
        Box(
            modifier =
                modifier
                    .dropShadow(
                        shape = RoundedCornerShape(12.dp),
                        color = BbangZipTheme.color.staticBlack_121212.copy(0.15f),
                        blur = 4.dp,
                        offsetX = 2.dp,
                    ),
        ) {
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .width(IntrinsicSize.Max),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier =
                            Modifier
                                .noRippleClickable(onClick = onAddCategoryClick)
                                .padding(horizontal = 8.dp)
                                .padding(top = 7.dp, bottom = 13.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_plus_bold_24),
                            contentDescription = stringResource(R.string.todo_add_category_icon_description),
                            tint = BbangZipTheme.color.labelNormal_6B6560,
                            modifier = Modifier.size(16.dp),
                        )

                        Gap(width = 8.dp)

                        Text(
                            text = stringResource(R.string.todo_add_category_button),
                            color = BbangZipTheme.color.labelNormal_6B6560,
                            style = BbangZipTheme.typography.body3Medium,
                        )
                    }

                    HorizontalDivider(
                        color = BbangZipTheme.color.labelDisable_E4E2E0,
                    )

                    Row(
                        modifier =
                            Modifier
                                .noRippleClickable(onClick = onManageCategoryClick)
                                .padding(horizontal = 8.dp)
                                .padding(top = 13.dp, bottom = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_pencil_default_24),
                            contentDescription = stringResource(R.string.todo_edit_category_icon_description),
                            tint = BbangZipTheme.color.labelNormal_6B6560,
                            modifier = Modifier.size(16.dp),
                        )

                        Gap(width = 8.dp)

                        Text(
                            text = stringResource(R.string.todo_edit_category_button),
                            color = BbangZipTheme.color.labelNormal_6B6560,
                            style = BbangZipTheme.typography.body3Medium,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
    val exampleCategories =
        listOf(
            Category(
                id = 1,
                name = "제과제빵점",
                color = "RED1",
                todos =
                    listOf(
                        Todo(
                            todoId = 11,
                            content = "두줄 두줄",
                            isCompleted = true,
                            startTime = LocalTime.of(11, 0),
                        ),
                        Todo(
                            todoId = 12,
                            content = "제과제빵점_한줄_실패",
                            isCompleted = false,
                            startTime = null,
                        ),
                    ),
            ),
            Category(
                id = 2,
                name = "경제학개론",
                color = "BLUE1",
                todos =
                    listOf(
                        Todo(
                            todoId = 21,
                            content = "경제학개론_한줄_완료",
                            isCompleted = true,
                            startTime = null,
                        ),
                    ),
            ),
        )

    val flatList =
        exampleCategories.flatMap { category ->
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
        }
    TodoScreen(
        flatList = flatList,
        totalTodoCount = 5,
        completedTodoCount = 2,
        isMenuOpen = false,
        isTimePickerBottomSheetVisible = false,
        isAddTodoBottomSheetVisible = false,
        todoText = "새로운 할 일",
        selectedStartTime = LocalTime.NOON,
        selectedTodoItem = null,
        onMenuClick = {},
        onListItemMove = { _, _ -> },
        onTodoCheckBoxClick = { _, _, _ -> },
        onAddTodoDone = {},
        onTimeConfirmButtonClick = {},
        onTimePickerBottomSheetDismissRequest = {},
        onAddTodoBottomSheetDismissRequest = {},
        onTimePickerBottomSheetShowRequest = {},
        onTimePickerBottomSheetClearButtonClick = {},
        onTodoTextChange = {},
        onCategoryChipClick = {},
        textFieldCommitmentMessage = "",
        confirmedCommitmentMessage = "",
        isCommitmentBottomSheetVisible = false,
        onCommitmentAreaClick = {},
        onCommitmentDone = {},
        onTextFieldCommitmentMessageChange = { },
        onCommitmentBottomSheetDismissRequest = {},
        isTodoSettingBottomSheetVisible = false,
        onTodoItemMenuClick = {},
        onTodoSettingBottomSheetDismissRequest = {},
        isEditTodoBottomSheetVisible = false,
        onEditTodoBottomSheetDismissRequest = { },
        isMonthlyCalendarBottomSheetVisible = false,
        selectedMonthlyDate = LocalDate.now(),
        onMonthlyCalendarBottomSheetDismissRequest = {},
        onDateSaveButtonClick = { },
        onMonthlyDateSelect = {},
        isDateSavable = false,
        isSundayStart = TODO(),
        selectedDate = TODO(),
    )
}
