package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.android.bbangzip.R

val PretendardExtraBold = FontFamily(Font(R.font.pretendard_extrabold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular))

@Immutable
data class BbangZipTypography(
    // Timer
    val timerExtraBold: TextStyle,

    // Picker
    val picker1SemiBold: TextStyle,
    val picker2SemiBold: TextStyle,

    // Title
    val title1SemiBold: TextStyle,
    val title2Medium: TextStyle,
    val title3SemiBold: TextStyle,

    // Subtitle
    val subTitle1Medium: TextStyle,
    val subTitle2Regular: TextStyle,
    val subTitle3Medium: TextStyle,

    // Body
    val body1Medium: TextStyle,
    val body2Medium: TextStyle,
    val body3Medium: TextStyle,
    val body4Medium: TextStyle,

    // Label
    val label1SemiBold: TextStyle,
    val label2Regular: TextStyle,
    val label3SemiBold: TextStyle,
    val label4Regular: TextStyle,
    val label5SemiBold: TextStyle,
    val label6Medium: TextStyle,
)

val defaultBbangZipTypography =
    BbangZipTypography(
        // Timer
        timerExtraBold = TextStyle(
            fontSize = 80.sp,
            fontFamily = PretendardExtraBold,
            lineHeight = 80.sp,
            letterSpacing = (-4).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),

        // Picker
        picker1SemiBold = TextStyle(
            fontSize = 20.sp,
            fontFamily = PretendardSemiBold,
            lineHeight = 28.sp,
            letterSpacing = (-0.6).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        picker2SemiBold = TextStyle(
            fontSize = 20.sp,
            fontFamily = PretendardMedium,
            lineHeight = 28.sp,
            letterSpacing = (-0.6).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),

        // Title
        title1SemiBold = TextStyle(
            fontSize = 20.sp,
            fontFamily = PretendardSemiBold,
            lineHeight = 24.sp,
            letterSpacing = (-0.6).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        title2Medium = TextStyle(
            fontSize = 18.sp,
            fontFamily = PretendardMedium,
            lineHeight = 21.6.sp,
            letterSpacing = (-0.54).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        title3SemiBold = TextStyle(
            fontSize = 16.sp,
            fontFamily = PretendardSemiBold,
            lineHeight = 19.2.sp,
            letterSpacing = (-0.48).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),

        // Subtitle
        subTitle1Medium = TextStyle(
            fontSize = 14.sp,
            fontFamily = PretendardMedium,
            lineHeight = 19.6.sp,
            letterSpacing = (-0.42).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        subTitle2Regular = TextStyle(
            fontSize = 14.sp,
            fontFamily = PretendardExtraBold,
            lineHeight = 19.6.sp,
            letterSpacing = (-0.42).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        subTitle3Medium = TextStyle(
            fontSize = 12.sp,
            fontFamily = PretendardMedium,
            lineHeight = 18.sp,
            letterSpacing = (-0.36).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),

        // Body
        body1Medium = TextStyle(
            fontSize = 16.sp,
            fontFamily = PretendardMedium,
            lineHeight = 22.4.sp,
            letterSpacing = (-0.48).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        body2Medium = TextStyle(
            fontSize = 14.sp,
            fontFamily = PretendardMedium,
            lineHeight = 19.6.sp,
            letterSpacing = (-0.42).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        body3Medium = TextStyle(
            fontSize = 13.sp,
            fontFamily = PretendardMedium,
            lineHeight = 18.2.sp,
            letterSpacing = (0.26).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        body4Medium = TextStyle(
            fontSize = 12.sp,
            fontFamily = PretendardMedium,
            lineHeight = 16.8.sp,
            letterSpacing = (-0.36).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),

        // Label
        label1SemiBold = TextStyle(
            fontSize = 14.sp,
            fontFamily = PretendardSemiBold,
            lineHeight = 16.8.sp,
            letterSpacing = (-0.42).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        label2Regular = TextStyle(
            fontSize = 14.sp,
            fontFamily = PretendardRegular,
            lineHeight = 16.8.sp,
            letterSpacing = (-0.42).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        label3SemiBold = TextStyle(
            fontSize = 12.sp,
            fontFamily = PretendardSemiBold,
            lineHeight = 14.4.sp,
            letterSpacing = (-0.36).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        label4Regular = TextStyle(
            fontSize = 12.sp,
            fontFamily = PretendardRegular,
            lineHeight = 14.4.sp,
            letterSpacing = (-0.36).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        label5SemiBold = TextStyle(
            fontSize = 10.sp,
            fontFamily = PretendardSemiBold,
            lineHeight = 14.sp,
            letterSpacing = (0.2).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        label6Medium = TextStyle(
            fontSize = 10.sp,
            fontFamily = PretendardMedium,
            lineHeight = 14.sp,
            letterSpacing = (0.2).sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )

val LocalBbangZipTypography = staticCompositionLocalOf { defaultBbangZipTypography }
