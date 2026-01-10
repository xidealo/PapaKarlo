package com.bunbeauty.order.ui

import androidx.compose.runtime.Composable
import com.bunbeauty.core.extension.getOrderColor
import com.bunbeauty.core.extension.getOrderStatusName
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.extension.getDateTimeString
import com.bunbeauty.order.ui.model.OrderItem

@Composable
fun LightOrder.toItem(): OrderItem = OrderItem(
    uuid = uuid,
    status = status,
    statusName = status.getOrderStatusName(),
    code = code,
    dateTime = dateTime.getDateTimeString(),
    background = status.getOrderColor()
)
