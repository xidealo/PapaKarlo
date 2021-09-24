package com.bunbeauty.domain.util.date_time

interface IDateTimeUtil {

    val currentTimeMillis: Long
    val currentTimeHour: Int
    val currentTimeMinute: Int

    fun getMinutesFromNowToTime(time: String): Int
    fun getTimeDDMMMMHHMM(millis: Long): String
    fun getTimeHHMM(millis: Long): String
}