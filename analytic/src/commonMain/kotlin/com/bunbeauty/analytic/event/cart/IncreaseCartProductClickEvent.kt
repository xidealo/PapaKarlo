package com.bunbeauty.analytic.event.cart

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class IncreaseCartProductClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "cart",
        action = "IncreaseCartProductClickEvent",
        params = listOf(menuProductUuidEventParameter)
    )