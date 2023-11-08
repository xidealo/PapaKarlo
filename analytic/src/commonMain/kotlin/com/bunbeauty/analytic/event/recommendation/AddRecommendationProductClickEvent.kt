package com.bunbeauty.analytic.event.recommendation

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class AddRecommendationProductClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "recommendation",
        action = "AddRecommendationProductClickEvent",
        params = listOf(menuProductUuidEventParameter)
    )