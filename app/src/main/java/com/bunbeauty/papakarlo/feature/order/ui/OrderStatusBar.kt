package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.domain.model.order.OrderStatus

private val stepShape = RoundedCornerShape(12.dp)

@Composable
fun OrderStatusBar(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    orderStatusName: String,
) {
    FoodDeliveryCard(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        clickable = false,
    ) {
        val currentStep = when (orderStatus) {
            OrderStatus.NOT_ACCEPTED -> 0
            OrderStatus.ACCEPTED -> 1
            OrderStatus.PREPARING -> 2
            OrderStatus.SENT_OUT -> 3
            OrderStatus.DONE -> 3
            OrderStatus.DELIVERED -> 4
            OrderStatus.CANCELED -> 0
        }
        Row(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace)) {
            repeat(5) { i ->
                val startSpace = if (i == 0) {
                    0.dp
                } else {
                    FoodDeliveryTheme.dimensions.smallSpace
                }
                when {
                    (i < currentStep) -> {
                        PassedOrderStatusChip(
                            modifier = Modifier
                                .padding(start = startSpace)
                                .weight(1f),
                            orderStatus = orderStatus
                        )
                    }
                    (i == currentStep) -> {
                        OrderStatusChip(
                            modifier = Modifier
                                .padding(start = startSpace),
                            orderStatus = orderStatus,
                            statusName = orderStatusName
                        )
                    }
                    else -> {
                        EmptyOrderStatusChip(
                            modifier = Modifier
                                .padding(start = startSpace)
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DoneStep(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(stepShape)
            .background(getOrderColor(orderStatus))
    ) {
        Icon(
            modifier = Modifier
                .icon16()
                .align(Alignment.Center),
            painter = painterResource(R.drawable.ic_check),
            contentDescription = stringResource(R.string.description_order_details_done),
            tint = FoodDeliveryTheme.colors.orderColors.onOrder
        )
    }
}

@Composable
private fun FutureStep(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(stepShape)
            .background(FoodDeliveryTheme.colors.mainColors.disabled)
    )
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
