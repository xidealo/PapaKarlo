package com.bunbeauty.analytic.event.recommendation

import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter

class AddRecommendationProductDetailsClickEvent(menuProductUuidEventParameter: MenuProductUuidEventParameter) :
    FoodDeliveryEvent(
        category = "reco",
        action = "add_detail",
        params = listOf(menuProductUuidEventParameter)
    )