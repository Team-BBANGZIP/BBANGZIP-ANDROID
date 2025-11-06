package org.android.bbangzip.presentation.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun OnboardingScreen(
    state: OnboardingContract.OnboardingState,
    onClickBackBtn: () -> Unit = {},
    onClickSaveBtn: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BbangZipBaseTopBar(
            title = "프로필 설정",
            titleStyle = BbangZipTheme.typography.body1Medium,
            titleColor = BbangZipTheme.color.labelStrong_463D34,
            leadingIcon = R.drawable.ic_arrow_left_24,
            onLeadingIconClick = onClickBackBtn,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BBANGZIPANDROIDTheme {
        OnboardingScreen(
            state = OnboardingContract.OnboardingState(),
        )
    }
}
