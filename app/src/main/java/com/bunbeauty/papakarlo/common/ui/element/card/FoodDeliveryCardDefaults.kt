package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryCardDefaults {

    val cardColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.surface,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
        )
}