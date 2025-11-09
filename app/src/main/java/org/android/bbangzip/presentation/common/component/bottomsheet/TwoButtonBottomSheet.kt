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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoButtonBottomSheet(
    title: String,
    description: String,
    completeBtnTitle: String,
    cancelBtnTitle: String,
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                style = BbangZipTheme.typography.title3SemiBold,
                color = BbangZipTheme.color.labelAlternative_A29D96,
            )

            Gap(height = 25.dp)
        },
        content = {
            Text(
                text = description
            )

            BbangzipBaseButton(
                onClick = onCompleteClick,
                colors = BbangZipButtonDefaults.colors(
                    enabledContentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    enabledContainerColor = BbangZipTheme.color.todoBlue1_5C62AC
                ),
                content = {
                    Text(
                        text = completeBtnTitle
                    )
                }
            )

            BbangzipBaseButton(
                onClick = onCancelClick,
                colors = BbangZipButtonDefaults.colors(
                    enabledContentColor = BbangZipTheme.color.staticBlack_121212,
                    enabledContainerColor = BbangZipTheme.color.labelAssistive_C9C7C5
                ),
                content = {
                    Text(
                        text = cancelBtnTitle
                    )
                }
            )
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TwoButtonBottomSheetPreview() {
    var isBottomSheetVisible by remember { mutableStateOf(true) }

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
                onClick = { isBottomSheetVisible = true },
            ) {
                Text("바텀시트 띄우기")
            }
            Gap(100.dp)
        }

        TwoButtonBottomSheet(
            title = "정말 로그아웃?",
            description = "로그아웃하면\n다시 로그인 해야 돼잉",
            completeBtnTitle = "로그아웃하기",
            cancelBtnTitle = "취소",
            onDismissRequest = { isBottomSheetVisible = false },
            isBottomSheetVisible = isBottomSheetVisible,
            onCompleteClick = { isBottomSheetVisible = false },
            onCancelClick = { isBottomSheetVisible = false }
        )
    }
}
