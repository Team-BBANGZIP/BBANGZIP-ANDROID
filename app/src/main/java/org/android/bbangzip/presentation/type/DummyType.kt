package org.android.bbangzip.presentation.type

import androidx.annotation.StringRes
import org.android.bbangzip.R

enum class DummyType(
    @StringRes val title: Int,
) {
    FIRST(
        title = R.string.app_name
    ),
    SECOND(
        title = R.string.app_name
    ),
    THIRD(
        title = R.string.app_name
    ),
}
