package com.bunbeauty.presentation.mapper.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.util.color.IColorUtil
import com.bunbeauty.presentation.util.string.IStringUtil
import javax.inject.Inject

class OrderUIMapper @Inject constructor(
    private val stringUtil: IStringUtil,
    private val colorUtil: IColorUtil,
) : IOrderUIMapper {

    override fun toItem(order: LightOrder): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringUtil.getOrderStatusString(order.status),
            orderColorResource = colorUtil.getOrderStatusColor(order.status),
            code = order.code,
            dateTime = order.dateTime
        )
    }
}