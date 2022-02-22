package com.bunbeauty.papakarlo.util.color

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.enums.OrderStatus.*
import com.bunbeauty.papakarlo.R

class ColorUtil : IColorUtil {

    override fun getOrderStatusColorAttr(status: OrderStatus): Int {
        return when (status) {
            NOT_ACCEPTED -> R.attr.colorNotAccepted
            ACCEPTED -> R.attr.colorAccepted
            PREPARING -> R.attr.colorPreparing
            SENT_OUT -> R.attr.colorSentOut
            DONE -> R.attr.colorDone
            DELIVERED -> R.attr.colorDelivered
            CANCELED -> R.attr.colorCanceled
        }
    }
}