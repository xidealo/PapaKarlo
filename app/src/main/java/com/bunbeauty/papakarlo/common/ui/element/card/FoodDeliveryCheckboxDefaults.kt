package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryCheckboxDefaults {

    val checkboxColors: CheckboxColors
        @Composable get() = CheckboxDefaults.colors(
            checkedColor = FoodDeliveryTheme.colors.mainColors.primary,
            uncheckedColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
        )
}
