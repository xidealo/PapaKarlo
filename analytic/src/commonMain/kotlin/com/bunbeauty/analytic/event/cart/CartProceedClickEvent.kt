package com.bunbeauty.analytic.event.cart

import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent

class CartProceedClickEvent(isAuthorized: Boolean) :
    FoodDeliveryEvent(
        category = "cart",
        action = "proceed",
        params =
            listOf(
                EventParameter(
                    key = "isAuthorized",
                    value = isAuthorized.toString(),
                ),
            ),
    )
