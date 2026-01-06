package com.bunbeauty.shared.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntOffset

/**
* Animation constants for navigation
* */

object NavAnimationSpec {
    val navAnimationSpecDurationForSlide = tween<IntOffset>(durationMillis = 300)
    val navAnimationSpecDurationForEnterFade: FiniteAnimationSpec<Float> =
        tween(
            durationMillis = 220,
            easing = FastOutSlowInEasing,
        )

    val navAnimationSpecDurationForPopFade: FiniteAnimationSpec<Float> =
        tween(
            durationMillis = 180,
            easing = LinearOutSlowInEasing,
        )

    const val NAV_ANIMATION_SPEC_SCALE_FOR_FADE = 0.95f
}
