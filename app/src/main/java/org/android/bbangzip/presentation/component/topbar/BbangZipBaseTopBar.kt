package org.android.bbangzip.presentation.component.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun BbangZipBaseTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    titleColor: Color = Color.Black,
    titleStyle: TextStyle = BbangZipTheme.typography.title2Medium,
    backGroundColor: Color = BbangZipTheme.color.backgroundNormal_FFFFFF,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    leadingIconColor: Color = BbangZipTheme.color.staticBlack_121212,
    trailingIconColor: Color = BbangZipTheme.color.staticBlack_121212,
    onTrailingIconClick: () -> Unit = {},
    onLeadingIconClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier.background(backGroundColor),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier =
                    modifier
                        .width(56.dp)
                        .height(56.dp),
                contentAlignment = Alignment.Center,
            ) {
                leadingIcon?.let {
                    Icon(
                        imageVector = ImageVector.vectorResource(it),
                        contentDescription = null,
                        tint = leadingIconColor,
                        modifier =
                            Modifier
                                .noRippleClickable { onLeadingIconClick }
                                .padding(8.dp),
                    )
                }
            }

            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = titleColor,
                style = titleStyle,
                textAlign = TextAlign.Center,
            )

            Box(
                modifier =
                    Modifier
                        .width(56.dp)
                        .height(56.dp),
                contentAlignment = Alignment.Center,
            ) {
                trailingIcon?.let {
                    Icon(
                        imageVector = ImageVector.vectorResource(it),
                        contentDescription = null,
                        tint = trailingIconColor,
                        modifier =
                            Modifier
                                .noRippleClickable { onTrailingIconClick }
                                .padding(8.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BbangZipMenuTopBarPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            BbangZipBaseTopBar(
                title = "제 과제 빵점",
            )
        }
    }
}
