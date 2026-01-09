package com.bunbeauty.designsystem.ui.element

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunbeauty.core.model.order.OrderStatus
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.icon16
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.description_order_details_done
import papakarlo.designsystem.generated.resources.ic_check

private val orderStatusShape = RoundedCornerShape(12.dp)
private val zeroStatusShape = RoundedCornerShape(0.dp)

@Composable
fun OrderStatusChip(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    statusName: String,
    background: Color,
) {
    Box(
        modifier =
            modifier
                .clip(orderStatusShape)
                .background(color = background),
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
    background: Color,
    roundedCornerShape: RoundedCornerShape? = null,
) {
    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .clip(shape = roundedCornerShape ?: zeroStatusShape)
                .background(color = background),
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
            background = FoodDeliveryTheme.colors.orderColors.sentOut
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
            background = FoodDeliveryTheme.colors.orderColors.sentOut
        )
    }
}
