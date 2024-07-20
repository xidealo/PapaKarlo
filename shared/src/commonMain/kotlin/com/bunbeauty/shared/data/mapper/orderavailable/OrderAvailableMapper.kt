package com.bunbeauty.shared.data.mapper.orderavailable

import com.bunbeauty.shared.data.network.model.OrderAvailableServer
import com.bunbeauty.shared.domain.model.addition.OrderAddition
import com.bunbeauty.shared.domain.model.order.OrderAvailable


val mapOrderAvailableServerToOrderAvailable: OrderAvailableServer.() -> OrderAvailable =
    {
        OrderAvailable(
            available = available
        )
    }
