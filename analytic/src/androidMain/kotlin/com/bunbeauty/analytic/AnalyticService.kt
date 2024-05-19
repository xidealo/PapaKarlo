package com.bunbeauty.analytic

import android.os.Bundle
import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.bunbeauty.core.Logger
import com.bunbeauty.core.flavorQualifier
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class AnalyticService : KoinComponent {

    private val firebaseAnalytics: FirebaseAnalytics by inject()
    private val flavor: String by inject(flavorQualifier)

    actual fun sendEvent(event: FoodDeliveryEvent) {
        val name = "${flavor}_${event.category}_${event.action}"
        Logger.logD(Logger.ANALYTIC_TAG, "send event: $name with params ${event.params.toBundle()}")
        firebaseAnalytics.logEvent(name, event.params.toBundle())
    }

    private fun List<EventParameter>.toBundle(): Bundle = Bundle(this.size).apply {
        this@toBundle.map { event ->
            putString(event.key, event.value)
        }
    }
}