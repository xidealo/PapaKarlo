package com.bunbeauty.analytic

import android.os.Bundle
import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.FoodDeliveryEvent
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class AnalyticService : KoinComponent {

    private val firebaseAnalytics: FirebaseAnalytics by inject()

    actual fun sendEvent(event: FoodDeliveryEvent, params: List<EventParameter>) {
        val name = "${BuildConfig.FLAVOR}_${event.category}_${event.action}"
        firebaseAnalytics.logEvent(name, params.toBundle())
    }

    private fun List<EventParameter>.toBundle(): Bundle = Bundle(this.size).apply {
        this@toBundle.map { event ->
            putString(event.key, event.value)
        }
    }
}