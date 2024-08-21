package com.bunbeauty.shared.domain.use_case.deferred_time

import com.bunbeauty.shared.Constants
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.util.DateTimeUtil

class GetMinTimeUseCase(private val dateTimeUtil: DateTimeUtil) {

    operator fun invoke(timeZone: String): Time {
        return dateTimeUtil.getDateTimeIn(
            Constants.MIN_DEFERRED_HOURS_ADDITION,
            Constants.MIN_DEFERRED_MINUTES_ADDITION,
            timeZone
        ).time
    }
}
