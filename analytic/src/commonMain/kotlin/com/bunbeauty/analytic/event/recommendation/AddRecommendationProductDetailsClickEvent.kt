package com.bunbeauty.analytic.event.recommendation

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class AddRecommendationProductDetailsClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "recommendation",
        action = "AddRecommendationProductDetailsClickEvent",
        params = listOf(menuProductUuidEventParameter)
    )