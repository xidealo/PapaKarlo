package com.bunbeauty.papakarlo.mapper

import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.order.LightOrder

class OrderItemMapper(
    private val stringUtil: IStringUtil,
) {
    fun toItem(order: LightOrder): OrderItem =
        OrderItem(
            uuid = order.uuid,
            status = order.status,
            statusName = stringUtil.getOrderStatusName(order.status),
            code = order.code,
            dateTime = stringUtil.getDateTimeString(order.dateTime),
        )
}
