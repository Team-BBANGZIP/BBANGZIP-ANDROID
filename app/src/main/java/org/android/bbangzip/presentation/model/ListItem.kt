package org.android.bbangzip.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface ListItem : Parcelable {
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