package com.bunbeauty.analytic

import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent

expect class AnalyticService() {

    fun sendEvent(event: FoodDeliveryEvent, params: List<EventParameter> = emptyList())
}