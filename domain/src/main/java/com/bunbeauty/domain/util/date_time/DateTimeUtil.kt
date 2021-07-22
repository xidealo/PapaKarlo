package com.bunbeauty.domain.util.date_time

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
}