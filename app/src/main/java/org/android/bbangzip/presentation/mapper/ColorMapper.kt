package org.android.bbangzip.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun colorMapper(color: String): Color =
    when (color) {
        "BbangZipTheme.color.todoRed1_EA7152" -> BbangZipTheme.color.todoRed1_EA7152
        "BbangZipTheme.color.todoRed2_F09C86" -> BbangZipTheme.color.todoRed2_F09C86
        "BbangZipTheme.color.todoYellow1_FED45C" -> BbangZipTheme.color.todoYellow1_FED45C
        "BbangZipTheme.color.todoYellow2_F6DDAF" -> BbangZipTheme.color.todoYellow2_F6DDAF
        "BbangZipTheme.color.todoGreen1_7A946D" -> BbangZipTheme.color.todoGreen1_7A946D
        "BbangZipTheme.color.todoGreen2_A2B499" -> BbangZipTheme.color.todoGreen2_A2B499
        "BbangZipTheme.color.todoBlue1_5C62AC" -> BbangZipTheme.color.todoBlue1_5C62AC
        "BbangZipTheme.color.todoBlue2_8D91C5" -> BbangZipTheme.color.todoBlue2_8D91C5
        "BbangZipTheme.color.todoPurple1_8F63E9" -> BbangZipTheme.color.todoPurple1_8F63E9
        "BbangZipTheme.color.todoPurple2_B79FE8" -> BbangZipTheme.color.todoPurple2_B79FE8
        else -> BbangZipTheme.color.todoRed1_EA7152
    }
