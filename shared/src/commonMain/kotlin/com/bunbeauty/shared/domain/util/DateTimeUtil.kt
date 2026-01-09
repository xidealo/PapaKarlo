package com.bunbeauty.shared.domain.util

import com.bunbeauty.core.model.date_time.DateTime
import com.bunbeauty.core.model.date_time.MinuteSecond
import com.bunbeauty.core.model.date_time.Time


interface DateTimeUtil {
    fun toDateTime(
        millis: Long,
        timeZone: String,
    ): DateTime

    fun toTime(
        millis: Long,
        timeZone: String,
    ): Time

    fun getCurrentMinuteSecond(timeZone: String): MinuteSecond

    fun getDateTimeIn(
        hour: Int,
        minute: Int,
        timeZone: String,
    ): DateTime

    fun getMillisByTime(
        time: Time,
        timeZone: String,
    ): Long
}
