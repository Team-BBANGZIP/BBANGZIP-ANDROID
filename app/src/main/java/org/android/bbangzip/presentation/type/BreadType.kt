package org.android.bbangzip.presentation.type

import androidx.annotation.DrawableRes
import org.android.bbangzip.R

enum class BreadType(val id: Int, @DrawableRes val img: Int) {
    SALT(1, R.drawable.img_salt_bread);

    companion object {
        fun getImgFromId(id: Int): Int? = entries.find { it.id == id }?.img
    }
}