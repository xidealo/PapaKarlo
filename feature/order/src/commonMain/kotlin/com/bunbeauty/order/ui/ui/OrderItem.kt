package com.bunbeauty.order.ui.ui

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
import androidx.compose.ui.unit.dp
import com.bunbeauty.core.extension.getOrderColor
import com.bunbeauty.core.model.order.OrderStatus
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.OrderStatusChip
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.order.ui.model.OrderItem
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            OrderStatusChip(
                statusName = orderItem.statusName,
                background = orderItem.background,
            )
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
                    background = OrderStatus.NOT_ACCEPTED.getOrderColor(),
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
                    background = OrderStatus.NOT_ACCEPTED.getOrderColor(),
                ),
            onClick = {},
        )
    }
}
