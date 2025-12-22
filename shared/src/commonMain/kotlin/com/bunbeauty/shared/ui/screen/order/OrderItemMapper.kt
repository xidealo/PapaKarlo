package com.bunbeauty.shared.ui.screen.order

import androidx.compose.runtime.Composable
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.ui.common.getDateTimeString
import com.bunbeauty.shared.ui.screen.order.model.OrderItem
import com.bunbeauty.shared.ui.screen.order.ui.getOrderStatusName

@Composable
fun LightOrder.toItem(): OrderItem =
    OrderItem(
        uuid = uuid,
        status = status,
        statusName = status.getOrderStatusName(),
        code = code,
        dateTime = dateTime.getDateTimeString(),
    )
