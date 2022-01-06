package com.bunbeauty.domain.util.date_time

interface IDateTimeUtil {

    fun getTimeDDMMMMHHMM(millis: Long): String
    fun getTimeHHMM(millis: Long): String
}