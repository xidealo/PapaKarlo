package com.bunbeauty.data.mapper

import com.bunbeauty.common.Constants
import com.bunbeauty.domain.mapper.IOrderMapper
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import org.joda.time.DateTime
import javax.inject.Inject

class OrderMapper @Inject constructor() : IOrderMapper {

    override fun toLightOrder(order: Order): LightOrder {
        return LightOrder(
            uuid = order.uuid,
            status = order.status,
            code = order.code,
            dateTime = getOrderDateTime(order.time)
        )
    }

    fun getOrderDateTime(time: Long): String {
        return DateTime(time).toString(Constants.DD_MMMM_HH_MM_PATTERN)
    }
}