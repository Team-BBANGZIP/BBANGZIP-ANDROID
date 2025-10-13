package org.android.bbangzip.presentation.ui.managecategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BbangZipTheme.color.staticWhite_FFFFFF)
            .statusBarsPadding()
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
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
            items(categories.size) { index ->
                val category = categories[index]

                if(index == 0) Gap(height = 32.dp)

                BbangZipCategoryChip(
                    categoryColor = CategoryColor.fromString(category.color).color,
                    categoryName = category.name,
                    modifier = Modifier.padding(start = 20.dp, bottom = 20.dp),
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