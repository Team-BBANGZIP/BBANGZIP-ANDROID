package org.android.bbangzip.presentation.common.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.type.CategoryColor
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun ColorSettingButton(
    selectedColorString: String,
    onColorSettingRowActionIconClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(24.dp)
                    .background(color = CategoryColor.fromString(selectedColorString).color, shape = CircleShape),
        )

        Gap(width = 6.dp)

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_24),
            contentDescription = stringResource(R.string.edit_category_show_color_picker_icon_description),
            modifier = Modifier.noRippleClickable(onClick = onColorSettingRowActionIconClick),
            tint = BbangZipTheme.color.labelAssistive_C9C7C5,
        )
    }
}
