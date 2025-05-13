package org.android.bbangzip.presentation.type

import androidx.compose.ui.graphics.Color
import org.android.bbangzip.ui.theme.defaultBbangZipColor

enum class BbangZipTextFieldType(
    val iconColor: Color = defaultBbangZipColor.labelStrong_463D34,
    val textColor: Color = defaultBbangZipColor.labelNormal_6B6560,
    val characterCheckColor: Color = defaultBbangZipColor.labelAlternative_A29D96,
    val borderColor: Color = Color.Transparent,
    val backgroundColor: Color = defaultBbangZipColor.componentStrong_F6F6F5,
    val guidelineColor: Color = Color.Transparent,
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

