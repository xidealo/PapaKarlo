package com.bunbeauty.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.core.model.order.OrderStatus
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.msg_status_accepted
import papakarlo.designsystem.generated.resources.msg_status_canceled
import papakarlo.designsystem.generated.resources.msg_status_delivered
import papakarlo.designsystem.generated.resources.msg_status_done
import papakarlo.designsystem.generated.resources.msg_status_not_accepted
import papakarlo.designsystem.generated.resources.msg_status_preparing
import papakarlo.designsystem.generated.resources.msg_status_sent_out

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
fun OrderStatus.getOrderColor(): Color =
    when (this) {
        OrderStatus.NOT_ACCEPTED -> FoodDeliveryTheme.colors.orderColors.notAccepted
        OrderStatus.ACCEPTED -> FoodDeliveryTheme.colors.orderColors.accepted
        OrderStatus.PREPARING -> FoodDeliveryTheme.colors.orderColors.preparing
        OrderStatus.SENT_OUT -> FoodDeliveryTheme.colors.orderColors.sentOut
        OrderStatus.DONE -> FoodDeliveryTheme.colors.orderColors.done
        OrderStatus.DELIVERED -> FoodDeliveryTheme.colors.orderColors.delivered
        OrderStatus.CANCELED -> FoodDeliveryTheme.colors.orderColors.canceled
    }