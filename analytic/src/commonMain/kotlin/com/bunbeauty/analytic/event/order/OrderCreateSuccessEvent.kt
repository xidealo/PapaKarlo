package com.bunbeauty.analytic.event.order

import com.bunbeauty.analytic.event.FoodDeliveryEvent

class OrderCreateSuccessEvent :
    FoodDeliveryEvent(
        category = "order",
        action = "create_ok",
    )
