package org.android.bbangzip.presentation.common.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BbangZipTheme

private const val MAX_CATEGORY_NAME_CHARACTER = 20

/**
 * 카테고리를 표현하는 칩 컴포넌트
 *
 * @param categoryColor 카테고리를 나타내는 색상
 * @param categoryName 표시할 카테고리 이름
 * @param modifier Modifier 설정
 * @param onClick 클릭 시 실행되는 콜백
 * @param isClickable 클릭 가능 여부
 * @param isDraggable 드래그 가능 여부 (현재는 사용되지 않음)
 * @param maxCharacters 카테고리 이름의 최대 글자 수
 */
@Composable
fun BbangZipCategoryChip(
    categoryColor: Color,
    categoryName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isClickable: Boolean = true,
    isDraggable: Boolean = true,
    isTrailingIconVisible: Boolean = true,
    maxCharacters: Int = MAX_CATEGORY_NAME_CHARACTER,
) {
    val displayText =
        truncateText(
            text = categoryName,
            maxCharacters = maxCharacters,
        )

    Row(
        modifier =
            modifier
                .clip(BbangZipCategoryChipDefaults.CHIP_SHAPE)
                .then(
                    if (isClickable) {
                        Modifier.noRippleClickable(onClick = onClick)
                    } else {
                        Modifier // isClickable이 false이면 아무 효과 없는 Modifier를 적용
                    }
                )
                .background(
                    color = BbangZipCategoryChipDefaults.containerColor(),
                )
                .padding(
                    BbangZipCategoryChipDefaults.CHIP_PADDING,
                ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BbangZipCategoryChipDefaults.CONTENT_GAP),
    ) {
        Box(
            modifier =
                Modifier
                    .size(BbangZipCategoryChipDefaults.DOT_SIZE)
                    .clip(CircleShape)
                    .background(color = categoryColor),
        )

        Text(
            text = displayText,
            style = BbangZipCategoryChipDefaults.categoryNameStyle(),
            color = BbangZipCategoryChipDefaults.categoryNameColor(),
            overflow = TextOverflow.Ellipsis,
        )

        if (isTrailingIconVisible) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_bold_24),
                contentDescription = null,
                modifier = Modifier.size(BbangZipCategoryChipDefaults.ICON_SIZE),
                tint = BbangZipCategoryChipDefaults.iconColor(),
            )
        }
    }
}

@Composable
private fun truncateText(
    text: String,
    maxCharacters: Int,
): String {
    return remember(text, maxCharacters) {
        if (text.length > maxCharacters) {
            text.take(maxCharacters) + "..."
        } else {
            text
        }
    }
}

@Preview
@Composable
private fun BbangZipCategoryChipPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BbangZipCategoryChip(
            categoryColor = BbangZipTheme.color.todoRed1_EA7152,
            categoryName = "label",
        )
        BbangZipCategoryChip(
            categoryColor = BbangZipTheme.color.todoBlue1_5C62AC,
            categoryName = "abcdefghijklmnabcdefghijklmnabcdefghijklmn",
        )
    }
}
