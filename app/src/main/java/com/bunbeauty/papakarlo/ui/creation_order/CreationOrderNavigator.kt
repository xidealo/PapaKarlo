package com.bunbeauty.papakarlo.ui.creation_order

import com.bunbeauty.data.model.order.OrderEntity

interface CreationOrderNavigator {
    fun goToMain(orderEntity: OrderEntity)
    fun createDeliveryOrder()
    fun goToCreationAddress()
}