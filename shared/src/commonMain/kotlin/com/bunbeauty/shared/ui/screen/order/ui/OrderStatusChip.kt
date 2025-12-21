package com.bunbeauty.shared.ui.screen.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.icon16
import com.bunbeauty.shared.domain.model.order.OrderStatus
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.description_order_details_done
import papakarlo.shared.generated.resources.ic_check
import papakarlo.shared.generated.resources.msg_status_accepted
import papakarlo.shared.generated.resources.msg_status_canceled
import papakarlo.shared.generated.resources.msg_status_delivered
import papakarlo.shared.generated.resources.msg_status_done
import papakarlo.shared.generated.resources.msg_status_not_accepted
import papakarlo.shared.generated.resources.msg_status_preparing
import papakarlo.shared.generated.resources.msg_status_sent_out

private val orderStatusShape = RoundedCornerShape(12.dp)
private val zeroStatusShape = RoundedCornerShape(0.dp)

@Composable
fun OrderStatus.getOrderStatusName(): String =
    when (this) {
        OrderStatus.NOT_ACCEPTED -> stringResource(Res.string.msg_status_not_accepted)
        OrderStatus.ACCEPTED -> stringResource(Res.string.msg_status_accepted)
        OrderStatus.PREPARING -> stringResource(Res.string.msg_status_preparing)
        OrderStatus.SENT_OUT -> stringResource(Res.string.msg_status_sent_out)
        OrderStatus.DELIVERED -> stringResource(Res.string.msg_status_delivered)
        OrderStatus.DONE -> stringResource(Res.string.msg_status_done)
        OrderStatus.CANCELED -> stringResource(Res.string.msg_status_canceled)
    }

@Composable
fun OrderStatusChip(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    statusName: String,
) {
    Box(
        modifier =
            modifier
                .clip(orderStatusShape)
                .background(getOrderColor(orderStatus)),
    ) {
        Text(
            text = statusName,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            style = FoodDeliveryTheme.typography.labelSmall.medium,
            color = FoodDeliveryTheme.colors.orderColors.onOrder,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun EmptyOrderStatusChip(
    modifier: Modifier = Modifier,
    roundedCornerShape: RoundedCornerShape? = null,
) {
    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .clip(shape = roundedCornerShape ?: zeroStatusShape)
                .background(FoodDeliveryTheme.colors.mainColors.disabled),
    )
}

@Composable
fun PassedOrderStatusChip(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    roundedCornerShape: RoundedCornerShape? = null,
) {
    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .clip(shape = roundedCornerShape ?: zeroStatusShape)
                .background(getOrderColor(orderStatus)),
    ) {
        Icon(
            modifier =
                Modifier
                    .icon16()
                    .align(Alignment.Center),
            painter = painterResource(Res.drawable.ic_check),
            contentDescription = stringResource(Res.string.description_order_details_done),
            tint = FoodDeliveryTheme.colors.orderColors.onOrder,
        )
    }
}

@Preview
@Composable
private fun StatusChipPreview() {
    FoodDeliveryTheme {
        OrderStatusChip(
            orderStatus = OrderStatus.ACCEPTED,
            statusName = "Принят",
        )
    }
}

@Preview(heightDp = 24)
@Composable
private fun EmptyOrderStatusChipPreview() {
    FoodDeliveryTheme {
        EmptyOrderStatusChip(
            modifier = Modifier.width(100.dp),
        )
    }
}

@Preview(heightDp = 24)
@Composable
private fun PassedOrderStatusChipPreview() {
    FoodDeliveryTheme {
        PassedOrderStatusChip(
            modifier = Modifier.width(100.dp),
            orderStatus = OrderStatus.ACCEPTED,
        )
    }
}
