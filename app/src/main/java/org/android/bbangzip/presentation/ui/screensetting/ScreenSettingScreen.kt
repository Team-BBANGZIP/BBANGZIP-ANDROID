package org.android.bbangzip.presentation.ui.screensetting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.toggle.BbangZipSwitch
import org.android.bbangzip.presentation.common.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun ScreenSettingScreen(
    state: ScreenSettingContract.ScreenSettingState,
    onClickBack: () -> Unit,
    onToggleSundayStart: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        BbangZipBaseTopBar(
            title = stringResource(R.string.screen_setting_title),
            titleColor = BbangZipTheme.color.labelNormal_6B6560,
            titleStyle = BbangZipTheme.typography.title2Medium,
            backGroundColor = BbangZipTheme.color.backgroundNormal_FFFFFF,
            leadingIcon = R.drawable.ic_arrow_left_24,
            leadingIconColor = BbangZipTheme.color.labelAssistive_C9C7C5,
            onLeadingIconClick = onClickBack,
        )

        Gap(height = 20.dp)

        SundayStartSettingItem(
            isEnabled = state.isSundayStartEnabled,
            onToggle = onToggleSundayStart,
        )
    }
}

@Composable
private fun SundayStartSettingItem(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    onToggle: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.screen_setting_sunday_start_title),
                style = BbangZipTheme.typography.body2Medium,
                color = BbangZipTheme.color.labelNormal_6B6560,
                modifier = Modifier.weight(1f),
            )

            BbangZipSwitch(
                isChecked = isEnabled,
                onCheckedChange = onToggle,
                modifier = Modifier.width(44.dp),
            )
        }

        Gap(height = 4.dp)

        Text(
            text = stringResource(R.string.screen_setting_sunday_start_description),
            style = BbangZipTheme.typography.body4Medium,
            color = BbangZipTheme.color.labelAssistive_C9C7C5,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenSettingScreenPreview() {
    BBANGZIPANDROIDTheme {
        var isSundayStartEnabled by remember { mutableStateOf(false) }

        ScreenSettingScreen(
            state = ScreenSettingContract.ScreenSettingState(
                isSundayStartEnabled = isSundayStartEnabled,
            ),
            onClickBack = {},
            onToggleSundayStart = {
                isSundayStartEnabled = !isSundayStartEnabled
            },
        )
    }
}
