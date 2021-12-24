package com.bunbeauty.presentation.util.color

import com.bunbeauty.domain.enums.OrderStatus

interface IColorUtil {

    fun getOrderStatusColor(status: OrderStatus): Int
}