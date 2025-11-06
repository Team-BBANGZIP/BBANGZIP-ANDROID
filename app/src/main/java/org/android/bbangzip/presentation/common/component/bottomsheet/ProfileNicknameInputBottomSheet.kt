package org.android.bbangzip.presentation.common.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.textfield.BbangZipTextField
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileNicknameInputBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    nickname: String,
    focusManager: FocusManager,
    onNicknameChange: (String) -> Unit,
    onDoneAction: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.profile_nickname_input_title),
                style = BbangZipTheme.typography.title3SemiBold,
                color = BbangZipTheme.color.labelAlternative_A29D96,
            )

            Gap(height = 31.dp)
        },
        content = {
            BbangZipTextField(
                value = nickname,
                onValueChange = onNicknameChange,
                focusManager = focusManager,
                focusRequester = focusRequester,
                placeholder = R.string.onboarding_name_description,
                onEnterClick = onDoneAction,
                maxCharacter = 20
            )
        }
    )
}

@Preview
@Composable
fun ProfileNicknameInputBottomSheetPreview() {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.Blue,
                    ),
                onClick = { isBottomSheetVisible = !isBottomSheetVisible },
            ) {
                Text("바텀시트 띄우기")
            }
            ProfileNicknameInputBottomSheet(
                isBottomSheetVisible = isBottomSheetVisible,
                onDismissRequest = { isBottomSheetVisible = false },
                focusManager = focusManager,
                nickname = text,
                onNicknameChange = { text = it },
                onDoneAction = { isBottomSheetVisible = false },
            )
        }
    }
}