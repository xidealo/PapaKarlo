package com.bunbeauty.papakarlo.compose.theme

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }

data class AppDimensions(
    val verySmallSpace: Dp = 2.dp,
    val smallSpace: Dp = 4.dp,
    val mediumSpace: Dp = 8.dp,
    val elevation: Dp = 2.dp,
    val codeWidth: Dp = 56.dp,
    val cardHeight: Dp = 40.dp,
    val buttonSize: Dp = 40.dp,
    val smallButtonSize: Dp = 32.dp,
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

    fun getEvaluation(hasShadow: Boolean): Dp {
        return if (hasShadow) {
            elevation
        } else {
            0.dp
        }
    }

    @Composable
    fun getButtonEvaluation(hasShadow: Boolean): ButtonElevation {
        return if (hasShadow) {
            ButtonDefaults.elevation()
        } else {
            ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp,
            )
        }
    }
}