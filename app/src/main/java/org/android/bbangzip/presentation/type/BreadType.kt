package org.android.bbangzip.presentation.type

import androidx.annotation.DrawableRes
import org.android.bbangzip.R

enum class BreadType(val id: Int, @DrawableRes val img: Int) {
    SALT(1, R.drawable.img_salt_bread),
    DUMMY_2(2, R.drawable.img_salt_bread),
    DUMMY_3(3, R.drawable.img_salt_bread),
    DUMMY_4(4, R.drawable.img_salt_bread),
    DUMMY_5(5, R.drawable.img_salt_bread),
    DUMMY_6(6, R.drawable.img_salt_bread),
    DUMMY_7(7, R.drawable.img_salt_bread),
    DUMMY_8(8, R.drawable.img_salt_bread),
    DUMMY_9(9, R.drawable.img_salt_bread);

    companion object {
        fun getImgFromId(id: Int): Int = entries.find { it.id == id }?.img ?: R.drawable.img_salt_bread
    }
}