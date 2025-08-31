package org.android.bbangzip.presentation.model

import androidx.compose.ui.graphics.Color
import org.android.bbangzip.ui.theme.defaultBbangZipColor

enum class CategoryColor(val color: Color) {
    RED1(defaultBbangZipColor.todoRed1_EA7152),
    RED2(defaultBbangZipColor.todoRed2_F09C86),
    YELLOW1(defaultBbangZipColor.todoYellow1_FED45C),
    YELLOW2(defaultBbangZipColor.todoYellow2_F6DDAF),
    GREEN1(defaultBbangZipColor.todoGreen1_7A946D),
    GREEN2(defaultBbangZipColor.todoGreen2_A2B499),
    BLUE1(defaultBbangZipColor.todoBlue1_5C62AC),
    BLUE2(defaultBbangZipColor.todoBlue2_8D91C5),
    PURPLE1(defaultBbangZipColor.todoPurple1_8F63E9),
    PURPLE2(defaultBbangZipColor.todoPurple2_B79FE8),
    ;

    companion object {
        private val colorMap: Map<String, CategoryColor> = CategoryColor.entries.associateBy { it.color.toString() }

        fun fromString(colorString: String): CategoryColor {
            return colorMap[colorString] ?: RED1
        }
    }
}
