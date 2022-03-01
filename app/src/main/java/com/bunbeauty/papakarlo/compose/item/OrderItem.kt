package com.bunbeauty.papakarlo.compose.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.compose.theme.smallRoundedCornerShape
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItemModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    orderItem: OrderItemModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
            .shadow(1.dp, mediumRoundedCornerShape)
            .clip(mediumRoundedCornerShape)
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
            Surface(
                modifier = Modifier
                    .clip(smallRoundedCornerShape),
                color = getOrderColor(orderItem.status)
            ) {
                Text(
                    text = orderItem.statusName,
                    modifier = Modifier
                        .padding(
                            vertical = FoodDeliveryTheme.dimensions.smallSpace,
                            horizontal = FoodDeliveryTheme.dimensions.mediumSpace
                        ),
                    style = FoodDeliveryTheme.typography.h3,
                    color = FoodDeliveryTheme.colors.onStatus
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = orderItem.dateTime,
                    style = FoodDeliveryTheme.typography.body2,
                    color = FoodDeliveryTheme.colors.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun getOrderColor(orderStatus: OrderStatus): Color {
    return when (orderStatus) {
        OrderStatus.NOT_ACCEPTED -> FoodDeliveryTheme.colors.notAccepted
        OrderStatus.ACCEPTED -> FoodDeliveryTheme.colors.accepted
        OrderStatus.PREPARING -> FoodDeliveryTheme.colors.preparing
        OrderStatus.SENT_OUT -> FoodDeliveryTheme.colors.sentOut
        OrderStatus.DONE -> FoodDeliveryTheme.colors.done
        OrderStatus.DELIVERED -> FoodDeliveryTheme.colors.delivered
        OrderStatus.CANCELED -> FoodDeliveryTheme.colors.canceled
    }
}

@Preview
@Composable
fun OrderItemPreview() {
    OrderItem(
        orderItem = OrderItemModel(
            uuid = "",
            status = OrderStatus.NOT_ACCEPTED,
            statusName = "Обрабатывается",
            statusColorId = R.color.white,
            code = "Щ-99",
            dateTime = "9 февраля 22:00"
        )
    ) {}
}

@Preview(fontScale = 1.5f)
@Composable
fun OrderItemLageFontPreview() {
    OrderItem(
        orderItem = OrderItemModel(
            uuid = "",
            status = OrderStatus.NOT_ACCEPTED,
            statusName = "Обрабатывается",
            statusColorId = R.color.white,
            code = "Щ-99",
            dateTime = "9 февраля 22:00"
        )
    ) {}
}
