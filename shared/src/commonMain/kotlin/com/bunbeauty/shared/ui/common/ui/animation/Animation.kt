package com.bunbeauty.shared.ui.common.ui.animation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with

@OptIn(ExperimentalAnimationApi::class)
val slideInAndSlideOutVerticallyWithFadeAnimation =
    fadeIn(
        animationSpec =
            tween(
                durationMillis = 220,
                delayMillis = 90,
            ),
    ) +
        slideInVertically(
            animationSpec =
                tween(
                    durationMillis = 220,
                    delayMillis = 90,
                ),
        ) with fadeOut(
            animationSpec =
                tween(
                    durationMillis = 220,
                ),
        ) +
        slideOutVertically(
            animationSpec =
                tween(
                    durationMillis = 220,
                ),
        )
