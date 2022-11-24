package com.bunbeauty.papakarlo.feature.create_order.mapper

import android.content.res.Resources
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.domain.model.date_time.Time

class TimeMapper(
    private val resources: Resources,
    private val stringUtil: IStringUtil
) {

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

    fun toString(time: TimeUI): String {
        return when (time) {
            is TimeUI.ASAP -> {
                resources.getString(R.string.asap)
            }
            is TimeUI.Time -> {
                stringUtil.getTimeString(time)
            }
        }
    }
}
