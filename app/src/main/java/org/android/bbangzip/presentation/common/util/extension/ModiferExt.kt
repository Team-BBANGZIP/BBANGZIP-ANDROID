package org.android.bbangzip.presentation.common.util.extension

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    isRunOnce: Boolean = false,
    onClick: () -> Unit,
): Modifier =
    composed {
        var hasClicked by remember { mutableStateOf(false) }

        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick =
                if (isRunOnce) {
                    {
                        if (!hasClicked) {
                            hasClicked = true
                            onClick()
                        }
                    }
                } else {
                    onClick
                },
        )
    }

@Composable
fun Modifier.applyFilterOnClick(
    baseColor: Color = Color.Transparent,
    radius: Dp = 0.dp,
    isDisabled: Boolean = false,
    filterColor: Color = Color(0xFF282119).copy(alpha = 0.12f),
    onClick: () -> Unit = {},
): Modifier =
    composed {
        val finalFilteredColor =
            remember(baseColor, filterColor) {
                filterColor.compositeOver(baseColor)
            }
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        val rippleIndication = ripple(bounded = true)

        this
            .clip(RoundedCornerShape(size = radius))
            .background(
                if (isPressed && !isDisabled) finalFilteredColor else baseColor,
                shape = RoundedCornerShape(size = radius),
            )
            .clickable(
                interactionSource = interactionSource,
                indication = if (!isDisabled) rippleIndication else null,
                onClick = { onClick() },
            )
    }

fun Modifier.innerShadow(
    shape: Shape,
    color: Color,
    blur: Dp,
    offsetY: Dp,
    offsetX: Dp,
    spread: Dp,
) = drawWithContent {
    drawContent()

    val rect = Rect(Offset.Zero, size)
    val paint =
        Paint().apply {
            this.color = color
            this.isAntiAlias = true
        }

    val shadowOutline = shape.createOutline(size, layoutDirection, this)

    drawIntoCanvas { canvas ->
        canvas.saveLayer(rect, paint)
        canvas.drawOutline(shadowOutline, paint)

        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
        paint.color = Color.Black

        val spreadOffsetX = offsetX.toPx() + if (offsetX.toPx() < 0) -spread.toPx() else spread.toPx()
        val spreadOffsetY = offsetY.toPx() + if (offsetY.toPx() < 0) -spread.toPx() else spread.toPx()

        canvas.translate(spreadOffsetX, spreadOffsetY)
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint =
        Paint().apply {
            this.color = color
        }

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}
