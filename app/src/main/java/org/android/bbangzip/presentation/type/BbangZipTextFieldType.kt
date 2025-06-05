package org.android.bbangzip.presentation.type

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import org.android.bbangzip.ui.theme.defaultBbangZipColor
import org.android.bbangzip.ui.theme.defaultBbangZipTypography

enum class BbangZipTextFieldType(
    val textColor: Color = defaultBbangZipColor.labelNormal_6B6560,
    val cursorColor : Color = defaultBbangZipColor.labelStrong_463D34,
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
