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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

@Composable
fun BbangZipUnderLinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    onFocusChange: (Boolean) -> Unit = {},
    onEnterClick: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    @StringRes placeholder: Int? = null,
    @StringRes guideline: Int? = null,
    textStyles: TextFieldTypography = BbangZipUnderlinedTextFieldDefaults.typography(),
    colors: TextFieldColors = BbangZipUnderlinedTextFieldDefaults.colors(),
    contentPadding: PaddingValues = BbangZipUnderlinedTextFieldDefaults.CONTENT_PADDING,
    isUnderLined: Boolean = true,
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
        focusRequester = focusRequester,
        modifier = modifier,
        onFocusChange = onFocusChange,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        guideline = guideline,
        textStyles = textStyles,
        colors = colors,
        contentPadding = contentPadding,
        isUnderLined = isUnderLined,
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Preview
@Composable
private fun UnderLineTextFieldPreview() {
    BBANGZIPANDROIDTheme {
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }
        var underlinedText by remember { mutableStateOf("") }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE3FFD6))
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("✅ BbangZipUnderLinedTextField 예시")

            BbangZipUnderLinedTextField(
                value = underlinedText,
                onValueChange = { underlinedText = it },
                focusManager = focusManager,
                focusRequester = focusRequester,
                placeholder = R.string.app_name,
            )
        }
    }
}
