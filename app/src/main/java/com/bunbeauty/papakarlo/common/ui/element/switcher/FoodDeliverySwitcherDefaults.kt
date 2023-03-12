package com.bunbeauty.papakarlo.common.ui.element.switcher

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliverySwitcherDefaults {

    val switcherButtonColor: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.secondary,
            contentColor = FoodDeliveryTheme.colors.mainColors.onSecondary,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.primary,
            disabledContentColor = FoodDeliveryTheme.colors.mainColors.onPrimary,
        )
}