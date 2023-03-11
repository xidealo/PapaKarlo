package com.bunbeauty.papakarlo.common.ui

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.icon24() = this.size(24.dp)
fun Modifier.icon16() = this.size(16.dp)

fun <T> Modifier.applyIfNotNull(value: T?, block: Modifier.(T) -> Modifier): Modifier {
    return if (value == null) {
        this
    } else {
        block(value)
    }
}