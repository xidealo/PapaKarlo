package com.bunbeauty.shared.data.mapper.orderavailable

import com.bunbeauty.shared.data.network.model.OrderAvailableServer
import com.bunbeauty.shared.domain.model.order.OrderAvailability

val mapOrderAvailableServerToOrderAvailability: OrderAvailableServer.() -> OrderAvailability =
    {
        OrderAvailability(
            available = available
        )
    }
