package com.bunbeauty.papakarlo.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }

data class AppDimensions(
    val verySmallSpace: Dp = 2.dp,
    val smallSpace: Dp = 4.dp,
    val mediumSpace: Dp = 8.dp,
    val elevation: Dp = 1.dp,
    val codeWidth: Dp = 56.dp,
    val cardHeight: Dp = 40.dp,
    val buttonSize: Dp = 40.dp,
    val addressEndSpace: Dp = 32.dp,
    val productImageHeight: Dp = 72.dp,
    val productImageWidth: Dp = 108.dp,
    val blurHeight: Dp = 16.dp,
    val autoCompleteListHeight: Dp = 160.dp,
) {
    fun getTopItemSpaceByIndex(i: Int): Dp {
        return if (i == 0) {
            0.dp
        } else {
            smallSpace
        }
    }
}