package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryCardDefaults {

    val cardColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.surface,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.surface
        )

    val cardShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(FoodDeliveryTheme.dimensions.cardRadius)

    @Composable
    fun getCardElevation(elevated: Boolean): CardElevation = if (elevated) {
        CardDefaults.cardElevation(
            defaultElevation = FoodDeliveryTheme.dimensions.cardElevation,
            disabledElevation = FoodDeliveryTheme.dimensions.cardElevation
        )
    } else {
        CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            disabledElevation = 0.dp
        )
    }
}
