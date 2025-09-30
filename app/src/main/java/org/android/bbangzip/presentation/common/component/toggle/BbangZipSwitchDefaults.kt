package org.android.bbangzip.presentation.common.component.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.common.component.toggle.model.SwitchColors
import org.android.bbangzip.ui.theme.BbangZipTheme

object BbangZipSwitchDefaults {
    val CONTAINER_PADDING = 1.dp

    @Composable
    fun colors(
        checkedThumbColor: Color = BbangZipTheme.color.primaryNormal_897869,
        uncheckedThumbColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        checkedContainerColor: Color = BbangZipTheme.color.componentIvory_FDFDFD,
        uncheckedContainerColor: Color = BbangZipTheme.color.componentIvory_FDFDFD,
    ): SwitchColors =
        remember(
            checkedThumbColor,
            uncheckedThumbColor,
            checkedContainerColor,
            uncheckedContainerColor,
        ) {
            SwitchColors(
                checkedThumbColor = checkedThumbColor,
                uncheckedThumbColor = uncheckedThumbColor,
                checkedContainerColor = checkedContainerColor,
                uncheckedContainerColor = uncheckedContainerColor,
            )
        }
}
