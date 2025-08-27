package org.android.bbangzip.presentation.ui.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TodoRoute(modifier: Modifier = Modifier) {
    TodoScreen(
        categories = exampleList,
        motivationMessage = "",
    )
}
