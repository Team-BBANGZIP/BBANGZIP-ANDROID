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
import androidx.compose.material3.HorizontalDivider
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
import org.android.bbangzip.presentation.type.getTextStyle
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

/**
 * 빵집 앱의 커스텀 텍스트 필드 컴포넌트입니다.
 *
 * 기본적으로 한 줄짜리 입력 필드를 제공합니다.
 *
 * @param value 현재 텍스트 필드의 값
 * @param onValueChange 텍스트가 변경될 때 호출되는 콜백
 * @param focusManager 포커스를 제어하기 위한 객체
 * @param modifier 전체 컴포넌트에 적용할 Modifier
 * @param onFocusChange 포커스 변경 시 호출되는 콜백
 * @param onEnterClick 엔터 키 입력 시 호출되는 콜백
 * @param trailingIcon 텍스트 필드 우측에 표시할 아이콘 슬롯
 * @param leadingIcon 텍스트 필드 좌측에 표시할 아이콘 슬롯
 * @param placeholder 입력 전 표시할 플레이스홀더 문자열 리소스 ID
 * @param guideline 하단에 표시할 가이드라인 문자열 리소스 ID
 * @param contentPadding 텍스트 필드 내부 여백 설정
 * @param borderSize 테두리 두께 설정
 * @param contentHeight 텍스트 필드 최소 높이
 * @param maxCharacter 최대 입력 가능 문자 수 (null이면 제한 없음)
 * ->   matCharacter 입력시 Character Count가 표시됩니다.
 * @param isUnderLined true일 경우 하단 Divider 선이 추가로 나타남
 * @param keyboardOptions 키보드 동작 옵션 (IME Action 등)
 * @param keyboardActions 키보드 액션 시 동작 정의
 */

@Composable
fun BbangZipBaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    onFocusChange: (Boolean) -> Unit = {},
    onEnterClick: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    @StringRes placeholder: Int? = null,
    @StringRes guideline: Int? = null,
    textStyles: TextFieldTypography = TextFieldDefaults.defaultTextFieldTypography(),
    colors: TextFieldColors = TextFieldDefaults.defaultTextFieldColors(),
    contentPadding: PaddingValues = TextFieldDefaults.contentPadding(),
    borderRadius: Dp = TextFieldDefaults.BORDER_RADIUS,
    borderSize: Dp = TextFieldDefaults.BORDER_SIZE,
    contentHeight: Dp? = null,
    maxCharacter: Int? = null,
    isUnderLined: Boolean = false,
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
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val heightModifier =
        if (contentHeight != null) {
            Modifier.heightIn(min = contentHeight)
        } else {
            Modifier
        }
    val textColor = colors.textColor(isFocused)
    val placeholderColor = colors.placeholderColor(isFocused)
    val containerColor = colors.containerColor(isFocused)
    val textStyle = textStyles.textStyle.merge(textColor)

    BbangZipTextFieldSlot(
        columnModifier = modifier,
        rowModifier =
        Modifier
            .fillMaxWidth()
            .background(color = containerColor, shape = RoundedCornerShape(borderRadius))
            .border(width = borderSize, color = colors.borderColor, shape = RoundedCornerShape(borderRadius))
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
                            onEnterClick()
                            true
                        } else {
                            false
                        }
                    }
                    .then(heightModifier),
                value = value,
                onValueChange = {
                    if (maxCharacter == null || it.length <= maxCharacter) onValueChange(it)
                },
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
                textStyle = textStyle,
                cursorBrush = SolidColor(colors.cursorColor),
                decorationBox = { innerTextField ->
                    innerTextField()

                    if (value.isEmpty()) {
                        if (placeholder != null) {
                            Text(
                                text = stringResource(placeholder),
                                color = placeholderColor,
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
                    color = colors.guideLineColor,
                    style = textStyles.guidelineTextStyle,
                )
            }
        },
        characterCount = {
            if (maxCharacter != null) {
                Text(
                    text = stringResource(R.string.textfield_character_counter, value.length.toString(), maxCharacter.toString()),
                    modifier =
                    Modifier
                        .padding(top = TextFieldDefaults.CHARACTER_COUNT_PADDING_TOP)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),
                    color = colors.characterCountColor,
                    style = textStyles.characterCountTextStyle,
                )
            }
        },
        underLine = {
            if (isUnderLined) {
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp),
                    thickness = 2.dp,
                    color = colors.underLineColor,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun BbangZipBasicTextFieldPreview() {
    BBANGZIPANDROIDTheme {
        var text by remember { mutableStateOf("") }

        var text1 by remember { mutableStateOf("") }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            Modifier
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
                onValueChange = { newValue ->
                    text = newValue
                },
                onFocusChange = {
                },
                focusManager = LocalFocusManager.current,
            )
            BbangZipBaseTextField(
                placeholder = R.string.app_name,
                modifier =
                Modifier
                    .padding(8.dp),
                value = text,
                onValueChange = { newValue ->
                    text = newValue
                },
                colors = UnderlinedTextFieldDefaults.defaultUnderLinedTextFieldColors(),
                isUnderLined = true,
                contentPadding = UnderlinedTextFieldDefaults.contentPadding(),
                onFocusChange = {
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
                onValueChange = { newValue ->
                    text1 = newValue
                },
                contentHeight = 90.dp,
                maxCharacter = 50,
                focusManager = LocalFocusManager.current,
            )
        }
    }
}
