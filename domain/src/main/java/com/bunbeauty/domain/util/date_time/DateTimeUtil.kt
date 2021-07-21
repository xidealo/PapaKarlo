package com.bunbeauty.domain.util.date_time

import org.joda.time.DateTime
import javax.inject.Inject

class DateTimeUtil @Inject constructor(): IDateTimeUtil {

    override val currentTimeHour: Int
        get() = DateTime.now().hourOfDay

    override val currentTimeMinute: Int
        get() = DateTime.now().minuteOfHour
}