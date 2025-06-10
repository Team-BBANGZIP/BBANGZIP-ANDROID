package org.android.bbangzip.presentation.component.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme

object TextFieldDefaults {
    // ContentPadding values for the text field
    val CONTENT_PADDING_TOP = 12.dp
    val CONTENT_PADDING_BOTTOM = 10.dp
    val CONTENT_PADDING_START = 12.dp
    val CONTENT_PADDING_END = 12.dp

    val BORDER_RADIUS = 8.dp
    val BORDER_SIZE = 0.dp

    @Composable
    fun contentPadding(
        start: Dp = CONTENT_PADDING_START,
        top: Dp = CONTENT_PADDING_TOP,
        end: Dp = CONTENT_PADDING_END,
        bottom: Dp = CONTENT_PADDING_BOTTOM
    ): PaddingValues = PaddingValues(start, top, end, bottom)

    @Composable
    fun defaultTextFieldColors(): TextFieldColors = TextFieldColors(
        focusedTextColor = BbangZipTheme.color.labelNormal_6B6560,
        unfocusedTextColor = BbangZipTheme.color.labelAssistive_C9C7C5,
        focusedContainerColor = BbangZipTheme.color.labelStrong_463D34,
        unfocusedContainerColor = BbangZipTheme.color.labelStrong_463D34,
        focusedPlaceholderColor = BbangZipTheme.color.labelAssistive_C9C7C5,
        unfocusedPlaceholderColor = BbangZipTheme.color.labelAssistive_C9C7C5,
        cursorColor = BbangZipTheme.color.labelStrong_463D34,
        borderColor = Color.Transparent,
        underLineColor = BbangZipTheme.color.primaryNormal_897869,
    )

    @Composable
    fun defaultTextFieldColors(
        focusedTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        unfocusedTextColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        focusedContainerColor: Color = Color.Transparent,
        unfocusedContainerColor: Color = Color.Transparent,
        focusedPlaceholderColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        unfocusedPlaceholderColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        cursorColor: Color = BbangZipTheme.color.labelStrong_463D34,
        underLineColor: Color = BbangZipTheme.color.primaryNormal_897869,
    ): TextFieldColors = TextFieldColors(
        focusedTextColor = focusedTextColor,
        unfocusedTextColor = unfocusedTextColor,
        focusedContainerColor = focusedContainerColor,
        unfocusedContainerColor = unfocusedContainerColor,
        focusedPlaceholderColor = focusedPlaceholderColor,
        unfocusedPlaceholderColor = unfocusedPlaceholderColor,
        cursorColor = cursorColor,
        borderColor = Color.Transparent,
        underLineColor = underLineColor
    )
}

object UnderlinedTextFieldDefaults {
    val CONTENT_PADDING_TOP = 4.dp
    val CONTENT_PADDING_BOTTOM = 0.dp
    val CONTENT_PADDING_START = 2.dp
    val CONTENT_PADDING_END = 0.dp

    fun contentPadding(
        start: Dp = CONTENT_PADDING_START,
        top: Dp = CONTENT_PADDING_TOP,
        end: Dp = CONTENT_PADDING_END,
        bottom: Dp = CONTENT_PADDING_BOTTOM
    ): PaddingValues = PaddingValues(start, top, end, bottom)

    @Composable
    fun defaultUnderLinedTextFieldColors(): TextFieldColors = TextFieldColors(
        focusedTextColor = BbangZipTheme.color.labelNormal_6B6560,
        unfocusedTextColor = BbangZipTheme.color.labelAssistive_C9C7C5,
        focusedContainerColor = BbangZipTheme.color.labelStrong_463D34,
        unfocusedContainerColor = BbangZipTheme.color.labelStrong_463D34,
        focusedPlaceholderColor = BbangZipTheme.color.labelDisable_E4E2E0,
        unfocusedPlaceholderColor = BbangZipTheme.color.labelAssistive_C9C7C5,
        cursorColor = BbangZipTheme.color.labelStrong_463D34,
        borderColor = Color.Transparent,
        underLineColor = BbangZipTheme.color.primaryNormal_897869,
    )

    @Composable
    fun defaultUnderLinedTextFieldColors(
        focusedTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        unfocusedTextColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        focusedContainerColor: Color = Color.Transparent,
        unfocusedContainerColor: Color = Color.Transparent,
        focusedPlaceholderColor: Color = BbangZipTheme.color.labelDisable_E4E2E0,
        unfocusedPlaceholderColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        cursorColor: Color = BbangZipTheme.color.labelStrong_463D34,
        borderColor: Color = Color.Transparent,
        underLineColor: Color = BbangZipTheme.color.primaryNormal_897869,
    ): TextFieldColors = TextFieldColors(
        focusedTextColor = focusedTextColor,
        unfocusedTextColor = unfocusedTextColor,
        focusedContainerColor = focusedContainerColor,
        unfocusedContainerColor = unfocusedContainerColor,
        focusedPlaceholderColor = focusedPlaceholderColor,
        unfocusedPlaceholderColor = unfocusedPlaceholderColor,
        cursorColor = cursorColor,
        borderColor = borderColor,
        underLineColor = underLineColor
    )
}

@Immutable
data class TextFieldColors(
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val focusedPlaceholderColor: Color,
    val unfocusedPlaceholderColor: Color,
    val cursorColor: Color,
    val borderColor: Color,
    val underLineColor: Color
) {
    @Stable
    fun textColor(
        isFocused: Boolean,
    ): Color = when {
        isFocused -> focusedTextColor
        else -> unfocusedTextColor
    }

    @Stable
    fun placeholderColor(
        isFocused: Boolean,
    ): Color = when {
        isFocused -> focusedPlaceholderColor
        else -> unfocusedPlaceholderColor
    }

    @Stable
    fun containerColor(
        isFocused: Boolean,
    ): Color = when {
        isFocused -> focusedContainerColor
        else -> unfocusedContainerColor
    }
}