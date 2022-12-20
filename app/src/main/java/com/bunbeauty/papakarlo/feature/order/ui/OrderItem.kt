package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.element.StatusChip
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.shared.domain.model.order.OrderStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    orderItem: OrderItem,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.cardHeight),
        shape = mediumRoundedCornerShape,
        colors = FoodDeliveryTheme.colors.cardColors(),
        elevation = FoodDeliveryTheme.dimensions.cardEvaluation(),
        onClick = onClick
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
