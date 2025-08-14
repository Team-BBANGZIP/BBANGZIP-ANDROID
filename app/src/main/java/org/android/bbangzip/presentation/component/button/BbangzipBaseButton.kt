package org.android.bbangzip.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.button.model.ButtonColors
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

/**
 * 빵집 앱의 기본 버튼
 *
 * @param onClick 버튼 클릭 시 실행할 콜백
 * @param modifier 버튼에 적용할 수정자
 * @param enabled 버튼 활성화 여부
 * @param colors 버튼에 관한 색상들
 * @param verticalPadding 버튼 세로 패딩
 * @param contentGap 아이콘과 콘텐츠 사이 간격
 * @param leadingIcon 버튼 앞에 표시할 아이콘
 * @param trailingIcon 버튼 뒤에 표시할 아이콘
 * @param content 버튼 내용
 */
@Composable
fun BbangzipBaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = BbangZipButtonDefaults.colors(),
    verticalPadding: Dp = BbangZipButtonDefaults.BUTTON_VERTICAL_PADDING,
    contentGap: Dp = BbangZipButtonDefaults.BUTTON_CONTENT_GAP,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val currentContentColor = if (enabled) colors.enabledContentColor else colors.disabledContentColor
    val currentContainerColor = if (enabled) colors.enabledContainerColor else colors.disabledContainerColor
    Box(
        modifier =
            modifier
                .clip(BbangZipButtonDefaults.BUTTON_SHAPE)
                .background(
                    color = currentContainerColor,
                )
                .noRippleClickable(
                    enabled = enabled,
                    onClick = onClick,
                )
                .padding(vertical = verticalPadding),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides currentContentColor) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(contentGap, Alignment.CenterHorizontally),
            ) {
                leadingIcon?.invoke()

                content()

                trailingIcon?.invoke()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BbangzipBaseButtonPreview() {
    var isEnable by remember { mutableStateOf(true) }
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
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_30minutes_more),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

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
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
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
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_60minutes_more),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

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
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
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
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_go_back),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

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
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
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
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_go_back),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

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
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
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
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_30minutes_more),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

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
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
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
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_cancellation),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

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
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
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
                colors =
                    BbangZipButtonDefaults.colors(
                        enabledContainerColor = BbangZipTheme.color.primaryLight_C8B5A2,
                    ),
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
                colors =
                    BbangZipButtonDefaults.colors(
                        enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    ),
                content = {
                    Text(
                        text = stringResource(R.string.button_label_save),
                        style = BbangZipTheme.typography.body2Medium,
                    )
                },
            )

            BbangzipBaseButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { isEnable = !isEnable },
                enabled = isEnable,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_trash_default_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                },
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
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_go_back),
                            style = BbangZipTheme.typography.body2Medium,
                        )
                    },
                )

                Gap(width = 8.dp)

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
                    colors =
                        BbangZipButtonDefaults.colors(
                            enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                        ),
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
