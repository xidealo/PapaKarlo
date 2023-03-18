package com.bunbeauty.papakarlo.common.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }

data class AppDimensions(
    val verySmallSpace: Dp = 4.dp,
    val smallSpace: Dp = 8.dp,
    val mediumSpace: Dp = 16.dp,
    val largeSpace: Dp = 24.dp,
    val elevation: Dp = 1.dp,
    val bottomSurfaceElevation: Dp = 6.dp,
    val codeWidth: Dp = 56.dp,
    val cardHeight: Dp = 40.dp,
    val buttonSize: Dp = 40.dp,
    val buttonHeight: Dp = 40.dp,
    val smallButtonSize: Dp = 32.dp,
    val addressEndSpace: Dp = 32.dp,
    val productImageSmallHeight: Dp = 72.dp,
    val productImageSmallWidth: Dp = 108.dp,
    val blurHeight: Dp = 16.dp,
    val smallProgressBarSize: Dp = 24.dp,
    val smsEditTextWidth: Dp = 320.dp,
    val scrollScreenBottomSpace: Dp = buttonHeight + 32.dp,
) {
    fun getItemSpaceByIndex(i: Int): Dp {
        return if (i == 0) {
            0.dp
        } else {
            smallSpace
        }
    }

    @Composable
    fun getButtonEvaluation(hasShadow: Boolean): ButtonElevation {
        return if (hasShadow) {
            ButtonDefaults.buttonElevation(
                defaultElevation = elevation
            )
        } else {
            ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp
            )
        }
    }

    @Composable
    fun cardEvaluation(hasShadow: Boolean = true): CardElevation {
        return CardDefaults.cardElevation(
            defaultElevation = if (hasShadow) {
                elevation
            } else {
                0.dp
            }
        )
    }
}
