package org.android.bbangzip.presentation.ui.addcategory.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.android.bbangzip.R

enum class AddCategoryActionType(
    @DrawableRes val interactionIconResId: Int,
    @StringRes val actionName: Int,
) {
    COLOR_SETTING(
        interactionIconResId = R.drawable.ic_palette_default_24,
        actionName = R.string.edit_category_color_setting_row_label,
    ),
}
