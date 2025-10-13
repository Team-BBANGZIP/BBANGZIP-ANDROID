package org.android.bbangzip.presentation.ui.editcategory.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.android.bbangzip.R

enum class EditCategoryActionType(
    @DrawableRes val interactionIconResId: Int,
    @StringRes val actionName: Int,
    @StringRes val description: Int? = null
) {
    COLOR_SETTING(
        interactionIconResId = R.drawable.ic_palette_default_24,
        actionName = R.string.edit_category_color_setting_row_label
    ),
    STOP(
        interactionIconResId = R.drawable.ic_eye_default_24,
        actionName = R.string.edit_category_stop_category_row_label,
        description = R.string.edit_category_stop_category_row_description
    )
}