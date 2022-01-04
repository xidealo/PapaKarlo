package com.bunbeauty.presentation.mapper.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.presentation.item.OrderItem

interface IOrderUIMapper {

    fun toItem(order: LightOrder): OrderItem
}