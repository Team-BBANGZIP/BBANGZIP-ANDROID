package org.android.bbangzip.presentation.ui.timer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimerRoute(modifier: Modifier = Modifier) {
    Text("timer")
}

@Preview(showSystemUi = true)
@Composable
private fun TimerRoutePreview() {
    TimerRoute()
}
