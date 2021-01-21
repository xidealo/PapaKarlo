package com.bunbeauty.papakarlo.ui.creation_order

import com.bunbeauty.papakarlo.data.model.order.Order

interface CreationOrderNavigator {
    fun goToMain(order: Order)
    fun createDeliveryOrder()
    fun goToCreationAddress()
}