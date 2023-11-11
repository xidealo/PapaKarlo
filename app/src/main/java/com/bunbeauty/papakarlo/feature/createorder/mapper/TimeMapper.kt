package com.bunbeauty.papakarlo.feature.createorder.mapper

import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.model.TimeUI
import com.bunbeauty.shared.domain.model.date_time.Time

class TimeMapper {

    fun toTimeUI(time: Time?): TimeUI {
        return if (time == null) {
            TimeUI.ASAP
        } else {
            toTimeUI(time)
        }
    }

    fun toTimeUI(time: Time): TimeUI.Time {
        return TimeUI.Time(
            hours = time.hours,
            minutes = time.minutes,
        )
    }

    fun toTime(time: TimeUI): Time? {
        return  when (time) {
            is TimeUI.Time -> Time(
                hours = time.hours,
                minutes = time.minutes,
            )
            TimeUI.ASAP -> null
        }
    }

}