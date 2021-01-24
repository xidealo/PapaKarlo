package com.bunbeauty.papakarlo.ui.creation_order

import com.bunbeauty.papakarlo.data.model.order.OrderEntity

interface CreationOrderNavigator {
    fun goToMain(orderEntity: OrderEntity)
    fun createDeliveryOrder()
    fun goToCreationAddress()
}