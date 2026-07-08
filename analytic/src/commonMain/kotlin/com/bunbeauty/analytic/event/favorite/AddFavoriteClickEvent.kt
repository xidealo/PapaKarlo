package com.bunbeauty.analytic.event.favorite

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class AddFavoriteClickEvent(
    menuProductUuidEventParameter: MenuProductUuidEventParameter,
) : FoodDeliveryEvent(
        category = "favorite",
        action = "add",
        params = listOf(menuProductUuidEventParameter),
    )
