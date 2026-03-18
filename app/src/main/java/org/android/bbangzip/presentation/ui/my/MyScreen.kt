package org.android.bbangzip.presentation.ui.my

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.TwoButtonBottomSheet
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
            .background(BbangZipTheme.color.backgroundStrong_F2EAE4)
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
                    modifier = Modifier.padding(start = 24.dp)
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
                    modifier = Modifier.padding(start = 24.dp)
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
                    painter = painterResource(R.drawable.ic_notification_20),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 24.dp)
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
            }
        }

        item {
            AppVersionItem(appVersion = state.appVersion)
            Gap(height = 17.dp)
        }

        item {
            LogoutAndWithdrawal(
                onClickLogoutBtn = onClickLogoutBtn,
                onClickWithdrawalBtn = onClickWithdrawalBtn
            )
        }
    }

    TwoButtonBottomSheet(
        title = stringResource(R.string.my_logout_title),
        description = stringResource(R.string.my_logout_descriptoin),
        isBottomSheetVisible = state.isLogoutConfirmBottomSheetVisible,
        onDismissRequest = onClickLogoutBottomSheetDismissRequest,
        completeBtnTitle = stringResource(R.string.button_label_logout),
        cancelBtnTitle = stringResource(R.string.button_label_cancellation),
        onCancelClick = onCancelLogoutBtn,
        onCompleteClick = onConfirmLogoutBtn,
    )

    TwoButtonBottomSheet(
        title = stringResource(R.string.my_withdrawal_title),
        description = stringResource(R.string.my_withdrawal_description),
        completeBtnTitle = stringResource(R.string.button_label_withdrawal),
        cancelBtnTitle = stringResource(R.string.button_label_cancellation),
        isBottomSheetVisible = state.isWithdrawalConfirmBottomSheetVisible,
        onDismissRequest = onClickWithdrawalBottomSheetDismissRequest,
        onCancelClick = onCancelWithdrawalBtn,
        onCompleteClick = onConfirmWithdrawalBtn,
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
            Row {
                Text(
                    text = nickname,
                    style = BbangZipTheme.typography.title4SemiBold,
                    color = BbangZipTheme.color.labelStrong_463D34
                )

                Icon(
                    painter = painterResource(R.drawable.ic_pencil_default_24),
                    contentDescription = null
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
            contentDescription = null
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
        verticalAlignment = Alignment.CenterVertically
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
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )

        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .noRippleClickable { onClickWithdrawalBtn() },
            text = stringResource(R.string.my_logout),
            style = BbangZipTheme.typography.body4Medium,
            color = BbangZipTheme.color.labelAssistive_C9C7C5
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    BBANGZIPANDROIDTheme {
        MyScreen(
            state = MyContract.MyState(
                nickname = "홍길동",
                commitmentMessage = "열심히 하자!",
                appVersion = "v 1.0.0"
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