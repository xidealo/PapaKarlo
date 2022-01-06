package com.bunbeauty.domain.util.date_time

import com.bunbeauty.common.Constants.DD_MMMM_HH_MM_PATTERN
import com.bunbeauty.common.Constants.HH_MM_PATTERN
import org.joda.time.DateTime
import javax.inject.Inject

class DateTimeUtil @Inject constructor() : IDateTimeUtil {

    override fun getTimeDDMMMMHHMM(millis: Long): String {
        return DateTime(millis).toString(DD_MMMM_HH_MM_PATTERN)
    }

    override fun getTimeHHMM(millis: Long): String {
        return DateTime(millis).toString(HH_MM_PATTERN)
    }
}