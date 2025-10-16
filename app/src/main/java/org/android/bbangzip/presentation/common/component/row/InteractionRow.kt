package org.android.bbangzip.presentation.common.component.row

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun InteractionRow(
    @DrawableRes interactionIconResId: Int,
    actionName: String,
    modifier: Modifier = Modifier,
    @StringRes description: Int? = null,
    interactionButton: @Composable () -> Unit = {},
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(interactionIconResId),
                contentDescription = null,
                tint = BbangZipTheme.color.labelAlternative_A29D96,
            )

            Gap(width = 8.dp)

            Text(
                text = actionName,
                modifier = Modifier.padding(vertical = 10.dp),
                style = BbangZipTheme.typography.body2Medium,
                color = BbangZipTheme.color.labelAlternative_A29D96,
            )

            Gap()

            interactionButton()
        }
        if (description != null) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Gap(width = 32.dp)

                Text(
                    text = stringResource(description),
                    style = BbangZipTheme.typography.body4Medium,
                    color = BbangZipTheme.color.labelAssistive_C9C7C5,
                )
            }
        }
    }
}
