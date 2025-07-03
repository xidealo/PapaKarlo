package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.domain.model.order.OrderStatus


const val FIRST_POSITION = 0
const val LAST_POSITION = 4

@Composable
fun OrderStatusBar(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    orderStatusName: String
) {
    FoodDeliveryCard(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        clickable = false,
        elevated = false
    ) {
        val currentStep = when (orderStatus) {
            OrderStatus.NOT_ACCEPTED -> 0
            OrderStatus.ACCEPTED -> 1
            OrderStatus.PREPARING -> 2
            OrderStatus.SENT_OUT -> 3
            OrderStatus.DONE -> 3
            OrderStatus.DELIVERED -> 4
            OrderStatus.CANCELED -> 4
        }

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    painter = painterResource(id = getIcon(status = orderStatus)),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(
                        start = 8.dp
                    ),
                    text = orderStatusName,
                    style = FoodDeliveryTheme.typography.bodyMedium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                    .padding(vertical = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                repeat(5) { i ->
                    val startSpace = if (i == 0) {
                        0.dp
                    } else {
                        FoodDeliveryTheme.dimensions.smallSpace
                    }

                    when {
                        (i <= currentStep) -> {
                            PassedOrderStatusChip(
                                modifier = Modifier
                                    .padding(start = startSpace)
                                    .weight(1f),
                                orderStatus = orderStatus,
                                roundedCornerShape = getRoundedShape(i)
                            )
                        }

                        else -> {
                            EmptyOrderStatusChip(
                                modifier = Modifier
                                    .padding(start = startSpace)
                                    .weight(1f),
                                roundedCornerShape = getRoundedShape(i)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun getRoundedShape(i: Int): RoundedCornerShape? {
    return when (i) {
        FIRST_POSITION -> RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 12.dp
        )

        LAST_POSITION -> RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 12.dp,
            bottomEnd = 12.dp,
            bottomStart = 0.dp
        )

        else -> null
    }
}

@Composable
private fun getIcon(status: OrderStatus): Int {
    return when (status) {
        OrderStatus.NOT_ACCEPTED -> R.drawable.ic_not_accpted
        OrderStatus.ACCEPTED -> R.drawable.ic_accpted
        OrderStatus.PREPARING -> R.drawable.ic_preparing
        OrderStatus.SENT_OUT -> R.drawable.ic_delivering
        OrderStatus.DONE -> R.drawable.ic_done
        OrderStatus.DELIVERED -> R.drawable.ic_delivered
        OrderStatus.CANCELED -> R.drawable.ic_canceled
    }
}

@Preview
@Composable
private fun StatusBarNotAcceptedPreview() {
    FoodDeliveryTheme {
        OrderStatusBar(
            orderStatus = OrderStatus.NOT_ACCEPTED,
            orderStatusName = "Обрабатывается"
        )
    }
}

@Preview
@Composable
fun StatusBarAcceptedPreview() {
    FoodDeliveryTheme {
        OrderStatusBar(
            orderStatus = OrderStatus.ACCEPTED,
            orderStatusName = "Принят"
        )
    }
}

@Preview
@Composable
fun StatusBarPreparingPreview() {
    FoodDeliveryTheme {
        OrderStatusBar(
            orderStatus = OrderStatus.PREPARING,
            orderStatusName = "Готовится"
        )
    }
}

@Preview
@Composable
fun StatusBarDonePreview() {
    FoodDeliveryTheme {
        OrderStatusBar(
            orderStatus = OrderStatus.DONE,
            orderStatusName = "Готов"
        )
    }
}

@Preview
@Composable
fun StatusBarSentOutPreview() {
    FoodDeliveryTheme {
        OrderStatusBar(
            orderStatus = OrderStatus.SENT_OUT,
            orderStatusName = "В пути"
        )
    }
}

@Preview
@Composable
fun StatusBarSentDeliveredPreview() {
    FoodDeliveryTheme {
        OrderStatusBar(
            orderStatus = OrderStatus.DELIVERED,
            orderStatusName = "Выдан"
        )
    }
}

@Preview
@Composable
fun StatusBarSentCanceledPreview() {
    FoodDeliveryTheme {
        OrderStatusBar(
            orderStatus = OrderStatus.CANCELED,
            orderStatusName = "Отменен"
        )
    }
}
