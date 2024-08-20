package com.bunbeauty.analytic.event.menu

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.TimeParameter

class LoadedMenuEvent(timeParameter: TimeParameter) :
    FoodDeliveryEvent(
        category = "menu",
        action = "LoadedMenuEvent",
        params = listOf(timeParameter)
    )