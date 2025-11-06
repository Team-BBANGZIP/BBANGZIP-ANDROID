package org.android.bbangzip.presentation.common.util.constant

import androidx.annotation.DrawableRes
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.defaultBbangZipColor

object OnboardingConstants {
    @DrawableRes
    val DEFAULT_PROFILE_IMG_RES_ID = R.drawable.ic_profile_default_100

    @DrawableRes
    val PROFILE_IMG_RES_IDS =
        listOf(
            R.drawable.ic_profile_illust1_100,
            R.drawable.ic_profile_illust2_100,
            R.drawable.ic_profile_illust3_100,
            R.drawable.ic_profile_illust4_100,
            R.drawable.ic_profile_illust5_100,
            R.drawable.ic_profile_illust6_100,
        )

    val PROFILE_IMG_OUTLINE_COLORS =
        mapOf(
            R.drawable.ic_profile_illust1_100 to defaultBbangZipColor.todoRed1_EA7152,
            R.drawable.ic_profile_illust2_100 to defaultBbangZipColor.todoYellow1_FED45C,
            R.drawable.ic_profile_illust3_100 to defaultBbangZipColor.todoPurple2_B79FE8,
            R.drawable.ic_profile_illust4_100 to defaultBbangZipColor.todoBlue1_5C62AC,
            R.drawable.ic_profile_illust5_100 to defaultBbangZipColor.todoGreen1_7A946D,
            R.drawable.ic_profile_illust6_100 to defaultBbangZipColor.todoRed2_F09C86,
        )

    val DEFAULT_OUTLINE_COLOR = defaultBbangZipColor.staticWhite_FFFFFF
}
