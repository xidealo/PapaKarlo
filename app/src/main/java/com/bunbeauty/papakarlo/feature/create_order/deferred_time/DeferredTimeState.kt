package com.bunbeauty.papakarlo.feature.create_order.deferred_time

import com.bunbeauty.domain.model.datee_time.Time

sealed class DeferredTimeState {
    object Default : DeferredTimeState()
    data class SelectTime(val minTime: Time, val selectedTime: Time) : DeferredTimeState()
    data class TimeSelected(val selectedTimeMillis: Long?) : DeferredTimeState()
}
