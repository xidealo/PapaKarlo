package com.bunbeauty.papakarlo.feature.create_order.deferred_time

import com.bunbeauty.domain.model.date_time.Time

data class DeferredTimeSettings(
    val title: String,
    val minTime: Time,
    val selectedTime: Time
)
