package com.bunbeauty.presentation.mapper.order

import com.bunbeauty.domain.model.Order
import com.bunbeauty.presentation.item.OrderItem

interface IOrderUIMapper {

    fun toItem(order: Order): OrderItem
}