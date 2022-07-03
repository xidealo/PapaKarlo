package com.bunbeauty.papakarlo.feature.create_order.model

import com.bunbeauty.shared.domain.model.date_time.Time

data class DeferredTimeSettingsUI(
    val title: String,
    val minTime: Time,
    val selectedTime: Time
)
