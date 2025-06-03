package org.android.bbangzip.presentation.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun BbangzipBaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color,
    contentColor: Color,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 14.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = BbangZipTheme.color.labelDisable_E4E2E0,
                disabledContentColor = BbangZipTheme.color.labelAssistive_C9C7C5,
            ),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            leadingIcon?.let {
                it()
                Gap(width = 4)
            }

            content()

            trailingIcon?.let {
                Gap(width = 4)
                it()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BbangzipBaseButtonPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(140f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus_bold_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryNormal_897869,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_30minutes_more),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8)

                BbangzipBaseButton(
                    modifier = Modifier.weight(187f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_book_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_complete_task_check),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(140f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus_bold_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryNormal_897869,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_60minutes_more),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8)

                BbangzipBaseButton(
                    modifier = Modifier.weight(187f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_book_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_complete_task_check),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_go_back_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryNormal_897869,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_go_back),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8)

                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_x_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_initialize),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_go_back_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryNormal_897869,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_go_back),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8)

                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_x_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_exit),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(140f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus_bold_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryNormal_897869,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_30minutes_more),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8)

                BbangzipBaseButton(
                    modifier = Modifier.weight(187f),
                    onClick = { },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_x_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_exit),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_x_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryNormal_897869,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_cancellation),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8)

                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_check_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_setting),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }

            BbangzipBaseButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_trash_default_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                },
                containerColor = BbangZipTheme.color.primaryLight_C8B5A2,
                contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                content = {
                    Text(
                        text = stringResource(R.string.button_label_delete),
                        style = BbangZipTheme.typography.body2Medium,
                    )
                },
            )

            BbangzipBaseButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                onClick = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar_default_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                },
                containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                content = {
                    Text(
                        text = stringResource(R.string.button_label_save),
                        style = BbangZipTheme.typography.body2Medium,
                    )
                },
            )

            BbangzipBaseButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                enabled = false,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_trash_default_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                },
                containerColor = BbangZipTheme.color.primaryLight_C8B5A2,
                contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                content = {
                    Text(
                        text = stringResource(R.string.button_label_delete),
                        style = BbangZipTheme.typography.body2Medium,
                    )
                },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_return_thin_24),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryNormal_897869,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_go_back),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8)

                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_trash_default_24),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                    containerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    contentColor = BbangZipTheme.color.staticWhite_FFFFFF,
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_delete),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )
            }
        }
    }
}
