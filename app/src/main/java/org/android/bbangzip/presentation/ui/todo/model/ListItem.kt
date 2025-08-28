package org.android.bbangzip.presentation.ui.todo.model

import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.Todo

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
