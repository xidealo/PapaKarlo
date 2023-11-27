package com.bunbeauty.analytic

//import cocoapods.FirebaseAnalytics.FIRAnalytics
import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.event.toMap
import com.bunbeauty.core.Logger
import com.bunbeauty.core.Logger.ANALYTIC_TAG
import com.bunbeauty.core.targetName
import platform.Foundation.NSBundle

actual class AnalyticService {
    actual fun sendEvent(event: FoodDeliveryEvent) {
        val name = "${targetName}_${event.category}_${event.action}"
        Logger.logD(Logger.ANALYTIC_TAG, "send event: $name with params ${event.params.toMap()}")
        //FIRAnalytics.logEventWithName(name, event.params.toMap())
    }
}