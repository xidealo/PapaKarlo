package com.bunbeauty.domain.util

import com.bunbeauty.domain.model.date_time.DateTime
import com.bunbeauty.domain.model.date_time.MinuteSecond
import com.bunbeauty.domain.model.date_time.Time

interface IDateTimeUtil {

    fun toDateTime(millis: Long, timeZone: String): DateTime
    fun toTime(millis: Long, timeZone: String): Time
    fun getCurrentMinuteSecond(timeZone: String): MinuteSecond
    fun getCurrentDateTime(timeZone: String): DateTime
    fun getDateTimeIn(hour: Int, minute: Int, timeZone: String): DateTime
    fun getMillisByTime(time: Time, timeZone: String): Long
}