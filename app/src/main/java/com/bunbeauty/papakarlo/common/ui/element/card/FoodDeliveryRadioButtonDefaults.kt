package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryRadioButtonDefaults {
    val radioButtonColors: RadioButtonColors
        @Composable get() = RadioButtonDefaults.colors(
            selectedColor = FoodDeliveryTheme.colors.mainColors.primary,
            unselectedColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
        )
}
