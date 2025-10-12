package org.android.bbangzip.presentation.common.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.type.CategoryColor
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryColorPickerBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onColorSelected: (String) -> Unit,
){
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.category_color_picker_title),
                style = BbangZipTheme.typography.title3SemiBold,
                color = BbangZipTheme.color.labelAlternative_A29D96,
            )

            Gap(height = 31.dp)
        },
        content = {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                CategoryColor.entries.forEach {
                    ColorChip(
                        color = it.color,
                        onClick = { onColorSelected(it.name) }
                    )
                }
            }

            Gap(height = 28.dp)
        }
    )
}

@Composable
private fun ColorChip(
    modifier: Modifier = Modifier,
    color: Color,
    size: Dp = 48.dp,
    onClick: () -> Unit,
){
    Box(
        modifier = modifier
            .size(size)
            .background(color = color, shape = CircleShape)
            .noRippleClickable(onClick = onClick),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoryColorPickerBottomSheetPreview(){
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedColor by remember {mutableStateOf("")}
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
            Gap(100.dp)
            Text("selectedColor = $selectedColor")
        }
        CategoryColorPickerBottomSheet(
            isBottomSheetVisible = isBottomSheetVisible,
            onDismissRequest = { isBottomSheetVisible = false },
            onColorSelected = { selectedColor = it }
        )
    }
}