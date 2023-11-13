package com.bunbeauty.papakarlo.common.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }

data class AppDimensions(
    val screenContentSpace: Dp = 16.dp,

    val buttonRadius: Dp = 20.dp,
    val bottomSheetRadius: Dp = 16.dp,

    val switcherRadius: Dp = 24.dp,
    val switcherButtonRadius: Dp = 20.dp,

    val cardRadius: Dp = 8.dp,
    val cardMediumInnerSpace: Dp = 12.dp,
    val cardLargeInnerSpace: Dp = 16.dp,

    val verySmallSpace: Dp = 4.dp,
    val smallSpace: Dp = 8.dp,
    val mediumSpace: Dp = 16.dp,
    val largeSpace: Dp = 24.dp,

    val cardElevation: Dp = 4.dp,
    val surfaceElevation: Dp = 6.dp,

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
    val scrollScreenBottomSpace: Dp = buttonHeight + 32.dp
)
