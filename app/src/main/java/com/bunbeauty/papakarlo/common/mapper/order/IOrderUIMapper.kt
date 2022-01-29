package com.bunbeauty.papakarlo.common.mapper.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderUI
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderItem

interface IOrderUIMapper {

    fun toItem(order: LightOrder): OrderItem
    fun toUI(orderDetails: OrderDetails): OrderUI
}