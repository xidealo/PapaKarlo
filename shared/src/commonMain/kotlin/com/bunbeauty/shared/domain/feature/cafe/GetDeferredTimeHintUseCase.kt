package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.core.model.date_time.Time
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class GetDeferredTimeHintUseCase {
    @OptIn(ExperimentalTime::class)
    operator fun invoke(time: Time): Boolean {
        val now =
            Clock.System
                .now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .time

        val currentMinutes = now.hour * 60 + now.minute + 60

        val selectedMinutes = time.hours * 60 + time.minutes
        return currentMinutes >= selectedMinutes
    }
}
