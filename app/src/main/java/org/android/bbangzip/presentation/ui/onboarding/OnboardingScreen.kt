package org.android.bbangzip.presentation.ui.onboarding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun OnboardingScreen(
    state: OnboardingContract.OnboardingState,
    onClickNextBtn: () -> Unit = {}
) {
    Text("온보딩")
}