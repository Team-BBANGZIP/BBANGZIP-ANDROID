package org.android.bbangzip.presentation.ui.dummy

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DummyScreen(
    dummyState: DummyContract.DummyState,
    onClickNextBtn: (String) -> Unit = {},
    viewModel: DummyViewModel = hiltViewModel(),
) {
    Text(text = "Dummy")
}
