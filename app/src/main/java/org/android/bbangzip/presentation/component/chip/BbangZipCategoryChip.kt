package org.android.bbangzip.presentation.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun BbangZipCategoryChip(
    categoryColor: Color,
    categoryName: String,
    modifier: Modifier = Modifier,
    onclick: () -> Unit = {},
    isClickable: Boolean = true,
    isDraggable: Boolean = true,
    maxCharacters: Int = 20
) {
    val displayText = truncateText(
        text = categoryName,
        maxCharacters = maxCharacters
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .noRippleClickable(
                enabled = isClickable,
                onClick = onclick
            )
            .background(
                color = BbangZipTheme.color.secondaryLight_FAF6F3
            )
            .padding(
                horizontal = 10.dp,
                vertical = 7.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(11.dp)
                .clip(CircleShape)
                .background(color = categoryColor)
        )

        Text(
            text = displayText,
            style = BbangZipTheme.typography.label3SemiBold,
            color = BbangZipTheme.color.labelStrong_463D34,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_plus_bold_24),
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = BbangZipTheme.color.labelAlternative_A29D96
        )
    }
}

@Composable
private fun truncateText(text: String, maxCharacters: Int): String {
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
        modifier = Modifier.fillMaxSize()
    ){
        BbangZipCategoryChip(
            categoryColor = BbangZipTheme.color.todoRed1_EA7152,
            categoryName = "label"
        )
        BbangZipCategoryChip(
            categoryColor = BbangZipTheme.color.todoBlue1_5C62AC,
            categoryName = "abcdefghijklmnabcdefghijklmnabcdefghijklmn"
        )
    }
}

