package com.bunbeauty.domain.util

import com.bunbeauty.domain.model.Time

interface IDateTimeUtil {

    fun toTime(millis: Long): Time
    fun toDDMMMMHHMM(millis: Long): String
    fun toHHMM(millis: Long): String
    fun toHHMM(millis: Long?): String?
    fun getCurrentTime(): Time
    fun getTimeIn(hour: Int, minute: Int): Time
    fun getMillisByHourAndMinute(hour: Int, minute: Int): Long
}