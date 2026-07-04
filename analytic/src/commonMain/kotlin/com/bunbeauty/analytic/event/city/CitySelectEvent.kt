package com.bunbeauty.analytic.event.city

import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent

class CitySelectEvent(cityUuid: String) :
    FoodDeliveryEvent(
        category = "city",
        action = "select",
        params =
            listOf(
                EventParameter(
                    key = "cityUuid",
                    value = cityUuid,
                ),
            ),
    )
