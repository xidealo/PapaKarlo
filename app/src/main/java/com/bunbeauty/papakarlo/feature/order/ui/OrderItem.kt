package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.card
import com.bunbeauty.papakarlo.common.ui.element.StatusChip
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.shared.domain.model.order.OrderStatus

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    orderItem: OrderItem,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .card(true)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = FoodDeliveryTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = orderItem.code,
                modifier = Modifier
                    .requiredWidthIn(min = FoodDeliveryTheme.dimensions.codeWidth)
                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                style = FoodDeliveryTheme.typography.h2,
                color = FoodDeliveryTheme.colors.onSurface
            )
            StatusChip(orderStatus = orderItem.status, statusName = orderItem.statusName)
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End),
                text = orderItem.dateTime,
                style = FoodDeliveryTheme.typography.body2,
                color = FoodDeliveryTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
fun OrderItemPreview() {
    OrderItem(
        orderItem = OrderItem(
            uuid = "",
            status = OrderStatus.NOT_ACCEPTED,
            statusName = "Обрабатывается",
            code = "Щ-99",
            dateTime = "9 февраля 22:00"
        )
    ) {}
}

@Preview(fontScale = 1.5f)
@Composable
fun OrderItemLageFontPreview() {
    OrderItem(
        orderItem = OrderItem(
            uuid = "",
            status = OrderStatus.NOT_ACCEPTED,
            statusName = "Обрабатывается",
            code = "Щ-99",
            dateTime = "9 февраля 22:00"
        )
    ) {}
}
