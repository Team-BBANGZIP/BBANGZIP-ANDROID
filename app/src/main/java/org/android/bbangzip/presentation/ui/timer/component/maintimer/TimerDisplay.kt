package org.android.bbangzip.presentation.ui.timer.component.maintimer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.ui.shared.SharedContract
import org.android.bbangzip.presentation.ui.timer.contract.TimerContract
import org.android.bbangzip.presentation.ui.timer.contract.model.TimerSessionUiState
import org.android.bbangzip.presentation.ui.timer.contract.model.getTimerFontColor
import org.android.bbangzip.presentation.ui.timer.contract.model.getTitleText
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun TimerDisplay(
    timerState: TimerContract.TimerState,
    sharedState: SharedContract.SharedState,
    onBreadIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sessionState = timerState.timerSessionState
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
            progress = timerState.progress,
            progressMax = 1f,
            centerContent = { modifier ->
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = timerState.formattedTime,
                        style = BbangZipTheme.typography.timerExtraBold,
                        color = sessionState.getTimerFontColor(),
                    )
                }
            },
            bottomContent = { mod ->
                val breadImageRes =
                    if (isReady) {
                        sharedState.breadImg
                    } else {
                        timerState.breadImg
                    }

                BreadWithTriangleIndicator(
                    modifier = mod,
                    breadImageRes = breadImageRes,
                    showTriangle = isReady,
                    isClickable = isReady,
                    onClick = onBreadIconClick,
                )
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F1E9)
@Composable
private fun TimerDisplayPreview() {
    BBANGZIPANDROIDTheme {
        TimerDisplay(
            timerState =
                TimerContract.TimerState(
                    timerSessionState = TimerSessionUiState.Ready(totalBreadCount = 5),
                ),
            sharedState = SharedContract.SharedState(),
            onBreadIconClick = {},
        )
    }
}
