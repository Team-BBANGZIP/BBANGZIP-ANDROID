package org.android.bbangzip.presentation.common.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.android.bbangzip.ui.theme.BbangZipTheme

object BbangZipBottomSheetDefaults {
    // dragHandle 관련 상수
    val DRAG_HANDLE_WIDTH = 60.dp
    val DRAG_HANDLE_HEIGHT = 5.dp
    val DRAG_HANDLE_TOP_PADDING = 6.dp
    val DRAG_HANDLE_CORNER_RADIUS = 4.dp

    // 바텀시트 모양 관련 상수
    val BOTTOM_SHEET_CORNER_RADIUS = 48.dp

    // 컨텐츠 패딩 관련 상수
    val CONTENT_PADDING_TOP = 25.dp
    val CONTENT_PADDING_HORIZONTAL = 20.dp
    val CONTENT_PADDING_BOTTOM = 12.dp

    // Shape 객체
    val DRAG_HANDLE_SHAPE = RoundedCornerShape(DRAG_HANDLE_CORNER_RADIUS)
    val BOTTOM_SHEET_SHAPE =
        RoundedCornerShape(
            topStart = BOTTOM_SHEET_CORNER_RADIUS,
            topEnd = BOTTOM_SHEET_CORNER_RADIUS,
        )

    // PaddingValues 객체
    val CONTENT_PADDING =
        PaddingValues(
            top = CONTENT_PADDING_TOP,
            start = CONTENT_PADDING_HORIZONTAL,
            end = CONTENT_PADDING_HORIZONTAL,
            bottom = CONTENT_PADDING_BOTTOM,
        )

    // 색상
    @Composable
    fun containerColor() = BbangZipTheme.color.backgroundNormal_FFFFFF

    @Composable
    fun dragHandleColor() = BbangZipTheme.color.labelDisable_E4E2E0

    @Composable
    fun DragHandle() =
        Box(
            modifier =
                Modifier
                    .padding(top = DRAG_HANDLE_TOP_PADDING)
                    .width(DRAG_HANDLE_WIDTH)
                    .height(DRAG_HANDLE_HEIGHT)
                    .clip(shape = DRAG_HANDLE_SHAPE)
                    .background(color = dragHandleColor()),
        )
}
