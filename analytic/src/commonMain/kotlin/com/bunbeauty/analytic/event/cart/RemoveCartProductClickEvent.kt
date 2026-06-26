package com.bunbeauty.analytic.event.cart

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class RemoveCartProductClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "cart",
        action = "remove",
        params = listOf(menuProductUuidEventParameter)
    )