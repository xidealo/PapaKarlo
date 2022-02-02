package com.bunbeauty.domain.util

import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.MinuteSecond
import com.bunbeauty.domain.model.datee_time.Time

interface IDateTimeUtil {

    fun toDateTime(millis: Long, timeZone: String): DateTime
    fun toTime(millis: Long, timeZone: String): Time
    fun getCurrentMinuteSecond(timeZone: String): MinuteSecond
    fun getTimeIn(hour: Int, minute: Int, timeZone: String): Time
    fun getMillisByHourAndMinute(hour: Int, minute: Int, timeZone: String): Long
}