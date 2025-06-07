package org.android.bbangzip.presentation.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BbangZipBottomSheetSlot(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    contentPadding: PaddingValues = PaddingValues(top = 25.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
    title: @Composable (ColumnScope.() -> Unit) = {},
    content: @Composable (ColumnScope.() -> Unit) = {},
    interactRow: @Composable (ColumnScope.() -> Unit) = {},
) {
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            dragHandle = {
                    Box(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .width(60.dp)
                            .height(5.dp)
                            .background(
                                color = BbangZipTheme.color.labelDisable_E4E2E0,
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                         },
            shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
            containerColor = BbangZipTheme.color.backgroundNormal_FFFFFF,
        ) {
            Column(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                title()
                content()
                interactRow()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BbangZipBottomSheetPreview() {
    BBANGZIPANDROIDTheme {
        var isBottomSheetVisible by remember { mutableStateOf(true) }
        var isBottomSheetVisible2 by remember { mutableStateOf(false) }

        var text by remember { mutableStateOf("") }

        BbangZipBottomSheetSlot(
            isBottomSheetVisible = isBottomSheetVisible,
            onDismissRequest = {
                isBottomSheetVisible = !isBottomSheetVisible
                isBottomSheetVisible2 = !isBottomSheetVisible2
                               },
            title = {
                Text(
                    text = "할 일 추가",
                )
            },
            content = {
                TextField(
                    value = text,
                    onValueChange = {newText -> text = newText},
                    modifier = Modifier.fillMaxWidth()
                )
            },
        )
        BbangZipBottomSheetSlot(
            isBottomSheetVisible = isBottomSheetVisible2,
            onDismissRequest = {
                isBottomSheetVisible = !isBottomSheetVisible
                isBottomSheetVisible2 = !isBottomSheetVisible2
            },
            title = {
                Text(
                    text = "정말 삭제 하시겠어요?",
                )
            },
            content = {
                Text(
                    text = "정말 삭제 하시겠어요?",
                )
            },
            interactRow = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    ) { }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    ) { }
                }
            }
        )
    }
}

