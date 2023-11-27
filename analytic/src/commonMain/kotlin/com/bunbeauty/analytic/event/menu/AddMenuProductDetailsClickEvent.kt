package com.bunbeauty.analytic.event.menu

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class AddMenuProductDetailsClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "menu",
        action = "AddMenuProductDetailsClickEvent",
        params = listOf(menuProductUuidEventParameter)
    )