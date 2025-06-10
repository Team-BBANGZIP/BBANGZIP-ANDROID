package org.android.bbangzip.presentation.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp

@Composable
fun BbangZipTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    onFocusChange: (Boolean) -> Unit = {},
    onEnterClick: () -> Unit = {},
    contentHeight: Dp? = null,
    maxCharacter: Int? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    @StringRes placeholder: Int? = null,
    @StringRes guideline: Int? = null,
    textStyles: TextFieldTypography = TextFieldDefaults.defaultTextFieldTypography(),
    colors: TextFieldColors = TextFieldDefaults.defaultTextFieldColors(),
    contentPadding: PaddingValues = TextFieldDefaults.contentPadding(),
    borderRadius: Dp = TextFieldDefaults.BORDER_RADIUS,
    borderSize: Dp = TextFieldDefaults.BORDER_SIZE,
    keyboardOptions: KeyboardOptions =
        KeyboardOptions.Default.copy(
            imeAction = ImeAction.Default,
        ),
    keyboardActions: KeyboardActions =
        KeyboardActions(
            onDone = {
                val trimmedValue = value.trim()
                onValueChange(trimmedValue)
                onEnterClick()
                focusManager.clearFocus(force = true)
            },
        ),
) {
    BbangZipBaseTextField(
        value = value,
        onValueChange = onValueChange,
        focusManager = focusManager,
        modifier = modifier,
        onFocusChange = onFocusChange,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        guideline = guideline,
        textStyles = textStyles,
        colors = colors,
        contentPadding = contentPadding,
        borderRadius = borderRadius,
        borderSize = borderSize,
        contentHeight = contentHeight,
        maxCharacter = maxCharacter,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}