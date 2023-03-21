package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.element.button.FoodDeliveryButtonDefaults
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryCardDefaults {

    private val cardElevation: CardElevation
        @Composable get() = CardDefaults.cardElevation(2.dp)

    private val zeroCardElevation: CardElevation
        @Composable get() = CardDefaults.cardElevation(0.dp)

    val cardColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.surface,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
        )

    @Composable
    fun getCardElevation(elevated: Boolean): CardElevation = if (elevated) {
        cardElevation
    } else {
        zeroCardElevation
    }
}
