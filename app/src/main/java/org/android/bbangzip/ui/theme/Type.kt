package org.android.bbangzip.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.android.bbangzip.R

val pretendardExtraBold = FontFamily(Font(R.font.pretendard_extrabold))
val pretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold))
val pretendardMedium = FontFamily(Font(R.font.pretendard_medium))
val pretendardRegular = FontFamily(Font(R.font.pretendard_regular))

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
    val body1Bold: TextStyle,
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
        timerExtraBold =
            TextStyle(
                fontSize = 80.sp,
                fontFamily = pretendardExtraBold,
                lineHeight = 80.sp,
                letterSpacing = (-4).sp,
            ),
        // Picker
        picker1SemiBold =
            TextStyle(
                fontSize = 20.sp,
                fontFamily = pretendardSemiBold,
                lineHeight = 28.sp,
                letterSpacing = (-0.6).sp,
            ),
        picker2SemiBold =
            TextStyle(
                fontSize = 20.sp,
                fontFamily = pretendardMedium,
                lineHeight = 28.sp,
                letterSpacing = (-0.6).sp,
            ),
        // Title
        title1SemiBold =
            TextStyle(
                fontSize = 20.sp,
                fontFamily = pretendardSemiBold,
                lineHeight = 24.sp,
                letterSpacing = (-0.6).sp,
            ),
        title2Medium =
            TextStyle(
                fontSize = 18.sp,
                fontFamily = pretendardMedium,
                lineHeight = 21.6.sp,
                letterSpacing = (-0.54).sp,
            ),
        title3SemiBold =
            TextStyle(
                fontSize = 16.sp,
                fontFamily = pretendardSemiBold,
                lineHeight = 19.2.sp,
                letterSpacing = (-0.48).sp,
            ),
        // Subtitle
        subTitle1Medium =
            TextStyle(
                fontSize = 14.sp,
                fontFamily = pretendardMedium,
                lineHeight = 19.6.sp,
                letterSpacing = (-0.42).sp,
            ),
        subTitle2Regular =
            TextStyle(
                fontSize = 14.sp,
                fontFamily = pretendardExtraBold,
                lineHeight = 19.6.sp,
                letterSpacing = (-0.42).sp,
            ),
        subTitle3Medium =
            TextStyle(
                fontSize = 12.sp,
                fontFamily = pretendardMedium,
                lineHeight = 18.sp,
                letterSpacing = (-0.36).sp,
            ),
        // Body
        body1Medium =
            TextStyle(
                fontSize = 16.sp,
                fontFamily = pretendardMedium,
                lineHeight = 22.4.sp,
                letterSpacing = (-0.48).sp,
            ),
        body1Bold =
            TextStyle(
                fontSize = 16.sp,
                fontFamily = pretendardSemiBold,
                lineHeight = 24.sp,
                letterSpacing = (-0.57).sp,
            ),
        body2Medium =
            TextStyle(
                fontSize = 14.sp,
                fontFamily = pretendardMedium,
                lineHeight = 19.6.sp,
                letterSpacing = (-0.42).sp,
            ),
        body3Medium =
            TextStyle(
                fontSize = 13.sp,
                fontFamily = pretendardMedium,
                lineHeight = 18.2.sp,
                letterSpacing = (0.26).sp,
            ),
        body4Medium =
            TextStyle(
                fontSize = 12.sp,
                fontFamily = pretendardMedium,
                lineHeight = 16.8.sp,
                letterSpacing = (-0.36).sp,
            ),
        // Label
        label1SemiBold =
            TextStyle(
                fontSize = 14.sp,
                fontFamily = pretendardSemiBold,
                lineHeight = 16.8.sp,
                letterSpacing = (-0.42).sp,
            ),
        label2Regular =
            TextStyle(
                fontSize = 14.sp,
                fontFamily = pretendardRegular,
                lineHeight = 16.8.sp,
                letterSpacing = (-0.42).sp,
            ),
        label3SemiBold =
            TextStyle(
                fontSize = 12.sp,
                fontFamily = pretendardSemiBold,
                lineHeight = 14.4.sp,
                letterSpacing = (-0.36).sp,
            ),
        label4Regular =
            TextStyle(
                fontSize = 12.sp,
                fontFamily = pretendardRegular,
                lineHeight = 14.4.sp,
                letterSpacing = (-0.36).sp,
            ),
        label5SemiBold =
            TextStyle(
                fontSize = 10.sp,
                fontFamily = pretendardSemiBold,
                lineHeight = 14.sp,
                letterSpacing = (0.2).sp,
            ),
        label6Medium =
            TextStyle(
                fontSize = 10.sp,
                fontFamily = pretendardMedium,
                lineHeight = 14.sp,
                letterSpacing = (0.2).sp,
            ),
    )

val LocalBbangZipTypography = staticCompositionLocalOf { defaultBbangZipTypography }
