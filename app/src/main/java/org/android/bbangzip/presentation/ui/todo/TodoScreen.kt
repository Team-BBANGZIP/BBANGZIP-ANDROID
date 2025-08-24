package org.android.bbangzip.presentation.ui.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.component.taskbox.BbangZipTaskBox
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import java.time.LocalTime

val colorMapper = mapOf(
    "Red" to Color.Red,
    "Blue" to Color.Blue,
    "Green" to Color.Green,
    "Yellow" to Color.Yellow
)
val exampleList = listOf(
    Category(
        categoryId = 1,
        categoryName = "제과제빵점",
        categoryColor = "Red",
        todos = listOf(
            Todo(
                todoId = 1,
                content = "두줄 \n 두줄",
                isCompleted = true,
                startTime = LocalTime.of(11,0)
            ),
            Todo(
                todoId = 2,
                content = "제과제빵점_한줄_실패",
                isCompleted = false,
                startTime = null
            ),
            Todo(
                todoId = 3,
                content = "제과제빵점_한줄_완료",
                isCompleted = true,
                startTime = null
            )
        )
    ),
    Category(
        categoryId = 2,
        categoryName = "경제학개론",
        categoryColor = "Blue",
        todos = listOf(
            Todo(
                todoId = 1,
                content = "경제학개론_한줄_완료",
                isCompleted = true,
                startTime = null
            ),
            Todo(
                todoId = 2,
                content = "경제학개론 \n 두줄_실패",
                isCompleted = false,
                startTime = LocalTime.of(11, 0)
            ),
        )
    ),
    Category(
        categoryId = 3,
        categoryName = "운동",
        categoryColor = "Green",
        todos = listOf(
            Todo(
                todoId = 1,
                content = "헬스장 가기",
                isCompleted = false,
                startTime = LocalTime.of(18,0)
            ),
            Todo(
                todoId = 2,
                content = "저녁 유산소 30분",
                isCompleted = true,
                startTime = LocalTime.of(19,30)
            )
        )
    ),
    Category(
        categoryId = 4,
        categoryName = "스터디",
        categoryColor = "Yellow",
        todos = listOf(
            Todo(
                todoId = 1,
                content = "알고리즘 문제 풀이",
                isCompleted = true,
                startTime = LocalTime.of(20,0)
            ),
            Todo(
                todoId = 2,
                content = "코틀린 스터디 준비",
                isCompleted = false,
                startTime = null
            ),
            Todo(
                todoId = 3,
                content = "CS 스터디 복습",
                isCompleted = true,
                startTime = LocalTime.of(10,0)
            )
        )
    ),
    Category(
        categoryId = 5,
        categoryName = "개인 프로젝트",
        categoryColor = "Red",
        todos = listOf(
            Todo(
                todoId = 1,
                content = "UI 디자인 검토",
                isCompleted = false,
                startTime = null
            ),
            Todo(
                todoId = 2,
                content = "백엔드 API 연동",
                isCompleted = false,
                startTime = LocalTime.of(14,0)
            )
        )
    )
)
data class Category(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
    val todos: List<Todo>
)

data class Todo(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: LocalTime? = null
)

@Composable
fun TodoList(
    draggableList: List<Category>,
){
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ){
        draggableList.forEach { category ->
            val categoryColor = colorMapper.getValue(category.categoryColor)
            item {
                BbangZipCategoryChip(
                    categoryColor = categoryColor,
                    categoryName = category.categoryName,
                )
            }
            items(category.todos.size){ index ->
                BbangZipTaskBox(
                    task = category.todos[index].content,
                    isCompleted = category.todos[index].isCompleted,
                    isLast = index == category.todos.size - 1,
                    startTime = category.todos[index].startTime,
                    categoryColor = categoryColor,
                    onHeightMeasure = {},
                )
            }

            item{
                Gap(height = 20.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListPreview(){
    BBANGZIPANDROIDTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            TodoList(
                draggableList = exampleList
            )
        }
    }
}