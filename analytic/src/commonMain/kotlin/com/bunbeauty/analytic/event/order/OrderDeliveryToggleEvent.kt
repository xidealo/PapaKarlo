package com.bunbeauty.analytic.event.order

import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent

class OrderDeliveryToggleEvent(isDelivery: Boolean) :
    FoodDeliveryEvent(
        category = "order",
        action = "delivery_toggle",
        params =
            listOf(
                EventParameter(
                    key = "isDelivery",
                    value = isDelivery.toString(),
                ),
            ),
    )
