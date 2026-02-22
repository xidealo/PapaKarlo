package com.bunbeauty.designsystem.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.icon24() = this.size(24.dp)

fun Modifier.icon16() = this.size(16.dp)

fun <T> Modifier.applyIfNotNull(
    value: T?,
    block: Modifier.(T) -> Modifier,
): Modifier =
    if (value == null) {
        this
    } else {
        block(value)
    }


val LocalStatusBarColor =
    compositionLocalOf { mutableStateOf(Color.Transparent) }

val LocalBottomBarPadding = compositionLocalOf { 0.dp }

fun Modifier.ignoreHorizontalParentPadding(horizontal: Dp): Modifier =
    this.layout { measurable, constraints ->
        val overridenWidth = constraints.maxWidth + 2 * horizontal.roundToPx()
        val placeable = measurable.measure(constraints.copy(maxWidth = overridenWidth))
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
