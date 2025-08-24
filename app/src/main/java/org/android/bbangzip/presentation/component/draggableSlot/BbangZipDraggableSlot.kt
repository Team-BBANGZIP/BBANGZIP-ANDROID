package org.android.bbangzip.presentation.component.draggableSlot

import androidx.compose.runtime.Composable

data class Category(
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String? = null,
    val todos: List<Todo>
)

data class Todo(
    val todoId: Int,
    val content: String,
    val isCompleted: Boolean,
    val startTime: String? = null
)

@Composable
fun BbangZipDraggableSlot(
    draggableList: List<Category>,
    categoryChip: @Composable () -> Unit,
    taskBox: @Composable () -> Unit,
){

}