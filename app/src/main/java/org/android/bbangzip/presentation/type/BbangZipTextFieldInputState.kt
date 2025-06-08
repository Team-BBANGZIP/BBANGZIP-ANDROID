package org.android.bbangzip.presentation.type

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.ui.theme.defaultBbangZipColor
import org.android.bbangzip.ui.theme.defaultBbangZipTypography

@Parcelize
sealed class BbangZipTextFieldInputState : Parcelable {
    data object Default : BbangZipTextFieldInputState()

    data object Placeholder : BbangZipTextFieldInputState()

    data object Typing : BbangZipTextFieldInputState()

    data object Field : BbangZipTextFieldInputState()
}

enum class BbangZipTextFieldType(
    val textColor: Color = defaultBbangZipColor.labelNormal_6B6560,
    val cursorColor: Color = defaultBbangZipColor.labelStrong_463D34,
    val textStyle: TextStyle = defaultBbangZipTypography.body1Medium,
    val borderColor: Color = Color.Transparent,
    val backgroundColor: Color = defaultBbangZipColor.componentStrong_F6F6F5,
    val guidelineColor: Color = Color.Transparent,
    val guidelineTextStyle: TextStyle = defaultBbangZipTypography.body1Medium,
) {
    DEFAULT(
        textColor = defaultBbangZipColor.labelAssistive_C9C7C5,
    ),
    PLACEHOLDER(
        textColor = defaultBbangZipColor.labelAssistive_C9C7C5,
    ),
    TYPING(
        textColor = defaultBbangZipColor.labelNormal_6B6560,
    ),
    FIELD(
        textColor = defaultBbangZipColor.labelNormal_6B6560,
    ),
}

private val stateToTypeMap =
    mapOf(
        BbangZipTextFieldInputState.Default to BbangZipTextFieldType.DEFAULT,
        BbangZipTextFieldInputState.Placeholder to BbangZipTextFieldType.PLACEHOLDER,
        BbangZipTextFieldInputState.Typing to BbangZipTextFieldType.TYPING,
        BbangZipTextFieldInputState.Field to BbangZipTextFieldType.FIELD,
    )

fun BbangZipTextFieldInputState.getCursorColor(): Color {
    return stateToTypeMap[this]?.cursorColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getTextColor(): Color {
    return stateToTypeMap[this]?.textColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getTextStyle(): TextStyle {
    return stateToTypeMap[this]?.textStyle?.merge(TextStyle(color = stateToTypeMap[this]!!.textColor)) ?: defaultBbangZipTypography.body1Medium
}

fun BbangZipTextFieldInputState.getBorderColor(): Color {
    return stateToTypeMap[this]?.borderColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getBackgroundColor(isOutLined: Boolean): Color {
    return if (!isOutLined) {
        stateToTypeMap[this]?.backgroundColor ?: Color.Transparent
    } else {
        Color.Transparent
    }
}

fun BbangZipTextFieldInputState.getGuidelineColor(): Color {
    return stateToTypeMap[this]?.guidelineColor ?: Color.Transparent
}

fun BbangZipTextFieldInputState.getGuidelineTextStyle(): TextStyle {
    return stateToTypeMap[this]?.guidelineTextStyle ?: defaultBbangZipTypography.body1Medium
}
