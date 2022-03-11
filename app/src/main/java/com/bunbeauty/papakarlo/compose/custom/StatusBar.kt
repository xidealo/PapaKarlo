package com.bunbeauty.papakarlo.compose.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.element.StatusChip
import com.bunbeauty.papakarlo.compose.icon
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.compose.theme.smallRoundedCornerShape

@Composable
fun OrderStatusBar(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    orderStatusName: String
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .shadow(
                elevation = FoodDeliveryTheme.dimensions.elevation,
                shape = mediumRoundedCornerShape
            )
            .clip(mediumRoundedCornerShape),
        backgroundColor = FoodDeliveryTheme.colors.surface
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
                        DoneStep(
                            modifier = Modifier
                                .padding(start = startSpace)
                                .weight(1f),
                            orderStatus = orderStatus
                        )
                    }
                    (i == currentStep) -> {
                        StatusChip(
                            modifier = Modifier
                                .padding(start = startSpace),
                            orderStatus = orderStatus,
                            statusName = orderStatusName
                        )
                    }
                    (i > currentStep) -> {
                        FutureStep(
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
fun DoneStep(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(smallRoundedCornerShape)
            .background(FoodDeliveryTheme.colors.orderColor(orderStatus))
    ) {
        Icon(
            modifier = Modifier
                .icon()
                .padding(
                    vertical = FoodDeliveryTheme.dimensions.verySmallSpace
                )
                .align(Alignment.Center),
            imageVector = ImageVector.vectorResource(R.drawable.ic_check),
            contentDescription = stringResource(R.string.description_order_details_done),
            tint = FoodDeliveryTheme.colors.onStatus
        )
    }
}

@Composable
fun FutureStep(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(smallRoundedCornerShape)
            .background(FoodDeliveryTheme.colors.surfaceVariant)
    )
}

@Preview
@Composable
fun StatusBarNotAcceptedPreview() {
    OrderStatusBar(
        orderStatus = OrderStatus.NOT_ACCEPTED,
        orderStatusName = "Обрабатывается"
    )
}

@Preview
@Composable
fun StatusBarAcceptedPreview() {
    OrderStatusBar(
        orderStatus = OrderStatus.ACCEPTED,
        orderStatusName = "Принят"
    )
}

@Preview
@Composable
fun StatusBarPreparingPreview() {
    OrderStatusBar(
        orderStatus = OrderStatus.PREPARING,
        orderStatusName = "Готовится"
    )
}

@Preview
@Composable
fun StatusBarDonePreview() {
    OrderStatusBar(
        orderStatus = OrderStatus.DONE,
        orderStatusName = "Готов"
    )
}

@Preview
@Composable
fun StatusBarSentOutPreview() {
    OrderStatusBar(
        orderStatus = OrderStatus.SENT_OUT,
        orderStatusName = "В пути"
    )
}

@Preview
@Composable
fun StatusBarSentDeliveredPreview() {
    OrderStatusBar(
        orderStatus = OrderStatus.DELIVERED,
        orderStatusName = "Выдан"
    )
}

@Preview
@Composable
fun StatusBarSentCanceledPreview() {
    OrderStatusBar(
        orderStatus = OrderStatus.CANCELED,
        orderStatusName = "Отменен"
    )
}