package com.bunbeauty.papakarlo.mapper.order

import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.order.OrderWithAmounts
import com.bunbeauty.papakarlo.feature.order.model.OrderUI
import com.bunbeauty.papakarlo.feature.order.model.OrderItem

interface IOrderUIMapper {

    fun toItem(order: LightOrder): OrderItem
    fun toOrderUI(orderWithAmounts: OrderWithAmounts): OrderUI
}