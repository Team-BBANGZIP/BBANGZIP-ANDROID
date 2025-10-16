package org.android.bbangzip.presentation.ui.timer.component.maintimer

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.common.component.preview.BbangZipPreviewWrapper
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.ui.shared.SharedContract
import org.android.bbangzip.presentation.ui.timer.contract.TimerContract
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerSessionUiState
import org.android.bbangzip.presentation.ui.timer.contract.model.getTimerFontColor
import org.android.bbangzip.presentation.ui.timer.contract.model.getTitleText
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun TimerDisplay(
    sessionState: TimerSessionUiState,
    progress: Float,
    formattedTime: String,
    @DrawableRes runningBreadImg: Int,
    @DrawableRes readyBreadImg: Int,
    onBreadIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isReady = sessionState is TimerSessionUiState.Ready

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(sessionState.getTitleText()),
            style = BbangZipTheme.typography.title2Medium,
            color = BbangZipTheme.color.labelAlternative_A29D96,
        )

        Gap(24.dp)

        CircularProgressBar(
            modifier = Modifier.padding(horizontal = 32.dp),
            progress = progress,
            progressMax = 1f,
            centerContent = { centerModifier ->
                Column(
                    modifier = centerModifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = formattedTime,
                        style = BbangZipTheme.typography.timerExtraBold,
                        color = sessionState.getTimerFontColor(),
                    )
                }
            },
            bottomContent = { bottomModifier ->
                val breadImageRes =
                    if (isReady) {
                        readyBreadImg
                    } else {
                        runningBreadImg
                    }

                BreadWithTriangleIndicator(
                    modifier = bottomModifier,
                    breadImageRes = breadImageRes,
                    showTriangle = isReady,
                    isClickable = isReady,
                    onClick = onBreadIconClick,
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimerDisplayPreview() {
    BbangZipPreviewWrapper {
        val previewState = TimerContract.TimerState(
            timerSessionState = TimerSessionUiState.Ready(totalBreadCount = 5),
        )

        TimerDisplay(
            sessionState = previewState.timerSessionState,
            progress = previewState.progress,
            formattedTime = previewState.formattedTime,
            runningBreadImg = previewState.breadImg,
            readyBreadImg = SharedContract.SharedState().breadImg,
            onBreadIconClick = {},
        )
    }
}