package com.bunbeauty.domain.util.date_time

interface IDateTimeUtil {

    val currentTimeHour: Int
    val currentTimeMinute: Int

    fun getTimeHourIn(hours: Int, minutes: Int) : Int
    fun getTimeMinuteIn(hours: Int, minutes: Int) : Int
    fun getHour(millis: Long) : Int
    fun getMinute(millis: Long) : Int
    fun getMillis(hours: Int, minutes: Int) : Long
    fun getMinutesFromNowToTime(time: String): Int
    fun getTimeDDMMMMHHMM(millis: Long): String
    fun getTimeHHMM(millis: Long): String
}