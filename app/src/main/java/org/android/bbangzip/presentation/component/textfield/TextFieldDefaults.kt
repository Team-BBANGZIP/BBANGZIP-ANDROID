package org.android.bbangzip.presentation.component.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme

object TextFieldDefaults {
    // ContentPadding values for the text field
    private val CONTENT_PADDING_TOP = 12.dp
    private val CONTENT_PADDING_BOTTOM = 10.dp
    private val CONTENT_PADDING_START = 12.dp
    private val CONTENT_PADDING_END = 12.dp

    val BORDER_RADIUS = 8.dp
    val BORDER_SIZE = 0.dp

    val CHARACTER_COUNT_PADDING_TOP = 10.dp

    @Composable
    fun contentPadding(
        start: Dp = CONTENT_PADDING_START,
        top: Dp = CONTENT_PADDING_TOP,
        end: Dp = CONTENT_PADDING_END,
        bottom: Dp = CONTENT_PADDING_BOTTOM,
    ): PaddingValues = PaddingValues(start, top, end, bottom)


    @Composable
    fun colors(
        focusedTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        unfocusedTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        focusedContainerColor: Color = BbangZipTheme.color.componentStrong_F6F6F5,
        unfocusedContainerColor: Color = BbangZipTheme.color.componentStrong_F6F6F5,
        focusedPlaceholderColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        unfocusedPlaceholderColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        characterCountColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        cursorColor: Color = BbangZipTheme.color.labelStrong_463D34,
        borderColor: Color = Color.Transparent,
        underLineColor: Color = BbangZipTheme.color.primaryNormal_897869,
        guideLineColor: Color = Color.Transparent,
    ): TextFieldColors =
        remember(
            focusedTextColor,
            unfocusedTextColor,
            focusedContainerColor,
            unfocusedContainerColor,
            focusedPlaceholderColor,
            unfocusedPlaceholderColor,
            characterCountColor,
            cursorColor,
            borderColor,
            underLineColor,
            guideLineColor,
        ) {
            TextFieldColors(
                focusedTextColor = focusedTextColor,
                unfocusedTextColor = unfocusedTextColor,
                focusedContainerColor = focusedContainerColor,
                unfocusedContainerColor = unfocusedContainerColor,
                focusedPlaceholderColor = focusedPlaceholderColor,
                unfocusedPlaceholderColor = unfocusedPlaceholderColor,
                characterCountColor = characterCountColor,
                cursorColor = cursorColor,
                borderColor = borderColor,
                underLineColor = underLineColor,
                guideLineColor = guideLineColor,
            )
        }

    @Composable
    fun typography(
        textStyle: TextStyle = BbangZipTheme.typography.body1Medium,
        guidelineTextStyle: TextStyle = BbangZipTheme.typography.body1Medium,
        characterCountTextStyle: TextStyle = BbangZipTheme.typography.body3Medium,
    ): TextFieldTypography =
        remember(
            textStyle,
            guidelineTextStyle,
            characterCountTextStyle,
        ) {
            TextFieldTypography(
                textStyle = textStyle,
                guidelineTextStyle = guidelineTextStyle,
                characterCountTextStyle = characterCountTextStyle,
            )
        }
}

object UnderlinedTextFieldDefaults {
    private val CONTENT_PADDING_TOP = 4.dp
    private val CONTENT_PADDING_BOTTOM = 0.dp
    private val CONTENT_PADDING_START = 2.dp
    private val CONTENT_PADDING_END = 0.dp

    fun contentPadding(
        start: Dp = CONTENT_PADDING_START,
        top: Dp = CONTENT_PADDING_TOP,
        end: Dp = CONTENT_PADDING_END,
        bottom: Dp = CONTENT_PADDING_BOTTOM,
    ): PaddingValues = PaddingValues(start, top, end, bottom)

    @Composable
    fun colors(
        focusedTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        unfocusedTextColor: Color = BbangZipTheme.color.labelNormal_6B6560,
        focusedContainerColor: Color = Color.Transparent,
        unfocusedContainerColor: Color = Color.Transparent,
        focusedPlaceholderColor: Color = BbangZipTheme.color.labelDisable_E4E2E0,
        unfocusedPlaceholderColor: Color = BbangZipTheme.color.labelAssistive_C9C7C5,
        characterCountColor: Color = BbangZipTheme.color.labelAlternative_A29D96,
        cursorColor: Color = BbangZipTheme.color.labelStrong_463D34,
        borderColor: Color = Color.Transparent,
        underLineColor: Color = BbangZipTheme.color.primaryNormal_897869,
        guideLineColor: Color = Color.Transparent,
    ): TextFieldColors =
        remember(
            focusedTextColor,
            unfocusedTextColor,
            focusedContainerColor,
            unfocusedContainerColor,
            focusedPlaceholderColor,
            unfocusedPlaceholderColor,
            characterCountColor,
            cursorColor,
            borderColor,
            underLineColor,
            guideLineColor,
        ) {
            TextFieldColors(
                focusedTextColor = focusedTextColor,
                unfocusedTextColor = unfocusedTextColor,
                focusedContainerColor = focusedContainerColor,
                unfocusedContainerColor = unfocusedContainerColor,
                focusedPlaceholderColor = focusedPlaceholderColor,
                unfocusedPlaceholderColor = unfocusedPlaceholderColor,
                characterCountColor = characterCountColor,
                cursorColor = cursorColor,
                borderColor = borderColor,
                underLineColor = underLineColor,
                guideLineColor = guideLineColor,
            )
        }

    @Composable
    fun typography(
        textStyle: TextStyle = BbangZipTheme.typography.body1Medium,
        guidelineTextStyle: TextStyle = BbangZipTheme.typography.body1Medium,
        characterCountTextStyle: TextStyle = BbangZipTheme.typography.body3Medium,
    ): TextFieldTypography =
        remember(
            textStyle,
            guidelineTextStyle,
            characterCountTextStyle,
        ) {
            TextFieldTypography(
                textStyle = textStyle,
                guidelineTextStyle = guidelineTextStyle,
                characterCountTextStyle = characterCountTextStyle,
            )
        }
}

@Immutable
data class TextFieldColors(
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val focusedPlaceholderColor: Color,
    val unfocusedPlaceholderColor: Color,
    val characterCountColor: Color,
    val cursorColor: Color,
    val borderColor: Color,
    val underLineColor: Color,
    val guideLineColor: Color,
) {
    @Stable
    fun textColor(
        isFocused: Boolean,
    ): Color =
        when {
            isFocused -> focusedTextColor
            else -> unfocusedTextColor
        }

    @Stable
    fun placeholderColor(
        isFocused: Boolean,
    ): Color =
        when {
            isFocused -> focusedPlaceholderColor
            else -> unfocusedPlaceholderColor
        }

    @Stable
    fun containerColor(
        isFocused: Boolean,
    ): Color =
        when {
            isFocused -> focusedContainerColor
            else -> unfocusedContainerColor
        }
}

@Immutable
data class TextFieldTypography(
    val textStyle: TextStyle,
    val guidelineTextStyle: TextStyle,
    val characterCountTextStyle: TextStyle,
)
