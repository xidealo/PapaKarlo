package com.bunbeauty.domain.util.date_time

import com.bunbeauty.common.Constants.HH_MM_PATTERN
import com.bunbeauty.common.Constants.MINUTES_IN_HOUR
import com.bunbeauty.common.Constants.TIME_DIVIDER
import com.instacart.library.truetime.TrueTime
import org.joda.time.DateTime
import javax.inject.Inject

class DateTimeUtil @Inject constructor() : IDateTimeUtil {

    override val currentTimeMillis: Long
        get() = try {
            TrueTime.now().time
        } catch (exception: Exception) {
            DateTime.now().millis
        }

    override val currentTimeHour: Int
        get() = DateTime.now().hourOfDay

    override val currentTimeMinute: Int
        get() = DateTime.now().minuteOfHour

    override fun getMinutesFromNowToTime(time: String): Int {
        val hoursMinutes = time
            .split(TIME_DIVIDER)
            .map { timePart -> timePart.toInt() }
        val minutes = hoursMinutes[0] * MINUTES_IN_HOUR + hoursMinutes[1]
        val nowMinutes = currentTimeHour * MINUTES_IN_HOUR + currentTimeMinute
        return minutes - nowMinutes
    }

    override fun getTimeHHMM(millis: Long): String {
        return DateTime(millis).toString(HH_MM_PATTERN)
    }
}