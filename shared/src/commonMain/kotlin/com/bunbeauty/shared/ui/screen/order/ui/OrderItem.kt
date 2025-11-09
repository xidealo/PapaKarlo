package com.bunbeauty.shared.ui.screen.order.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.shared.ui.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.theme.bold
import com.bunbeauty.shared.domain.model.order.OrderStatus
import com.bunbeauty.shared.ui.screen.order.model.OrderItem

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    orderItem: OrderItem,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        onClick = onClick,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = orderItem.code,
                modifier =
                    Modifier
                        .requiredWidthIn(min = FoodDeliveryTheme.dimensions.codeWidth)
                        .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
            OrderStatusChip(orderStatus = orderItem.status, statusName = orderItem.statusName)
            Text(
                modifier =
                    Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.End),
                text = orderItem.dateTime,
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderItemPreview() {
    FoodDeliveryTheme {
        OrderItem(
            orderItem =
                OrderItem(
                    uuid = "",
                    status = OrderStatus.NOT_ACCEPTED,
                    statusName = "Обрабатывается",
                    code = "Щ-99",
                    dateTime = "9 февраля 22:00",
                ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderItemLageFontPreview() {
    FoodDeliveryTheme {
        OrderItem(
            orderItem =
                OrderItem(
                    uuid = "",
                    status = OrderStatus.NOT_ACCEPTED,
                    statusName = "Обрабатывается",
                    code = "Щ-99",
                    dateTime = "9 февраля 22:00",
                ),
            onClick = {},
        )
    }
}
