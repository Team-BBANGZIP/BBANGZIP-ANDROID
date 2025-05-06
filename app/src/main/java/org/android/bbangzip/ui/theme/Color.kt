package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Common
private val Common100: Color = Color(0xFF121212)
private val Common0: Color = Color(0xFFFFFFFF)

// Grayscale
private val Grayscale5 = Color(0xFFFDFDFD)
private val Grayscale7 = Color(0xFFF6F6F5)
private val Grayscale10 = Color(0xFFEDECEA)
private val Grayscale20 = Color(0xFFE4E2E0)
private val Grayscale30 = Color(0xFFC9C7C5)
private val Grayscale40 = Color(0xFFB6B4B1)
private val Grayscale50 = Color(0xFFA29D96)
private val Grayscale60 = Color(0xFF9A958F)
private val Grayscale70 = Color(0xFF706A63)
private val Grayscale80 = Color(0xFF6B6560)
private val Grayscale90 = Color(0xFF463D34)

// Brown
private val Brown1 = Color(0xFFC8B5A2)
private val Brown50 = Color(0xFF897869)
private val Brown100 = Color(0xFF4B4137)

// Apricot
private val Apricot1 = Color(0xFFFAF6F3)
private val Apricot50 = Color(0xFFF6F1EE)
private val Apricot100 = Color(0xFFF2EAE4)

// Check Color
private val Todo1 = Color(0xFFEA7152)
private val Todo2 = Color(0xFFF09C86)
private val Todo3 = Color(0xFFFED45C)
private val Todo4 = Color(0xFFF6DDAF)
private val Todo5 = Color(0xFF7A946D)
private val Todo6 = Color(0xFFA2B499)
private val Todo7 = Color(0xFF5C62AC)
private val Todo8 = Color(0xFF8D91C5)
private val Todo9 = Color(0xFF8F63E9)
private val Todo10 = Color(0xFFB79FE8)

@Immutable
data class BbangZipColor(
    // Primary
    private val primaryLight_C8B5A2: Color,
    private val primaryNormal_897869: Color,
    private val primaryStrong_4B4137: Color,
    // Secondary
    private val secondaryLight_FAF6F3: Color,
    private val secondaryNormal_F6F1EE: Color,
    private val secondaryStrong_F2EAE4: Color,
    // Label
    private val labelDisable_E4E2E0: Color,
    private val labelAssistive_C9C7C5: Color,
    private val labelAlternative_A29D96: Color,
    private val labelNeutral_706A63: Color,
    private val labelNormal_6B6560: Color,
    private val labelStrong_463D34: Color,
    // Background
    private val backgroundNormal_FFFFFF: Color,
    private val backgroundGray_FDFDFD: Color,
    private val backgroundAlternative_FAF6F3: Color,
    private val backgroundStrong_F2EAE4: Color,
    private val backgroundDimmer_282119_52: Color,
    // Static
    private val staticWhite_FFFFFF: Color,
    private val staticBlack_121212: Color,
    // Component
    private val componentIvory_FDFDFD: Color,
    private val componentGrey_FFFFFF: Color,
    // To-do
    private val todoRed1_EA7152: Color,
    private val todoRed2_F09C86: Color,
    private val todoYellow1_FED45C: Color,
    private val todoYellow2_F6DDAF: Color,
    private val todoGreen1_7A946D: Color,
    private val todoGreen2_A2B499: Color,
    private val todoBlue1_5C62AC: Color,
    private val todoBlue2_8D91C5: Color,
    private val todoPurple1_8F63E9: Color,
    private val todoPurple2_B79FE8: Color,
)

val defaultBbangZipColor =
    BbangZipColor(
        // Primary
        primaryLight_C8B5A2 = Brown1,
        primaryNormal_897869 = Brown50,
        primaryStrong_4B4137 = Brown100,
        // Secondary
        secondaryLight_FAF6F3 = Apricot1,
        secondaryNormal_F6F1EE = Apricot50,
        secondaryStrong_F2EAE4 = Apricot100,
        // Label
        labelDisable_E4E2E0 = Grayscale20,
        labelAssistive_C9C7C5 = Grayscale30,
        labelAlternative_A29D96 = Grayscale50,
        labelNeutral_706A63 = Grayscale70,
        labelNormal_6B6560 = Grayscale80,
        labelStrong_463D34 = Grayscale90,
        // Background
        backgroundNormal_FFFFFF = Common0,
        backgroundGray_FDFDFD = Grayscale5,
        backgroundAlternative_FAF6F3 = Apricot1,
        backgroundStrong_F2EAE4 = Apricot100,
        backgroundDimmer_282119_52 = Grayscale90.copy(alpha = defaultBbangZipOpacity.opacity50),
        // Static
        staticWhite_FFFFFF = Common0,
        staticBlack_121212 = Common100,
        // Component
        componentIvory_FDFDFD = Grayscale5,
        componentGrey_FFFFFF = Common0,
        // To-do
        todoRed1_EA7152 = Todo1,
        todoRed2_F09C86 = Todo2,
        todoYellow1_FED45C = Todo3,
        todoYellow2_F6DDAF = Todo4,
        todoGreen1_7A946D = Todo5,
        todoGreen2_A2B499 = Todo6,
        todoBlue1_5C62AC = Todo7,
        todoBlue2_8D91C5 = Todo8,
        todoPurple1_8F63E9 = Todo9,
        todoPurple2_B79FE8 = Todo10,
    )

val LocalBbangZipColor = staticCompositionLocalOf { defaultBbangZipColor }
