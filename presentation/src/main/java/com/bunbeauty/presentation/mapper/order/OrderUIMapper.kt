package com.bunbeauty.presentation.mapper.order

import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.util.string.IStringUtil
import javax.inject.Inject

class OrderUIMapper @Inject constructor(
    private val dateTimeUtil: IDateTimeUtil,
    private val stringUtil: IStringUtil,
    private val orderUtil: IOrderUtil,
) : IOrderUIMapper {

    override fun toItem(order: Order): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringUtil.getOrderStatusString(order.orderStatus),
            orderColorResource = orderUtil.getBackgroundColor(order.orderStatus),
            code = order.code,
            dateTime = dateTimeUtil.getTimeDDMMMMHHMM(order.time)
        )
    }
}