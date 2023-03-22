package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.shared.domain.model.order.OrderStatus

@Composable
fun OrderStatusChip(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    statusName: String,
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        color = getOrderColor(orderStatus)
    ) {
        Text(
            text = statusName,
            modifier = Modifier
                .padding(
                    vertical = FoodDeliveryTheme.dimensions.verySmallSpace,
                    horizontal = FoodDeliveryTheme.dimensions.smallSpace
                ),
            style = FoodDeliveryTheme.typography.labelSmall.medium,
            color = FoodDeliveryTheme.colors.orderColors.onOrder,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun StatusChipPreview() {
    FoodDeliveryTheme {
        OrderStatusChip(
            orderStatus = OrderStatus.NOT_ACCEPTED,
            statusName = "Обрабатывается"
        )
    }
}
