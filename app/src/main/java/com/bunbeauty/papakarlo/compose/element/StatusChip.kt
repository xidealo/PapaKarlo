package com.bunbeauty.papakarlo.compose.element

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.smallRoundedCornerShape

@Composable
fun StatusChip(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    statusName: String,
) {
    Surface(
        modifier = modifier
            .clip(smallRoundedCornerShape),
        color = FoodDeliveryTheme.colors.orderColor(orderStatus)
    ) {
        Text(
            text = statusName,
            modifier = Modifier
                .padding(
                    vertical = FoodDeliveryTheme.dimensions.smallSpace,
                    horizontal = FoodDeliveryTheme.dimensions.mediumSpace
                ),
            style = FoodDeliveryTheme.typography.h3,
            color = FoodDeliveryTheme.colors.onStatus,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun StatusChipPreview() {
    StatusChip(
        orderStatus = OrderStatus.NOT_ACCEPTED,
        statusName = "Обрабатывается"
    )
}