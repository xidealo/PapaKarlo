package com.bunbeauty.core.domain.deferred_time

import com.bunbeauty.core.Constants
import com.bunbeauty.core.model.date_time.Time
import com.bunbeauty.core.domain.util.DateTimeUtil

class GetMinTimeUseCase(
    private val dateTimeUtil: DateTimeUtil,
) {
    operator fun invoke(timeZone: String): Time =
        dateTimeUtil
            .getDateTimeIn(
                Constants.MIN_DEFERRED_HOURS_ADDITION,
                Constants.MIN_DEFERRED_MINUTES_ADDITION,
                timeZone,
            ).time
}
