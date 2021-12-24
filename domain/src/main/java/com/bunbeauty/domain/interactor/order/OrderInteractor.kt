package com.bunbeauty.domain.interactor.order

import com.bunbeauty.common.Constants
import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order
import org.joda.time.DateTime
import javax.inject.Inject

class OrderInteractor @Inject constructor() : IOrderInteractor {

    override fun getLightOrder(order: Order): LightOrder {
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