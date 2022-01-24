package com.bunbeauty.presentation.mapper.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.model.OrderUI

interface IOrderUIMapper {

    fun toItem(order: LightOrder): OrderItem
    fun toUI(orderDetails: OrderDetails): OrderUI
}