package com.bunbeauty.papakarlo.common.mapper.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderStatusUI
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderUI
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem

interface IOrderUIMapper {

    fun toItem(order: LightOrder): OrderItem
    fun toOrderUI(order: Order): OrderUI
    fun toOrderStatusUI(orderStatus: OrderStatus): OrderStatusUI
}