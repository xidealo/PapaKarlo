package com.bunbeauty.papakarlo.util.color

import com.bunbeauty.domain.enums.OrderStatus

interface IColorUtil {

    fun getOrderStatusColorAttr(status: OrderStatus): Int
}