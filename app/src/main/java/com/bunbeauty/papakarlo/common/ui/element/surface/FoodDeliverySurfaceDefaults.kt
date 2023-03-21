package com.bunbeauty.papakarlo.common.ui.element.surface

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object FoodDeliverySurfaceDefaults {

    private val surfaceElevation: Dp = 4.dp
    private val zeroSurfaceElevation: Dp = 0.dp

    fun getSurfaceElevation(elevated: Boolean) = if (elevated) {
        surfaceElevation
    } else {
        zeroSurfaceElevation
    }
}
