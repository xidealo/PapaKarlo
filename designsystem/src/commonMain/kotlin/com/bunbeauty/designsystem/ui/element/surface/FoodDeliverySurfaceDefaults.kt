package com.bunbeauty.designsystem.ui.element.surface

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme

object FoodDeliverySurfaceDefaults {
    @Composable
    fun getSurfaceElevation(elevated: Boolean) =
        if (elevated) {
            FoodDeliveryTheme.dimensions.surfaceElevation
        } else {
            0.dp
        }
}
