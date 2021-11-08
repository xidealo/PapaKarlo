package com.bunbeauty.domain.util.date_time

import com.bunbeauty.common.Constants.DD_MMMM_HH_MM_PATTERN
import com.bunbeauty.common.Constants.HH_MM_PATTERN
import com.bunbeauty.common.Constants.MINUTES_IN_HOUR
import com.bunbeauty.common.Constants.TIME_DIVIDER
import org.joda.time.DateTime
import javax.inject.Inject

class DateTimeUtil @Inject constructor() : IDateTimeUtil {

    override val currentTimeHour: Int
        get() = DateTime.now().hourOfDay

    override val currentTimeMinute: Int
        get() = DateTime.now().minuteOfHour

    override fun getTimeHourIn(hours: Int, minutes: Int): Int {
        return DateTime.now()
            .plusHours(hours)
            .plusMinutes(minutes)
            .hourOfDay
    }

    override fun getTimeMinuteIn(hours: Int, minutes: Int): Int {
        return DateTime.now()
            .plusHours(hours)
            .plusMinutes(minutes)
            .minuteOfHour
    }

    override fun getHour(millis: Long): Int {
        return DateTime(millis).hourOfDay
    }

    override fun getMinute(millis: Long): Int {
        return DateTime(millis).minuteOfHour
    }

    override fun getMillis(hours: Int, minutes: Int): Long {
        return DateTime.now()
            .withHourOfDay(hours)
            .withMinuteOfHour(minutes)
            .millis
    }

    override fun getMinutesFromNowToTime(time: String): Int {
        val hoursMinutes = time
            .split(TIME_DIVIDER)
            .map { timePart -> timePart.toInt() }
        val minutes = hoursMinutes[0] * MINUTES_IN_HOUR + hoursMinutes[1]
        val nowMinutes = currentTimeHour * MINUTES_IN_HOUR + currentTimeMinute
        return minutes - nowMinutes
    }

    override fun getTimeDDMMMMHHMM(millis: Long): String {
        return DateTime(millis).toString(DD_MMMM_HH_MM_PATTERN)
    }

    override fun getTimeHHMM(millis: Long): String {
        return DateTime(millis).toString(HH_MM_PATTERN)
    }
}