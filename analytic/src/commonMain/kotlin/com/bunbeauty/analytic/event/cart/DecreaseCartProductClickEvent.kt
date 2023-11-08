package com.bunbeauty.analytic.event.cart

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class DecreaseCartProductClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "cart",
        action = "DecreaseCartProductClickEvent",
        params = listOf(menuProductUuidEventParameter)
    )