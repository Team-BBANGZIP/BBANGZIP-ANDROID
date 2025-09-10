package org.android.bbangzip.presentation.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.cache.RegexCaches

/**
 * 빵집 앱의 커스텀 텍스트 필드 컴포넌트입니다.
 *
 * 기본적으로 한 줄 입력 필드를 제공하며.
 *
 * @param value 현재 텍스트 필드에 입력된 값
 * @param onValueChange 텍스트가 변경될 때 호출되는 콜백 함수
 * @param focusManager 포커스를 수동으로 제어할 수 있는 Compose의 FocusManager
 * @param focusRequester 포커스 요청을 위한 FocusRequester
 * @param modifier 텍스트 필드 전체에 적용할 Modifier
 * @param onFocusChange 포커스 상태가 변경될 때 호출되는 콜백 (true = 포커스됨)
 * @param onEnterClick 키보드의 엔터 키 입력 시 호출되는 콜백 (기본 동작: 텍스트 트리밍 + 포커스 해제)
 * @param trailingIcon 텍스트 필드 우측에 표시할 컴포저블 슬롯 (아이콘 등)
 * @param leadingIcon 텍스트 필드 좌측에 표시할 컴포저블 슬롯 (아이콘 등)
 * @param placeholder 입력 전 표시할 플레이스홀더 텍스트의 문자열 리소스 ID (nullable)
 * @param guideline 텍스트 필드 하단에 표시할 설명 텍스트의 문자열 리소스 ID (nullable)
 * @param textStyles 텍스트, 플레이스홀더, 가이드라인, 카운터 등의 글꼴 스타일을 담은 클래스
 * @param colors 포커스 여부에 따른 텍스트, 배경, 테두리, 플레이스홀더 등의 색상을 정의한 클래스
 * @param contentPadding 텍스트 필드 내부의 패딩 값 (기본값 제공)
 * @param borderRadius 텍스트 필드 외곽의 둥근 정도를 설정 (default: 8.dp 등)
 * @param borderSize 텍스트 필드 외곽 테두리의 두께
 * @param maxLines 최대 줄 수
 * @param maxCharacter 입력 가능한 최대 문자 수 (null이면 제한 없음, 설정 시 글자 수 카운터 표시됨)
 * @param isUnderLined true일 경우 텍스트 필드 하단에 밑줄 Divider 표시
 * @param keyboardOptions 키보드 동작 방식(IMeAction 등)을 정의하는 옵션 객체
 * @param keyboardActions 키보드 입력 이벤트에 반응하는 액션 정의 (예: onDone → 포커스 해제 등)
 */

@Composable
fun BbangZipBaseTextField(
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
    textStyles: TextFieldTypography = BbangZipTextFieldDefaults.typography(),
    colors: TextFieldColors = BbangZipTextFieldDefaults.colors(),
    contentPadding: PaddingValues = BbangZipTextFieldDefaults.CONTENT_PADDING,
    borderRadius: Dp = BbangZipTextFieldDefaults.BORDER_RADIUS,
    borderSize: Dp = BbangZipTextFieldDefaults.BORDER_SIZE,
    maxLines: Int = 1,
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
    val aspectRatio = 336f / 90f
    val heightModifier =
        if (maxCharacter != null) {
            Modifier
                .aspectRatio(aspectRatio)
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
                    val filtered = it.replace(RegexCaches.EMOJI_AND_UNASSIGNED_REGEX, "")
                    if (maxCharacter == null || it.length <= maxCharacter) onValueChange(filtered)
                },
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
                textStyle = textStyle,
                maxLines = maxLines,
                cursorBrush = SolidColor(colors.cursorColor),
                decorationBox = { innerTextField ->
                    innerTextField()

                    if (value.isEmpty()) {
                        if (placeholder != null) {
                            Text(
                                modifier = Modifier.padding(start = 1.dp),
                                text = stringResource(placeholder),
                                color = placeholderColor,
                                style = textStyles.textStyle,
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
                            .padding(top = BbangZipTextFieldDefaults.CHARACTER_COUNT_PADDING_TOP)
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
