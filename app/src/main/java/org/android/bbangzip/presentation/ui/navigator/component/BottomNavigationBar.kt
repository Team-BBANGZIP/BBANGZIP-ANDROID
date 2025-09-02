package org.android.bbangzip.presentation.ui.navigator.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import okhttp3.internal.toImmutableList
import org.android.bbangzip.presentation.type.BottomNavigationType
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun BottomNavigationBar(
    isVisible: Boolean,
    bottomNaviBarItems: List<BottomNavigationType>,
    currentNaviBarItemSelected: BottomNavigationType?,
    onBottomNaviBarItemSelected: (BottomNavigationType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = BbangZipTheme.color.labelDisable_E4E2E0

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(),
    ) {
        Box(
            modifier =
            modifier
                .fillMaxWidth()
                .background(color = BbangZipTheme.color.componentIvory_FDFDFD)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth,
                    )
                }
                .padding(top = 10.dp),
        ) {
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                bottomNaviBarItems.forEach { navItem ->
                    BottomNavigationItem(
                        isSelected = currentNaviBarItemSelected == navItem,
                        bottomNaviType = navItem,
                        onBottomNaviBarItemSelected = onBottomNaviBarItemSelected,
                        bottomNaviIcon = navItem.bottomNaviIcon,
                        bottomNaviTitle = navItem.bottomNaviTitle,
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationItem(
    isSelected: Boolean,
    bottomNaviType: BottomNavigationType,
    onBottomNaviBarItemSelected: (BottomNavigationType) -> Unit,
    @DrawableRes bottomNaviIcon: Int,
    @StringRes bottomNaviTitle: Int,
    modifier: Modifier = Modifier,
    spacing: Dp = 4.dp,
) {
    Column(
        modifier =
        modifier
            .noRippleClickable {
                onBottomNaviBarItemSelected(bottomNaviType)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 26.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = bottomNaviIcon),
                contentDescription = stringResource(bottomNaviTitle),
                modifier = Modifier.size(20.dp),
                tint =
                if (isSelected) {
                    BbangZipTheme.color.labelStrong_463D34
                } else {
                    BbangZipTheme.color.labelAssistive_C9C7C5
                },
            )
        }

        Gap(spacing)

        Text(
            text = stringResource(bottomNaviTitle),
            color =
            if (isSelected) {
                BbangZipTheme.color.labelStrong_463D34
            } else {
                BbangZipTheme.color.labelAssistive_C9C7C5
            },
            style =
            if (isSelected) {
                BbangZipTheme.typography.label5SemiBold
            } else {
                BbangZipTheme.typography.label6Medium
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BBANGZIPANDROIDTheme {
        BottomNavigationBar(
            isVisible = true,
            bottomNaviBarItems = BottomNavigationType.entries.toImmutableList(),
            currentNaviBarItemSelected = BottomNavigationType.TIMER,
            onBottomNaviBarItemSelected = {},
        )
    }
}
