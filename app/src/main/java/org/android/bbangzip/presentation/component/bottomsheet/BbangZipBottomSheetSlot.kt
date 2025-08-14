package org.android.bbangzip.presentation.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BbangZipBottomSheetSlot(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    contentPadding: PaddingValues = BbangZipBottomSheetDefaults.CONTENT_PADDING,
    dragHandle: @Composable () -> Unit = { BbangZipBottomSheetDefaults.DragHandle() },
    title: @Composable (ColumnScope.() -> Unit) = {},
    content: @Composable (ColumnScope.() -> Unit) = {},
    interactRow: @Composable (ColumnScope.() -> Unit) = {},
) {
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            dragHandle = dragHandle,
            shape = BbangZipBottomSheetDefaults.BOTTOM_SHEET_SHAPE,
            containerColor = BbangZipBottomSheetDefaults.containerColor(),
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
private fun BbangZipBottomSheetSlotPreview() {
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
                    onValueChange = { newText -> text = newText },
                    modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                    ) { }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                    ) { }
                }
            },
        )
    }
}
