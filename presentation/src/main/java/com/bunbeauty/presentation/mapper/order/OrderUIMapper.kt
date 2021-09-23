package com.bunbeauty.presentation.mapper.order

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.enums.OrderStatus.*
import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.presentation.R
import com.bunbeauty.presentation.item.OrderItem
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import javax.inject.Inject

class OrderUIMapper @Inject constructor(
    private val dateTimeUtil: IDateTimeUtil,
    private val stringUtil: IStringUtil,
    private val resourcesProvider: IResourcesProvider,
) : IOrderUIMapper {

    override fun toItem(order: Order): OrderItem {
        return OrderItem(
            uuid = order.uuid,
            orderStatus = stringUtil.getOrderStatusString(order.orderStatus),
            orderColorResource = getOrderColorResource(order.orderStatus),
            code = order.code,
            dateTime = dateTimeUtil.getTimeHHMM(order.time)
        )
    }

    fun getOrderColorResource(orderStatus: OrderStatus): Int {
        return when (orderStatus) {
            NOT_ACCEPTED -> R.color.acceptedColor
            ACCEPTED -> R.color.acceptedColor
            PREPARING -> R.color.preparingColor
            SENT_OUT -> R.color.sentOutColor
            DELIVERED -> R.color.deliveredColor
            DONE -> R.color.doneColor
            CANCELED -> R.color.canceledColor
        }
    }
}