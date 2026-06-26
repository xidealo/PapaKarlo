package com.bunbeauty.analytic.event.order

import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent

class OrderCreateClickEvent(
    isDelivery: Boolean,
    paymentMethod: String,
) : FoodDeliveryEvent(
        category = "order",
        action = "create_clk",
        params =
            listOf(
                EventParameter(
                    key = "isDelivery",
                    value = isDelivery.toString(),
                ),
                EventParameter(
                    key = "paymentMethod",
                    value = paymentMethod,
                ),
            ),
    )
