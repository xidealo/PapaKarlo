package com.bunbeauty.presentation.util.color

import com.bunbeauty.domain.R
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.enums.OrderStatus.*
import javax.inject.Inject

class ColorUtil @Inject constructor() : IColorUtil {

    override fun getOrderStatusColor(status: OrderStatus): Int {
        return when (status) {
            NOT_ACCEPTED -> R.color.acceptedColor
            ACCEPTED -> R.color.acceptedColor
            PREPARING -> R.color.preparingColor
            SENT_OUT -> R.color.sentOutColor
            DONE -> R.color.doneColor
            DELIVERED -> R.color.deliveredColor
            CANCELED -> R.color.canceledColor
        }
    }
}