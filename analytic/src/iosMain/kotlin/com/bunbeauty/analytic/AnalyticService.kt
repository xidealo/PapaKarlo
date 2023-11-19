package com.bunbeauty.analytic

import cocoapods.FirebaseAnalytics.FIRAnalytics
import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.event.toMap
import com.bunbeauty.core.targetName
import platform.Foundation.NSBundle

actual class AnalyticService {
    actual fun sendEvent(event: FoodDeliveryEvent){
        val name = "${targetName}_${event.category}_${event.action}"
        FIRAnalytics.logEventWithName(name, event.params.toMap())
    }
}