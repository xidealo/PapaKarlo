package com.bunbeauty.analytic

import cocoapods.FirebaseAnalytics.FIRAnalytics
import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.analytic.event.toMap
import com.bunbeauty.core.Logger
import com.bunbeauty.core.Logger.ANALYTIC_TAG
import com.bunbeauty.core.targetName
import platform.Foundation.NSMutableDictionary

actual class AnalyticService {
    actual fun sendEvent(event: FoodDeliveryEvent) {
        val name = event.eventName(prefix = targetName)
        val parameters = event.params.toFirebaseParameters()
        Logger.logD(ANALYTIC_TAG, "send event: $name with params ${event.params.toMap()}")
        FIRAnalytics.logEventWithName(name, parameters = parameters)
    }
}

private fun List<EventParameter>.toFirebaseParameters(): Map<Any?, *>? {
    if (isEmpty()) {
        return null
    }

    val dictionary = NSMutableDictionary(capacity = size.toULong())
    forEach { parameter ->
        dictionary.setObject(parameter.value, forKey = parameter.key)
    }
    return dictionary as Map<Any?, *>
}
