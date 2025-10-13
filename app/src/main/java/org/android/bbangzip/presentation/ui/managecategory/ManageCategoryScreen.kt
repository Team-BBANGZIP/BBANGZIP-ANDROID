package org.android.bbangzip.presentation.ui.managecategory

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.chip.BbangZipCategoryChip
import org.android.bbangzip.presentation.common.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.common.type.CategoryColor
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

data class CategoryItem(
    val id: Long,
    val name: String,
    val color: String,
    val isStopped: Boolean
)

@Composable
fun ManageCategoryScreen(
    categories: List<CategoryItem>,
    modifier: Modifier = Modifier,
    onTopBarTrailingIconClick: () -> Unit = {},
    onTopBarLeadingIconClick: () -> Unit = {},
    onCategoryChipClick: () -> Unit = {},
){
    val localDensity = LocalDensity.current

    val lazyListState = rememberLazyListState()

    var draggingItem by remember { mutableStateOf<CategoryItem?>(null) }
    var fakeOffset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BbangZipTheme.color.staticWhite_FFFFFF)
            .statusBarsPadding()
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(categories){
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        val longPress = awaitLongPressOrCancellation(down.id)

                        if(longPress != null){
                            val pressedLazyColumnItem =
                                lazyListState.layoutInfo.visibleItemsInfo
                                    .firstOrNull {
                                        val itemTopY = it.offset
                                        val itemBottomY = it.offset + it.size
                                        down.position.y >= itemTopY && down.position.y <= itemBottomY
                                    } ?: return@awaitEachGesture

                            val pressedLazyColumnIndex = pressedLazyColumnItem.index
                            val pressedIndexOfCategories = pressedLazyColumnIndex - 2
                            val pressedItemOfCategories = categories.getOrNull(pressedIndexOfCategories)

                            fakeOffset = Offset(0f, pressedLazyColumnItem.offset.toFloat())
                            draggingItem = pressedItemOfCategories
                        }
                    }
                },
            state = lazyListState,
        ) {
            stickyHeader {
                BbangZipBaseTopBar(
                    title = "카테고리 관리",
                    titleColor = BbangZipTheme.color.labelNormal_6B6560,
                    titleStyle = BbangZipTheme.typography.title2Medium,
                    leadingIcon = R.drawable.ic_arrow_left_24,
                    leadingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
                    trailingIcon = R.drawable.ic_plus_bold_24,
                    trailingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
                )
            }

            item{ Gap(height = 32.dp) }

            items(count = categories.size, key = { index -> categories[index].id }) { index ->
                val category = categories[index]

                BbangZipCategoryChip(
                    categoryColor = CategoryColor.fromString(category.color).color,
                    categoryName = category.name,
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 20.dp)
                        .graphicsLayer(
                            alpha = if(category.id == draggingItem?.id) 0f else 1f
                        ),
                    isTrailingIconVisible = false
                )
            }
        }

        draggingItem?.let { item ->
            Box(
                modifier =
                    Modifier
                        .padding(start = 20.dp, bottom = 20.dp)
                        .offset(
                            x = with(localDensity) { fakeOffset.x.toDp() },
                            y = with(localDensity) { fakeOffset.y.toDp() },
                        )
                        .background(
                            color = BbangZipTheme.color.componentStrong_F6F6F5,
//                            shape = RoundedCornerShape(32.dp)
                        ),
            ) {
                BbangZipCategoryChip(
                    categoryColor = CategoryColor.fromString(item.color).color,
                    categoryName = item.name,
                    isTrailingIconVisible = false
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManageCategoryScreenPreview(
){
    val categories = listOf(
        CategoryItem(
            id = 1,
            name = "제 과제 빵점",
            color = "RED1",
            isStopped = false
        ),
        CategoryItem(
            id = 2,
            name = "SOPT",
            color = "YELLOW1",
            isStopped = false
        ),
        CategoryItem(
            id = 3,
            name = "솝트대학교",
            color = "BLUE1",
            isStopped = false
        ),
    )
    BBANGZIPANDROIDTheme {
        ManageCategoryScreen(
            categories = categories
        )
    }
}