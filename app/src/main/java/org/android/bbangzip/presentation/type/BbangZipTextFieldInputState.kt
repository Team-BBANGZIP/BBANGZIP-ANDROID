package org.android.bbangzip.presentation.type

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.ui.theme.defaultBbangZipTypography

@Parcelize
sealed class BbangZipTextFieldInputState : Parcelable {
    data object Default : BbangZipTextFieldInputState()

    data object Placeholder : BbangZipTextFieldInputState()

    data object Typing : BbangZipTextFieldInputState()

    data object Field : BbangZipTextFieldInputState()
}

private val stateToTypeMap =
    mapOf(
        BbangZipTextFieldInputState.Default to BbangZipTextFieldType.DEFAULT,
        BbangZipTextFieldInputState.Placeholder to BbangZipTextFieldType.PLACEHOLDER,
        BbangZipTextFieldInputState.Typing to BbangZipTextFieldType.TYPING,
        BbangZipTextFieldInputState.Field to BbangZipTextFieldType.FIELD,
    )

fun BbangZipTextFieldInputState.getTextColor(): Color {
    return stateToTypeMap[this]?.textColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getTextStyle(): TextStyle {
    return stateToTypeMap[this]?.textStyle ?: defaultBbangZipTypography.body1Medium
}

fun BbangZipTextFieldInputState.getBorderColor(): Color {
    return stateToTypeMap[this]?.borderColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getBackgroundColor(): Color {
    return stateToTypeMap[this]?.backgroundColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getGuidelineColor(): Color {
    return stateToTypeMap[this]?.guidelineColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getGuidelineTextStyle(): TextStyle {
    return stateToTypeMap[this]?.guidelineTextStyle ?: defaultBbangZipTypography.body1Medium
}