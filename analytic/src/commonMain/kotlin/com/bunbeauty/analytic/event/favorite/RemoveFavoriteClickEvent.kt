package com.bunbeauty.analytic.event.favorite

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class RemoveFavoriteClickEvent(
    menuProductUuidEventParameter: MenuProductUuidEventParameter,
) : FoodDeliveryEvent(
        category = "favorite",
        action = "remove",
        params = listOf(menuProductUuidEventParameter),
    )
