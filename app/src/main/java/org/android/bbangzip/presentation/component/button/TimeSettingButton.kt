package org.android.bbangzip.presentation.component.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeSettingButton(
    onSettingTimeClick: () -> Unit,
    startTime: LocalTime?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.noRippleClickable(onClick = onSettingTimeClick),
    ) {
        Text(
            text =
                if (startTime != null) {
                    startTime.format(DateTimeFormatter.ofPattern("a hh:mm"))
                } else {
                    stringResource(id = R.string.add_todo_start_time_not_set)
                },
            color = BbangZipTheme.color.labelAlternative_A29D96,
            style = BbangZipTheme.typography.body1Medium,
        )

        Gap(8.dp)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = stringResource(id = R.string.add_todo_arrow_icon_description),
            tint = BbangZipTheme.color.labelAlternative_A29D96,
            modifier = Modifier.size(20.dp),
        )
    }
}
