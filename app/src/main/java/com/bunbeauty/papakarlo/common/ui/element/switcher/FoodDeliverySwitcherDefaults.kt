package com.bunbeauty.papakarlo.common.ui.element.switcher

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliverySwitcherDefaults {

    val switcherButtonColor: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.stroke,
            contentColor = FoodDeliveryTheme.colors.mainColors.onSecondary,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.secondary,
            disabledContentColor = FoodDeliveryTheme.colors.mainColors.primary
        )

    val switcherShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(FoodDeliveryTheme.dimensions.switcherRadius)

    val switcherButtonShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(FoodDeliveryTheme.dimensions.switcherButtonRadius)
}
