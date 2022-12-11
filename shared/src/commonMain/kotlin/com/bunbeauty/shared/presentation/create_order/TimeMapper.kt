package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.presentation.create_order.model.TimeUI

class TimeMapper {

    fun toUiModel(time: Time?): TimeUI {
        return if (time == null) {
            TimeUI.ASAP
        } else {
            toUiModel(time)
        }
    }

    fun toUiModel(time: Time): TimeUI.Time {
        return TimeUI.Time(
            hours = time.hours,
            minutes = time.minutes,
        )
    }

    fun toDomainModel(time: TimeUI): Time? {
        return when (time) {
            is TimeUI.ASAP -> {
                null
            }
            is TimeUI.Time -> {
                Time(
                    hours = time.hours,
                    minutes = time.minutes,
                )
            }
        }
    }
}
