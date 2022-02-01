package com.bunbeauty.domain.util

import com.bunbeauty.domain.model.datee_time.Date
import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.MinuteSecond
import com.bunbeauty.domain.model.datee_time.Time
import kotlinx.datetime.*
import javax.inject.Inject

class DateTimeUtil @Inject constructor() : IDateTimeUtil {

    override val currentMinuteSecond: MinuteSecond
        get() = getLocalDateTime(currentMillis).minuteSecond

    private val timeZone = TimeZone.of("UTC+3")

    private val currentMillis: Long
        get() = Clock.System.now().toEpochMilliseconds()

    override fun toDateTime(millis: Long): DateTime {
        return getLocalDateTime(millis).dateTime
    }

    override fun toTime(millis: Long): Time {
        return getLocalDateTime(millis).time
    }

    override fun getTimeIn(hour: Int, minute: Int): Time {
        return getTimeIn(currentMillis, hour, minute)
    }

    override fun getMillisByHourAndMinute(hour: Int, minute: Int): Long {
        return getMillisByHourAndMinute(currentMillis, hour, minute)
    }

    fun getTimeIn(currentMillis: Long, hour: Int, minute: Int): Time {
        return toTime(
            getInstant(currentMillis).plus(hour, DateTimeUnit.HOUR)
                .plus(minute, DateTimeUnit.MINUTE)
                .toEpochMilliseconds()
        )
    }

    fun getMillisByHourAndMinute(currentMillis: Long, hour: Int, minute: Int): Long {
        val currentLocalDateTime = getLocalDateTime(currentMillis)
        return LocalDateTime(
            year = currentLocalDateTime.year,
            monthNumber = currentLocalDateTime.monthNumber,
            dayOfMonth = currentLocalDateTime.dayOfMonth,
            hour = hour,
            minute = minute,
            second = 0,
            nanosecond = 0
        ).toInstant(timeZone)
            .toEpochMilliseconds()
    }

    private fun getLocalDateTime(millis: Long): LocalDateTime {
        return getInstant(millis).toLocalDateTime(timeZone)
    }

    private fun getInstant(millis: Long): Instant {
        return Instant.fromEpochMilliseconds(millis)
    }

    private val LocalDateTime.time
        get() = Time(
            hourOfDay = hour,
            minuteOfHour = minute,
        )

    private val LocalDateTime.dateTime
        get() = DateTime(
            time = time,
            date = Date(
                datOfMonth = dayOfMonth,
                monthNumber = monthNumber,
                year = year
            )
        )

    private val LocalDateTime.minuteSecond
        get() = MinuteSecond(
            minuteOfDay = minute + hour * 60,
            secondOfMinute = second
        )
}