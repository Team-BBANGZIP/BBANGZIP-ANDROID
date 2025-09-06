package org.android.bbangzip.presentation.ui.timer.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.component.taskbox.BbangZipTaskBox
import org.android.bbangzip.presentation.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.ListItem
import org.android.bbangzip.presentation.model.Todo
import org.android.bbangzip.presentation.type.CategoryColor
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalTime


//TODO timeOptionIndex 연결 / todoadd 바텀시트 추가 , picker 추가
@Composable
fun TimerTodoScreen(
    uiState: TimerTodoContract.TimerTodoState,
    timeOptionIndex: Int,
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit = {},
    onExitBtnClick: () -> Unit = {},
    onRestartTimerBtnClick: () -> Unit = {},
    onAddTodoIconClick: () -> Unit = {},
    onTodoCheckBoxClick: (categoryId: Int, todoId: Int, isChecked: Boolean) -> Unit = { _, _, _ -> },
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
                .statusBarsPadding(),
    ) {
        LazyColumn(
            modifier = modifier.padding(bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item {
                TodoTopBar(onBackIconClick = onBackIconClick)
            }

            item { Gap(18.dp) }

            item {
                TodoTitle(modifier = Modifier.padding(horizontal = 20.dp))
            }

            item { Gap(30.dp) }

            itemsIndexed(
                items = uiState.flatList,
                key = { _, item -> item.id },
            ) { index, item ->
                TodoListItem(
                    item = item,
                    itemIndex = index,
                    onTodoCheckBoxClick = onTodoCheckBoxClick,
                    onAddTodoIconClick = onAddTodoIconClick,
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
            }
        }

        DualActionButton(
            onRestartBtnClick = onRestartTimerBtnClick,
            onExitBtnClick = onExitBtnClick,
            timeOptionIndex = timeOptionIndex,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 12.dp),
        )
    }
}


@Composable
private fun TodoTopBar(
    onBackIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BbangZipBaseTopBar(
        modifier = modifier,
        leadingIcon = R.drawable.ic_arrow_left_24,
        leadingIconColor = BbangZipTheme.color.labelAlternative_A29D96,
        onLeadingIconClick = onBackIconClick
    )
}

@Composable
fun TodoTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.timer_todo_title),
        style = BbangZipTheme.typography.picker1SemiBold,
        color = BbangZipTheme.color.labelNormal_6B6560,
        modifier = modifier
    )
}


@Composable
private fun TodoListItem(
    item: ListItem,
    itemIndex: Int,
    onTodoCheckBoxClick: (categoryId: Int, todoId: Int, isChecked: Boolean) -> Unit,
    onAddTodoIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (item) {
            is ListItem.CategoryItem -> {
                Column {
                    Gap(height = if (itemIndex == 0) 4.dp else 16.dp)
                    BbangZipCategoryChip(
                        categoryColor = CategoryColor.fromString(item.category.categoryColor).color,
                        categoryName = item.category.categoryName,
                        onClick = onAddTodoIconClick
                    )
                }
            }

            is ListItem.TodoItem -> {
                BbangZipTaskBox(
                    task = item.todo.content,
                    isCompleted = item.todo.isCompleted,
                    onCheckBoxClick = { isChecked ->
                        onTodoCheckBoxClick(item.category.categoryId, item.todo.todoId, isChecked)
                    },
                    isLast = item.isLastInCategory,
                    startTime = item.todo.startTime,
                    categoryColor = CategoryColor.fromString(item.category.categoryColor).color,
                )
            }
        }
    }
}

@Composable
fun DualActionButton(
    onRestartBtnClick: () -> Unit,
    onExitBtnClick: () -> Unit,
    timeOptionIndex: Int,
    modifier: Modifier = Modifier
) {
    val leftBtnText =
        if (timeOptionIndex == 0) {
            stringResource(R.string.complete_sheet_left_btn_thirty)
        } else {
            stringResource(R.string.complete_sheet_left_btn_sixty)
        }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        BbangzipBaseButton(
            modifier = Modifier.weight(140f),
            onClick = { onRestartBtnClick() },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_plus_bold_24),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            },
            content = {
                Text(
                    text = leftBtnText,
                    style = BbangZipTheme.typography.body2Medium,
                )
            },
        )

        BbangzipBaseButton(
            modifier = Modifier.weight(187f),
            onClick = { onExitBtnClick() },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_x_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            },
            colors =
                BbangZipButtonDefaults.colors(
                    enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                ),
            content = {
                Text(
                    text = stringResource(R.string.button_label_exit),
                    style = BbangZipTheme.typography.body2Medium,
                )
            },
        )
    }
}

@Preview
@Composable
private fun TimerTodoScreenPreview() {
    val exampleCategories =
        listOf(
            Category(
                categoryId = 1,
                categoryName = "제과제빵점",
                categoryColor = "BbangZipTheme.color.todoRed1_EA7152",
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
                    ),
            ),
            Category(
                categoryId = 2,
                categoryName = "경제학개론",
                categoryColor = "BbangZipTheme.color.todoBlue1_5C62AC",
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

    val previewState =
        TimerTodoContract.TimerTodoState(
            categories = exampleCategories,
            flatList = flatList,
        )
    TimerTodoScreen(uiState = previewState, timeOptionIndex = 0)
}