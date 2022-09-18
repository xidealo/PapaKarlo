package com.bunbeauty.papakarlo.feature.create_order.mapper

import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.shared.domain.model.date_time.Time

class TimeMapper {

    fun toUiModel(time: Time): TimeUI {
        return TimeUI(
            hours = time.hours,
            minutes = time.minutes,
        )
    }

    fun toDomainModel(time: TimeUI): Time {
        return Time(
            hours = time.hours,
            minutes = time.minutes,
        )
    }
}