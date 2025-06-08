package org.android.bbangzip.presentation.component.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.component.button.model.ButtonColors
import org.android.bbangzip.ui.theme.BbangZipTheme

object BbangZipButtonDefaults {
    // 버튼 크기 관련 상수
    val BUTTON_VERTICAL_PADDING = 14.dp
    val BUTTON_CONTENT_GAP = 4.dp

    // 버튼 모양 관련 상수
    val BUTTON_BORDER_RADIUS = 32.dp

    // 버튼 모양 객체
    val BUTTON_SHAPE = RoundedCornerShape(BUTTON_BORDER_RADIUS)

    @Composable
    fun defaultButtonColors(): ButtonColors =
        ButtonColors(
            enabledContainerColor = BbangZipTheme.color.primaryNormal_897869,
            enabledContentColor = BbangZipTheme.color.staticWhite_FFFFFF,
            disabledContainerColor = BbangZipTheme.color.labelDisable_E4E2E0,
            disabledContentColor = BbangZipTheme.color.labelAssistive_C9C7C5,
        )

    @Composable
    fun defaultButtonColors(
        enabledContainerColor: Color = BbangZipTheme.color.primaryNormal_897869,
        enabledContentColor: Color = BbangZipTheme.color.staticWhite_FFFFFF,
        disabledContainerColor: Color = BbangZipTheme.color.labelDisable_E4E2E0,
        disabledContentColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
    ): ButtonColors =
        defaultButtonColors().copy(
            enabledContainerColor = enabledContainerColor,
            enabledContentColor = enabledContentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        )
}
