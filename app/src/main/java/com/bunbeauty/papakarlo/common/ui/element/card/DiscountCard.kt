package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun DiscountCard(discount: String) {
    FoodDeliveryCard(
        colors = FoodDeliveryCardDefaults.positiveCardStatusColors,
        shape = FoodDeliveryCardDefaults.smallCardShape,
        elevated = false,
        clickable = false
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = discount,
            style = FoodDeliveryTheme.typography.bodyMedium,
            color = FoodDeliveryTheme.colors.statusColors.onStatus
        )
    }
}

@Preview
@Composable
fun DiscountCardPreview() {
    FoodDeliveryTheme {
        DiscountCard(discount = "10%")
    }
}
