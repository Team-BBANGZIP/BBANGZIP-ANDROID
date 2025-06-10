package org.android.bbangzip.presentation.component.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object TextFieldDefaults {
        val DEFAULT_CONTENT_PADDING = PaddingValues(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 10.dp)
        val CHARACTER_COUNT_CONTENT_PADDING = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        val OUTLINED_CONTENT_PADDING = PaddingValues(start = 2.dp, top = 4.dp)
        const val DEFAULT_BORDER_RADIUS = 8
        const val DEFAULT_BORDER_SIZE_DP = 0
}


@Immutable
data class TextFieldColors(
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val disabledTextColor: Color,
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val disabledContainerColor: Color,
    val cursorColor: Color,
    val focusedPlaceholderColor: Color,
    val unfocusedPlaceholderColor: Color,
    val disabledPlaceholderColor: Color,
    val underLineColor: Color
){
    @Stable
    fun textColor(
        isFocused: Boolean,
    ): Color = when {
        isFocused -> focusedTextColor
        else -> unfocusedTextColor
    }

    @Stable
    fun placeholderColor(
        isFocused: Boolean,
    ): Color = when {
        isFocused -> focusedPlaceholderColor
        else -> unfocusedPlaceholderColor
    }

    @Stable
    fun containerColor(
        isFocused: Boolean,
    ): Color = when {
        isFocused -> focusedContainerColor
        else -> unfocusedContainerColor
    }
}