package com.bunbeauty.shared.ui.screen.order

import androidx.compose.runtime.Composable
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.profile.ui.screen.profile.getOrderStatusName
import com.bunbeauty.designsystem.ui.getDateTimeString
import com.bunbeauty.profile.ui.screen.profile.getOrderColor
import com.bunbeauty.shared.ui.screen.order.model.OrderItem

@Composable
fun LightOrder.toItem(): OrderItem = OrderItem(
    uuid = uuid,
    status = status,
    statusName = status.getOrderStatusName(),
    code = code,
    dateTime = dateTime.getDateTimeString(),
    background = status.getOrderColor()
)
