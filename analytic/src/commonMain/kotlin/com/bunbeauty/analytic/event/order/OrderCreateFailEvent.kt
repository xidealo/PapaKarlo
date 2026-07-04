package com.bunbeauty.analytic.event.order

import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent

class OrderCreateFailEvent(error: String) :
    FoodDeliveryEvent(
        category = "order",
        action = "create_fail",
        params =
            listOf(
                EventParameter(
                    key = "error",
                    value = error,
                ),
            ),
    )
