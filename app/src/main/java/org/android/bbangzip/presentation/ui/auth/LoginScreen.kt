package org.android.bbangzip.presentation.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.android.bbangzip.presentation.common.component.button.BbangZipButtonDefaults
import org.android.bbangzip.presentation.common.component.button.BbangzipBaseButton
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun LoginScreen(
    state: LoginContract.LoginState,
    onClickKakaoLoginBtn: () -> Unit = {},
) {
    var isEntered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isEntered = true
    }

    Box(
        modifier =
            Modifier
                .background(BbangZipTheme.color.backgroundStrong_F2EAE4)
                .fillMaxSize(),
    ) {
        // Step 1: Delay 200ms, Duration 500ms
        AnimatedVisibility(
            visible = isEntered,
            enter =
                fadeIn(
                    animationSpec =
                        tween(
                            durationMillis = 500,
                            delayMillis = 200,
                            easing = EaseInOut,
                        ),
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 61.dp, end = 61.dp, top = 163.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.png_bbangzip_slogan),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        // Step 2: Delay (200 + 500 + 200) = 900ms, Duration 600ms
        AnimatedVisibility(
            visible = isEntered,
            enter =
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec =
                        tween(
                            durationMillis = 600,
                            delayMillis = 900,
                            easing = EaseOut,
                        ),
                ),
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

        // Step 2: Same Timing
        AnimatedVisibility(
            visible = isEntered,
            enter =
                fadeIn(
                    animationSpec =
                        tween(
                            durationMillis = 600,
                            delayMillis = 900,
                            easing = EaseOut,
                        ),
                ),
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 43.dp, top = 40.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_bbangzip_team_info),
                contentDescription = null,
                tint = BbangZipTheme.color.primaryNormal_897869,
            )
        }

        // Step 3: Delay (900 + 600 + 300) = 1800ms, Duration 400ms
        AnimatedVisibility(
            visible = isEntered,
            enter =
                fadeIn(
                    animationSpec =
                        tween(
                            durationMillis = 400,
                            delayMillis = 1800,
                            easing = EaseInOut,
                        ),
                ),
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
                        modifier = Modifier.size(20.dp),
                        tint = BbangZipTheme.color.kakaoBlack_0000D9,
                    )
                },
                colors =
                    BbangZipButtonDefaults.colors(
                        enabledContainerColor = BbangZipTheme.color.kakaoYellow_FEE500,
                    ),
                content = {
                    Text(
                        text = stringResource(R.string.auth_kakao_login),
                        style = BbangZipTheme.typography.body1Bold,
                        color = BbangZipTheme.color.kakaoBlack_0000D9,
                    )
                },
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
                ),
        )
    }
}
