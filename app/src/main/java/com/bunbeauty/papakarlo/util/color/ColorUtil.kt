package com.bunbeauty.papakarlo.util.color

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.enums.OrderStatus.*
import com.bunbeauty.papakarlo.R
import javax.inject.Inject

class ColorUtil @Inject constructor() : IColorUtil {

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