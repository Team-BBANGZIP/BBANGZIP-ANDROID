package org.android.bbangzip.presentation.ui.my

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun MyScreen(
    state: MyContract.MyState,
    onClickProfileArea: () -> Unit,
    onClickScreenSetting: () -> Unit,
    onClickNotification: () -> Unit,
    onClickCustomerCenter: () -> Unit,
    onClickTermsOfService: () -> Unit,
    onClickFeedback: () -> Unit,
    onClickAppReview: () -> Unit,
    onClickLogoutBtn: () -> Unit,
    onConfirmLogoutBtn: () -> Unit,
    onCancelLogoutBtn: () -> Unit,
    onClickLogoutBottomSheetDismissRequest: () -> Unit,
    onClickWithdrawalBtn: () -> Unit,
    onConfirmWithdrawalBtn: () -> Unit,
    onCancelWithdrawalBtn: () -> Unit,
    onClickWithdrawalBottomSheetDismissRequest: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BbangZipTheme.color.secondaryLight_FAF6F3)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        item {
            Gap(height = 32.dp)

            Text(
                text = stringResource(R.string.my_title),
                style = BbangZipTheme.typography.title1SemiBold,
                color = BbangZipTheme.color.labelStrong_463D34,
                modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)
            )

            ProfileArea(
                nickname = state.nickname,
                commitmentMessage = state.commitmentMessage,
                profileImgResId = state.profileImgRes,
                onClickProfileArea = onClickProfileArea
            )

            Gap(height = 32.dp)
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
                    .padding(vertical = 32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_screen_20),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 24.dp),
                    tint = Color.Unspecified,
                )

                Gap(height = 12.dp)

                MyPageMenuItem(
                    title = stringResource(R.string.my_screen),
                    onClickMenu = onClickScreenSetting
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = BbangZipTheme.color.secondaryNormal_F6F1EE,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)
                )

                Icon(
                    painter = painterResource(R.drawable.ic_notification_20),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 24.dp),
                    tint = Color.Unspecified,
                )

                Gap(height = 12.dp)

                MyPageMenuItem(
                    title = stringResource(R.string.my_notification),
                    onClickMenu = onClickNotification
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = BbangZipTheme.color.secondaryNormal_F6F1EE,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)
                )

                Icon(
                    painter = painterResource(R.drawable.ic_service_20),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 24.dp),
                    tint = Color.Unspecified,
                )

                Gap(height = 12.dp)

                MyPageMenuItem(
                    title = stringResource(R.string.my_customer_center),
                    onClickMenu = onClickCustomerCenter
                )

                Gap(height = 12.dp)

                MyPageMenuItem(
                    title = stringResource(R.string.my_terms_of_service),
                    onClickMenu = onClickTermsOfService
                )

                Gap(height = 12.dp)

                MyPageMenuItem(
                    title = stringResource(R.string.my_feedback),
                    onClickMenu = onClickFeedback
                )

                Gap(height = 12.dp)

                MyPageMenuItem(
                    title = stringResource(R.string.my_app_review),
                    onClickMenu = onClickAppReview
                )

                Gap(height = 12.dp)

                AppVersionItem(appVersion = state.appVersion)

                Gap(height = 17.dp)

                LogoutAndWithdrawal(
                    onClickLogoutBtn = onClickLogoutBtn,
                    onClickWithdrawalBtn = onClickWithdrawalBtn
                )
            }
        }
    }

    LogoutBottomSheet(
        isBottomSheetVisible = state.isLogoutConfirmBottomSheetVisible,
        onDismissRequest = onClickLogoutBottomSheetDismissRequest,
        onLogoutClick = onConfirmLogoutBtn,
        onCancelClick = onCancelLogoutBtn,
    )

    WithdrawalBottomSheet(
        isBottomSheetVisible = state.isWithdrawalConfirmBottomSheetVisible,
        onDismissRequest = onClickWithdrawalBottomSheetDismissRequest,
        onCancelClick = onCancelWithdrawalBtn,
        onWithdrawalClick = onConfirmWithdrawalBtn,
    )
}

@Composable
private fun ProfileArea(
    modifier: Modifier = Modifier,
    nickname: String,
    commitmentMessage: String,
    @DrawableRes profileImgResId: Int,
    onClickProfileArea: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable { onClickProfileArea() }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = profileImgResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Gap(width = 12.dp)

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nickname,
                    style = BbangZipTheme.typography.title4SemiBold,
                    color = BbangZipTheme.color.labelStrong_463D34
                )

                Icon(
                    painter = painterResource(R.drawable.ic_pencil_default_24),
                    contentDescription = null,
                    tint = BbangZipTheme.color.labelAssistive_C9C7C5,
                )
            }

            Gap(height = 3.dp)

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commitmentMessage,
                style = BbangZipTheme.typography.subTitle1Medium,
                color = BbangZipTheme.color.labelAlternative_A29D96
            )
        }
    }
}

@Composable
private fun MyPageMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    onClickMenu: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable { onClickMenu() }
            .padding(start = 24.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = BbangZipTheme.typography.body2Medium,
            color = BbangZipTheme.color.labelNormal_6B6560,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right_24),
            modifier = Modifier
                .padding(4.dp),
            contentDescription = null,
            tint = BbangZipTheme.color.labelAssistive_C9C7C5
        )
    }
}

@Composable
private fun AppVersionItem(
    modifier: Modifier = Modifier,
    appVersion: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.my_app_version),
            style = BbangZipTheme.typography.body2Medium,
            color = BbangZipTheme.color.labelNormal_6B6560,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = appVersion,
            style = BbangZipTheme.typography.body2Medium,
            color = BbangZipTheme.color.labelAssistive_C9C7C5
        )
    }
}

@Composable
private fun LogoutAndWithdrawal(
    modifier: Modifier = Modifier,
    onClickLogoutBtn: () -> Unit,
    onClickWithdrawalBtn: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .noRippleClickable { onClickLogoutBtn() },
            text = stringResource(R.string.my_logout),
            style = BbangZipTheme.typography.body4Medium,
            color = BbangZipTheme.color.labelAssistive_C9C7C5
        )

        VerticalDivider(
            thickness = 1.dp,
            color = BbangZipTheme.color.labelAssistive_C9C7C5,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .height(16.dp)
        )

        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .noRippleClickable { onClickWithdrawalBtn() },
            text = stringResource(R.string.my_withdrawal),
            style = BbangZipTheme.typography.body4Medium,
            color = BbangZipTheme.color.labelAssistive_C9C7C5
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogoutBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Gap(16.dp)

            Text(
                text = stringResource(R.string.my_logout_title),
                style = BbangZipTheme.typography.title1SemiBold,
                color = BbangZipTheme.color.primaryNormal_897869,
            )

            Gap(height = 60.dp)
        },
        content = {
            Text(
                text = stringResource(R.string.my_logout_descriptoin),
                style = BbangZipTheme.typography.body2Medium,
                color = BbangZipTheme.color.labelAlternative_A29D96,
                textAlign = TextAlign.Center
            )

            Gap(height = 60.dp)
        },
        interactRow = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelClick,
                    colors = BbangZipButtonDefaults.colors(
                        enabledContainerColor = BbangZipTheme.color.primaryNormal_897869,
                    ),
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_cancel),
                            style = BbangZipTheme.typography.body2Medium,
                            color = BbangZipTheme.color.staticWhite_FFFFFF
                        )
                    }
                )

                Gap(width = 8.dp)

                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = onLogoutClick,
                    colors = BbangZipButtonDefaults.colors(
                        enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    ),
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_logout),
                            style = BbangZipTheme.typography.body2Medium,
                            color = BbangZipTheme.color.staticWhite_FFFFFF,
                        )
                    }
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WithdrawalBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onWithdrawalClick: () -> Unit
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        title = {
            Gap(16.dp)

            Text(
                text = stringResource(R.string.my_withdrawal_title),
                style = BbangZipTheme.typography.title1SemiBold,
                color = BbangZipTheme.color.primaryNormal_897869,
            )

            Gap(height = 60.dp)
        },
        content = {
            Text(
                text = stringResource(R.string.my_withdrawal_description),
                style = BbangZipTheme.typography.body2Medium,
                color = BbangZipTheme.color.labelAlternative_A29D96,
                textAlign = TextAlign.Center
            )

            Gap(height = 60.dp)
        },
        interactRow = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelClick,
                    colors = BbangZipButtonDefaults.colors(
                        enabledContainerColor = BbangZipTheme.color.primaryNormal_897869,
                    ),
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_cancel),
                            style = BbangZipTheme.typography.body2Medium,
                            color = BbangZipTheme.color.staticWhite_FFFFFF
                        )
                    }
                )

                Gap(width = 8.dp)

                BbangzipBaseButton(
                    modifier = Modifier.weight(1f),
                    onClick = onWithdrawalClick,
                    colors = BbangZipButtonDefaults.colors(
                        enabledContainerColor = BbangZipTheme.color.primaryStrong_4B4137,
                    ),
                    content = {
                        Text(
                            text = stringResource(R.string.button_label_withdrawal),
                            style = BbangZipTheme.typography.body2Medium,
                            color = BbangZipTheme.color.staticWhite_FFFFFF,
                        )
                    }
                )
            }
        },
    )
}


@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    BBANGZIPANDROIDTheme {
        MyScreen(
            state = MyContract.MyState(
                nickname = "홍길동",
                commitmentMessage = "열심히 하자!",
                appVersion = "v 1.0.0",
                isWithdrawalConfirmBottomSheetVisible = true
            ),
            onClickProfileArea = {},
            onClickScreenSetting = {},
            onClickNotification = {},
            onClickCustomerCenter = {},
            onClickTermsOfService = {},
            onClickFeedback = {},
            onClickAppReview = {},
            onClickLogoutBtn = {},
            onConfirmLogoutBtn = {},
            onCancelLogoutBtn = {},
            onClickLogoutBottomSheetDismissRequest = {},
            onClickWithdrawalBtn = {},
            onConfirmWithdrawalBtn = {},
            onCancelWithdrawalBtn = {},
            onClickWithdrawalBottomSheetDismissRequest = {}
        )
    }
}