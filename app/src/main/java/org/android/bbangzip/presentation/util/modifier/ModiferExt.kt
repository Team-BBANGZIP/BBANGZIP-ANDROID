package org.android.bbangzip.presentation.util.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier =
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
    )

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
                shape = RoundedCornerShape(size = radius)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = if (!isDisabled) rippleIndication else null,
                onClick = { onClick() },
            )
    }