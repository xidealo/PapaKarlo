package com.bunbeauty.shared.ui.common.ui.element.surface

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme

object FoodDeliverySurfaceDefaults {
    @Composable
    fun getSurfaceElevation(elevated: Boolean) =
        if (elevated) {
            FoodDeliveryTheme.dimensions.surfaceElevation
        } else {
            0.dp
        }
}
