package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.shared.domain.model.date_time.Time

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
