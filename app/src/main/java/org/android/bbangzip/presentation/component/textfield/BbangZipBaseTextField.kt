package org.android.bbangzip.presentation.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.type.BbangZipTextFieldInputState
import org.android.bbangzip.presentation.type.getBackgroundColor
import org.android.bbangzip.presentation.type.getBorderColor
import org.android.bbangzip.presentation.type.getCursorColor
import org.android.bbangzip.presentation.type.getGuidelineColor
import org.android.bbangzip.presentation.type.getGuidelineTextStyle
import org.android.bbangzip.presentation.type.getTextColor
import org.android.bbangzip.presentation.type.getTextStyle
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import org.android.bbangzip.ui.theme.constant.ComponentConstants

/*
    기본 한줄 TextField 사용시 value , onVauleChange , TextFieldInputState , focusManager 만 넣어주면 됩니다.
    bottomSheet에서 사용시 contentPadding,contentHeight , maxCharacter를 추가로 넣어주세요
    1. characterCount 추가
    2. 아래에 선 추가
    3. enterclick 추가
    4.
 */
@Composable
fun BbangZipBaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    bbangZipTextFieldInputState: BbangZipTextFieldInputState,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    onFocusChange: (Boolean) -> Unit = {},
    onEnterClick: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    @StringRes placeholder: Int? = null,
    @StringRes guideline: Int? = null,
    contentPadding: PaddingValues = ComponentConstants.TextField.DEFAULT_CONTENT_PADDING,
    borderSize: Dp = ComponentConstants.TextField.DEFAULT_BORDER_SIZE_DP.dp,
    contentHeight: Dp? = null,
    maxCharacter: Int? = null,
    isOutLined: Boolean = false,
    keyboardOptions: KeyboardOptions =
        KeyboardOptions.Default.copy(
            imeAction = ImeAction.Default,
        ),
    keyboardActions: KeyboardActions =
        KeyboardActions(
            onDone = {
                val trimmedValue = value.trim()
                onValueChange(trimmedValue)
                focusManager.clearFocus(force = true)
            },
        ),
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val heightModifier =
        if (contentHeight != null) {
            Modifier.heightIn(min = contentHeight)
        } else {
            Modifier
        }
    BbangZipTextFieldSlot(
        columnModifier = modifier,
        rowModifier =
        Modifier
            .background(color = bbangZipTextFieldInputState.getBackgroundColor(isOutLined), shape = RoundedCornerShape(8.dp))
            .border(width = borderSize, color = bbangZipTextFieldInputState.getBorderColor(), shape = RoundedCornerShape(8.dp))
            .padding(paddingValues = contentPadding),
        leadingIcon = { leadingIcon?.invoke() },
        content = {
            BasicTextField(
                modifier =
                Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                        onFocusChange(focusState.isFocused)
                    }
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                            focusManager.clearFocus(force = true)
                            onFocusChange(false)
                            true
                        } else {
                            false
                        }
                    }
                    .then(heightModifier),
                value = value,
                onValueChange = {
                    if (maxCharacter == null || value.length <= maxCharacter) onValueChange(it)

                },
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
                textStyle = bbangZipTextFieldInputState.getTextStyle(),
                cursorBrush = SolidColor(bbangZipTextFieldInputState.getCursorColor()),
                decorationBox = { innerTextField ->
                    innerTextField()

                    if (value.isEmpty()) {
                        if (placeholder != null) {
                            Text(
                                text = stringResource(placeholder),
                                color = BbangZipTextFieldInputState.Placeholder.getTextColor(),
                                style = BbangZipTextFieldInputState.Placeholder.getTextStyle(),
                            )
                        }
                    }
                },
            )
        },
        trailingIcon = {
            trailingIcon?.invoke()
        },
        guideline = {
            if (guideline != null) {
                Text(
                    text = stringResource(id = guideline),
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                    color = bbangZipTextFieldInputState.getGuidelineColor(),
                    style = bbangZipTextFieldInputState.getGuidelineTextStyle(),
                )
            }
        },
        characterCount = {
            if (maxCharacter != null) {
                Text(
                    text = stringResource(R.string.textfield_character_counter, value.length.toString(), maxCharacter.toString()),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),
                    color = BbangZipTheme.color.labelAlternative_A29D96,
                    style = BbangZipTheme.typography.body3Medium,
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BbangZipBasicTextFieldPreview() {
    BBANGZIPANDROIDTheme {
        var text by remember { mutableStateOf("") }
        var validationState by remember { mutableStateOf<BbangZipTextFieldInputState>(BbangZipTextFieldInputState.Default) }

        var text1 by remember { mutableStateOf("") }
        var validationState1 by remember { mutableStateOf<BbangZipTextFieldInputState>(BbangZipTextFieldInputState.Default) }

        fun validateText(text: String) {
            validationState =
                when {
                    text.isEmpty() -> BbangZipTextFieldInputState.Default
                    text.length == 1 -> BbangZipTextFieldInputState.Typing
                    text.length == 3 -> BbangZipTextFieldInputState.Placeholder
                    else -> BbangZipTextFieldInputState.Field
                }
        }

        fun validateText1(text: String) {
            validationState1 =
                when {
                    text.isEmpty() -> BbangZipTextFieldInputState.Default
                    text.length == 1 -> BbangZipTextFieldInputState.Typing
                    text.length == 3 -> BbangZipTextFieldInputState.Placeholder
                    else -> BbangZipTextFieldInputState.Field
                }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFFE3FFD6)),
        ) {
            Text("나만의 다짐 작성하기")

            BbangZipBaseTextField(
                placeholder = R.string.app_name,
                modifier =
                Modifier
                    .padding(8.dp),
                value = text,
                bbangZipTextFieldInputState = validationState,
                onValueChange = { newValue ->
                    text = newValue
                    validateText(text = newValue)
                },
                onFocusChange = {
                    if (validationState == BbangZipTextFieldInputState.Default) validationState = BbangZipTextFieldInputState.Typing else Unit
                },
                focusManager = LocalFocusManager.current,
            )
            BbangZipBaseTextField(
                placeholder = R.string.app_name,
                modifier =
                Modifier
                    .padding(8.dp),
                value = text,
                bbangZipTextFieldInputState = validationState,
                onValueChange = { newValue ->
                    text = newValue
                    validateText(text = newValue)
                },
                isOutLined = true,
                contentPadding = PaddingValues(top = 4.dp, bottom = 14.dp),
                onFocusChange = {
                    if (validationState == BbangZipTextFieldInputState.Default) validationState = BbangZipTextFieldInputState.Typing else Unit
                },
                focusManager = LocalFocusManager.current,
            )

            Gap(50)

            BbangZipBaseTextField(
                placeholder = R.string.app_name,
                modifier =
                Modifier
                    .padding(8.dp),
                value = text1,
                bbangZipTextFieldInputState = validationState1,
                onValueChange = { newValue ->
                    text1 = newValue
                    validateText1(text = newValue)
                },
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                contentHeight = 90.dp,
                maxCharacter = 50,
                onFocusChange = {
                    if (validationState1 == BbangZipTextFieldInputState.Default) validationState1 = BbangZipTextFieldInputState.Typing else Unit
                },
                focusManager = LocalFocusManager.current,
            )
        }
    }
}
