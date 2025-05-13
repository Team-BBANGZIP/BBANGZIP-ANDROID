package org.android.bbangzip.presentation.type

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.parcelize.Parcelize

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


fun BbangZipTextFieldInputState.getIconColor(): Color {
    return stateToTypeMap[this]?.iconColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getTextColor(): Color {
    return stateToTypeMap[this]?.textColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getCharacterCheckColor(): Color {
    return stateToTypeMap[this]?.characterCheckColor ?: Color.Transparent
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