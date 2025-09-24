package org.android.bbangzip.presentation.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.component.button.BbangzipBaseButton
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun LoginScreen(
    state: LoginContract.LoginState,
    onClickKakaoLoginBtn: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .background(BbangZipTheme.color.backgroundStrong_F2EAE4)
                .fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = state.isBackgroundVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 600, easing = EaseOut)),
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(417f / 812f),
        ) {
            Image(
                painter = painterResource(R.drawable.img_login_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }

        AnimatedVisibility(
            visible = state.isBackgroundVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 600, easing = EaseOut)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 43.dp, top = 40.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_bbangzip_team_info),
                contentDescription = null,
                tint = BbangZipTheme.color.primaryNormal_897869,
            )
        }

        AnimatedVisibility(
            visible = state.isSloganVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500, easing = EaseInOut)),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 61.dp, end = 61.dp, top = 163.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.png_bbangzip_slogan),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        AnimatedVisibility(
            visible = state.isKakaoLoginBtnVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 400, easing = EaseInOut)),
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 75.dp, start = 16.dp, end = 16.dp),
        ) {
            BbangzipBaseButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickKakaoLoginBtn,
                verticalPadding = 16.dp,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_kakao_14_15),
                        contentDescription = null,
                        // tint = BbangZipTheme.color.kakaoBlack_0000D9,
                        modifier = Modifier.size(20.dp),
                    )
                },
                colors = BbangZipButtonDefaults.colors(enabledContainerColor = BbangZipTheme.color.kakaoYellow_FEE500),
                content = {
                    Text(
                        text = stringResource(R.string.auth_kakao_login),
                        style = BbangZipTheme.typography.body1Bold,
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BBANGZIPANDROIDTheme {
        LoginScreen(
            state =
                LoginContract.LoginState(
                    isOnboardingCompleted = false,
                )
        )
    }
}