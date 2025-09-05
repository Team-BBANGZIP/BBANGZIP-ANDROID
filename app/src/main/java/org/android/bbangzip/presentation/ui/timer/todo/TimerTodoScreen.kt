package org.android.bbangzip.presentation.ui.timer.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.topbar.BbangZipBaseTopBar
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun TimerTodoScreen(
    uiState: TimerTodoContract.TimerTodoState,
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit = {},
    onExitBtnClick: () -> Unit = {},
    onRestartTimerBtnClick: () -> Unit = {},
    onAddTodoIconClick: () -> Unit = {},
    onTodoCheckBoxClick: (categoryId: Int, todoId: Int, isChecked: Boolean) -> Unit = { _, _, _ -> },
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(BbangZipTheme.color.backgroundNormal_FFFFFF)
                .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TodoTopBar(onBackIconClick = onBackIconClick)

        Gap(18.dp)

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TodoTitle()


        }
    }
}

@Composable
private fun TodoTopBar(
    onBackIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BbangZipBaseTopBar(
        modifier = modifier,
        leadingIcon = R.drawable.ic_arrow_left_24,
        leadingIconColor = BbangZipTheme.color.labelAlternative_A29D96,
        onLeadingIconClick = onBackIconClick
    )
}

@Composable
fun TodoTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.timer_todo_title),
        style = BbangZipTheme.typography.picker1SemiBold,
        color = BbangZipTheme.color.labelNormal_6B6560,
    )
}

@Preview
@Composable
private fun TimerTodoScreenPreview() {
    TimerTodoScreen(uiState = TimerTodoContract.TimerTodoState())
}