package org.android.bbangzip.presentation.common.util.scroll

import org.android.bbangzip.presentation.common.type.AutoScrollDirection

fun calculateScrollSpeed(
    direction: AutoScrollDirection,
    touchPointY: Float,
    columnHeight: Int,
    scrollThreshold: Float,
    minScrollAmount: Float = 5f,
    maxScrollAmount: Float = 30f
): Float {
    val intensity =
        when (direction) {
            AutoScrollDirection.UP -> (scrollThreshold - touchPointY) / scrollThreshold
            AutoScrollDirection.DOWN -> (touchPointY - (columnHeight - scrollThreshold)) / scrollThreshold
            AutoScrollDirection.NONE -> 0f
        }.coerceIn(0f, 1f)

    val speed = minScrollAmount + (maxScrollAmount - minScrollAmount) * intensity

    return if (direction == AutoScrollDirection.UP) -speed else speed
}