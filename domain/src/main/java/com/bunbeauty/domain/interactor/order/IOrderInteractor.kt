package com.bunbeauty.domain.interactor.order

import com.bunbeauty.domain.model.order.LightOrder
import com.bunbeauty.domain.model.order.Order

interface IOrderInteractor {

    fun getLightOrder(order: Order): LightOrder
}