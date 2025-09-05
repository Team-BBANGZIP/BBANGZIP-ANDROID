package org.android.bbangzip.presentation.ui.timer.todo

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.model.Category
import org.android.bbangzip.presentation.model.ListItem
import org.android.bbangzip.presentation.model.Todo
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoContract.TimerTodoEvent
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoContract.TimerTodoReduce
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoContract.TimerTodoSideEffect
import org.android.bbangzip.presentation.ui.timer.todo.TimerTodoContract.TimerTodoState
import org.android.bbangzip.presentation.util.base.BaseViewModel
import java.time.LocalTime
import javax.inject.Inject
import kotlin.collections.forEach

@HiltViewModel
class TimerTodoViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TimerTodoEvent, TimerTodoState, TimerTodoReduce, TimerTodoSideEffect>(savedStateHandle) {
    override fun createInitialState(savedState: Parcelable?): TimerTodoState {
        return savedState as? TimerTodoState ?: TimerTodoState()
    }

    init {
        setEvent(TimerTodoEvent.Initialize)
    }

    override fun handleEvent(event: TimerTodoEvent) {
        when (event) {
            is TimerTodoEvent.Initialize -> TODO()
            is TimerTodoEvent.OnBackIconClick -> TODO()
            is TimerTodoEvent.OnExitBtnClick -> TODO()
            is TimerTodoEvent.OnMenuClick -> TODO()
            is TimerTodoEvent.OnRestartTimerBtnClick -> TODO()
            is TimerTodoEvent.OnTodoCheckBoxClick -> TODO()
        }
    }

    override fun reduceState(state: TimerTodoState, reduce: TimerTodoReduce): TimerTodoState {
        return when (reduce) {
            is TimerTodoReduce.UpdateFlatList -> state.copy(flatList = reduce.flatList)
            is TimerTodoReduce.UpdateCategories -> state.copy(categories = reduce.categories, flatList = reduce.flatList)
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

    private fun List<Category>.toFlatList(): List<ListItem> {
        return this.flatMap { category ->
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

    private fun getExampleList(): List<Category> {
        return listOf(
            Category(
                categoryId = 1,
                categoryName = "제과제빵점",
                categoryColor = "RED1",
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
                categoryColor = "YELLOW1",
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
                categoryColor = "GREEN1",
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
                categoryColor = "BLUE1",
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
                categoryColor = "PURPLE1",
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
                categoryColor = "RED2",
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
                categoryColor = "YELLOW2",
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
    }

    private fun postCheckedTodo(){}
}