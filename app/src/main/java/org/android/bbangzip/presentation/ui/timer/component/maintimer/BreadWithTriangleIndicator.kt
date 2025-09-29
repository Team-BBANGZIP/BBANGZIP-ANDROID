package org.android.bbangzip.presentation.ui.timer.component.maintimer

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun BreadWithTriangleIndicator(
    breadImageRes: Int,
    showTriangle: Boolean,
    isClickable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    breadSize: DpSize = DpSize(120.dp, 100.dp),
) {
    val infiniteTransition = rememberInfiniteTransition(label = "triangle animation")
    val triangleOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(800, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "triangle offset",
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = showTriangle,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
        ) {
            Icon(
                modifier = Modifier.offset(y = (triangleOffset).dp),
                imageVector = ImageVector.Companion.vectorResource(id = R.drawable.ic_triangle_down_24),
                contentDescription = "moving triangle",
                tint = BbangZipTheme.color.primaryNormal_897869,
            )
        }
        Image(
            modifier =
                Modifier
                    .size(breadSize.width, breadSize.height)
                    .noRippleClickable(enabled = isClickable) { onClick() },
            painter = painterResource(breadImageRes),
            contentDescription = "Timer Icon",
        )
    }
}

@Preview(name = "Indicator Hidden", showBackground = true, backgroundColor = 0xFFF8F1E9)
@Composable
private fun BreadWithTriangleIndicatorPreview_Hidden() {
    BBANGZIPANDROIDTheme {
        BreadWithTriangleIndicator(
            breadImageRes = R.drawable.img_baking_bread_level2, // 예시 굽는 중인 빵 이미지
            showTriangle = false,
            isClickable = false,
            onClick = {},
        )
    }
}
