package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order

interface IOrderMapper {

    fun toLightOrder(order: Order): LightOrder
}