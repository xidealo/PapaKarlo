package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryCardDefaults {

    val cardColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.surface,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.surface
        )

    val switcherColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.stroke,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.stroke
        )

    val transparentCardColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )

    val positiveCardStatusColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.statusColors.positive
        )

    val warningCardStatusColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.statusColors.warning
        )

    val negativeCardStatusColors: CardColors
        @Composable get() = CardDefaults.cardColors(
            containerColor = FoodDeliveryTheme.colors.statusColors.negative
        )

    val cardShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(8.dp)

    val infoCardShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(16.dp)

    val smallCardShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(4.dp)

    val zeroCardShape: Shape
        @Composable get() = RectangleShape

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
