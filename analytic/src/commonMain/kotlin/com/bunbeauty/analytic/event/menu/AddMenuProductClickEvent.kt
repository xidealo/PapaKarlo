package com.bunbeauty.analytic.event.menu

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class AddMenuProductClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "menu",
        action = "AddMenuProductClickEvent",
        params = listOf(menuProductUuidEventParameter)
    )