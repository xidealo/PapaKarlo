package com.bunbeauty.domain.util

import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.MinuteSecond
import com.bunbeauty.domain.model.datee_time.Time

interface IDateTimeUtil {

    fun toDateTime(millis: Long): DateTime
    fun toTime(millis: Long): Time
    fun getCurrentMinuteSecond(): MinuteSecond
    fun getTimeIn(hour: Int, minute: Int): Time
    fun getMillisByHourAndMinute(hour: Int, minute: Int): Long
}