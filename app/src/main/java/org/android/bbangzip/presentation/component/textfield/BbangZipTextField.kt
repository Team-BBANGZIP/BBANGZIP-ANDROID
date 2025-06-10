package org.android.bbangzip.presentation.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

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

@Preview
@Composable
private fun TextFieldPreview() {

    BBANGZIPANDROIDTheme {
        val focusManager = LocalFocusManager.current

        var text by remember { mutableStateOf("") }
        var textWithLimit by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE3FFD6))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("✅ BbangZipTextField 예시")

            BbangZipTextField(
                value = text,
                onValueChange = { text = it },
                focusManager = focusManager,
                placeholder = R.string.app_name
            )

            Text("✅ Character Count 텍스트 필드")

            BbangZipTextField(
                value = textWithLimit,
                onValueChange = { textWithLimit = it },
                focusManager = focusManager,
                placeholder = R.string.app_name,
                contentHeight = 90.dp,
                maxCharacter = 50
            )
        }
    }
}