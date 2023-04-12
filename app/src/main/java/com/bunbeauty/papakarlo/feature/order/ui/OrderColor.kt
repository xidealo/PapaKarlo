package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.domain.model.order.OrderStatus

@Composable
fun getOrderColor(orderStatus: OrderStatus): Color {
    return when (orderStatus) {
        OrderStatus.NOT_ACCEPTED -> FoodDeliveryTheme.colors.orderColors.notAccepted
        OrderStatus.ACCEPTED -> FoodDeliveryTheme.colors.orderColors.accepted
        OrderStatus.PREPARING -> FoodDeliveryTheme.colors.orderColors.preparing
        OrderStatus.SENT_OUT -> FoodDeliveryTheme.colors.orderColors.sentOut
        OrderStatus.DONE -> FoodDeliveryTheme.colors.orderColors.done
        OrderStatus.DELIVERED -> FoodDeliveryTheme.colors.orderColors.delivered
        OrderStatus.CANCELED -> FoodDeliveryTheme.colors.orderColors.canceled
    }
}
