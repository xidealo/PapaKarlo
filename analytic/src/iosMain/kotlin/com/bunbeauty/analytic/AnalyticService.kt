package com.bunbeauty.analytic

import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent

actual class AnalyticService {

    actual fun sendEvent(event: FoodDeliveryEvent, params: List<EventParameter>){

    }
}