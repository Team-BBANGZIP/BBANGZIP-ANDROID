package org.android.bbangzip.presentation.ui.timer.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun CircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    progressMax: Float = 100f,
    progressBarColor: Color = BbangZipTheme.color.primaryLight_C8B5A2,
    progressBarWidth: Dp = 11.dp,
    backgroundProgressBarColor: Color = BbangZipTheme.color.secondaryNormal_F6F1EE,
    backgroundProgressBarWidth: Dp = 11.dp,
    roundBorder: Boolean = true,
    startAngle: Float = 270f,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    centerContent: @Composable ((Modifier) -> Unit) = { },
    bottomContent: @Composable ((Modifier) -> Unit) = { },
) {
    // Animation for progress
    val animateFloat by animateFloatAsState(
        targetValue = progress,
        animationSpec =
            tween(
                durationMillis = animationDuration,
                delayMillis = animationDelay,
            ),
        label = "progress_animation",
    )

    val density = LocalDensity.current
    val strokeWidthPx = with(density) { progressBarWidth.toPx() }
    val backgroundStrokeWidthPx = with(density) { backgroundProgressBarWidth.toPx() }
    val backgroundWhiteStrokeWithPx = with(density) { 5.dp.toPx() }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .aspectRatio(1f),
    ) {
        centerContent(Modifier.align(Alignment.Center))

        bottomContent(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
        )

        Canvas(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            val canvasSize = this.size
            val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)

            val progressRadius = (canvasSize.minDimension - strokeWidthPx.coerceAtLeast(backgroundStrokeWidthPx)) / 2f

            val innerWhiteRadius = progressRadius - (strokeWidthPx / 2f) - (backgroundWhiteStrokeWithPx / 2f)

            drawCircularProgressBackground(
                center = center,
                radius = progressRadius,
                color = backgroundProgressBarColor,
                strokeWidth = backgroundStrokeWidthPx,
            )

            drawCircularProgress(
                center = center,
                radius = progressRadius,
                progress = animateFloat,
                progressMax = progressMax,
                color = progressBarColor,
                strokeWidth = strokeWidthPx,
                startAngle = startAngle,
                roundBorder = roundBorder,
            )

            drawCircularProgressBackground(
                color = Color.White,
                radius = innerWhiteRadius,
                center = center,
                strokeWidth = backgroundWhiteStrokeWithPx,
            )
        }
    }
}

private fun DrawScope.drawCircularProgressBackground(
    center: Offset,
    radius: Float,
    color: Color,
    strokeWidth: Float,
) {
    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth),
    )
}

private fun DrawScope.drawCircularProgress(
    center: Offset,
    radius: Float,
    progress: Float,
    progressMax: Float,
    color: Color,
    strokeWidth: Float,
    startAngle: Float,
    roundBorder: Boolean,
) {
    val sweepAngle = (progress / progressMax) * 360f

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = if (sweepAngle > 0) sweepAngle else 0.1f,
        useCenter = false,
        topLeft =
            Offset(
                center.x - radius,
                center.y - radius,
            ),
        size = Size(radius * 2, radius * 2),
        style =
            Stroke(
                width = strokeWidth,
                cap = if (roundBorder) StrokeCap.Round else StrokeCap.Butt,
            ),
    )
}

@Preview
@Composable
private fun CircularProgressBarPreview() {
    CircularProgressBar(
        progress = 0f,
        progressMax = 100f,
        centerContent = { mod ->
            Text(
                text = "25%",
                modifier = mod,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        bottomContent = { mod ->
            Box(
                mod
                    .size(120.dp, 100.dp)
                    .background(color = Color.Blue),
            )
        },
    )
}
