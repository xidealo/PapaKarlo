package com.bunbeauty.shared.domain.util

import com.bunbeauty.shared.domain.model.date_time.Date
import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.MinuteSecond
import com.bunbeauty.shared.domain.model.date_time.Time
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class DateTimeUtil : IDateTimeUtil {

    private val currentMillis: Long
        get() = Clock.System.now().toEpochMilliseconds()

    override fun toDateTime(millis: Long, timeZone: String): DateTime {
        return getLocalDateTime(millis, timeZone).dateTime
    }

    override fun toTime(millis: Long, timeZone: String): Time {
        val localTime = getLocalDateTime(millis, timeZone).time
        return Time(hours = localTime.hour, minutes = localTime.minute)
    }

    override fun getCurrentMinuteSecond(timeZone: String): MinuteSecond {
        return getCurrentMinuteSecond(currentMillis, timeZone)
    }

    override fun getDateTimeIn(hour: Int, minute: Int, timeZone: String): DateTime {
        return getDateTimeIn(currentMillis, hour, minute, timeZone)
    }

    override fun getMillisByTime(time: Time, timeZone: String): Long {
        return getMillisByHourAndMinute(currentMillis, time, timeZone)
    }

    fun getCurrentMinuteSecond(currentMillis: Long, timeZone: String): MinuteSecond {
        return getLocalDateTime(currentMillis, timeZone).minuteSecond
    }

    fun getCurrentDateTime(currentMillis: Long, timeZone: String): DateTime {
        return getLocalDateTime(currentMillis, timeZone).dateTime
    }

    fun getDateTimeIn(currentMillis: Long, hour: Int, minute: Int, timeZone: String): DateTime {
        return getInstant(currentMillis).plus(hour, DateTimeUnit.HOUR)
            .plus(minute, DateTimeUnit.MINUTE)
            .toEpochMilliseconds()
            .let { millis ->
                toDateTime(millis, timeZone)
            }
    }

    fun getMillisByHourAndMinute(
        currentMillis: Long,
        time: Time,
        timeZone: String
    ): Long {
        val currentLocalDateTime = getLocalDateTime(currentMillis, timeZone)
        return LocalDateTime(
            year = currentLocalDateTime.year,
            monthNumber = currentLocalDateTime.monthNumber,
            dayOfMonth = currentLocalDateTime.dayOfMonth,
            hour = time.hours,
            minute = time.minutes,
            second = 0,
            nanosecond = 0
        ).toInstant(TimeZone.of(timeZone))
            .toEpochMilliseconds()
    }

    private fun getLocalDateTime(millis: Long, timeZone: String): LocalDateTime {
        return getInstant(millis).toLocalDateTime(TimeZone.of(timeZone))
    }

    private fun getInstant(millis: Long): Instant {
        return Instant.fromEpochMilliseconds(millis)
    }

    private val LocalDateTime.time
        get() = Time(
            hours = hour,
            minutes = minute
        )

    private val LocalDateTime.dateTime
        get() = DateTime(
            time = Time(hours = time.hour, minutes = time.minute),
            date = Date(
                dayOfMonth = dayOfMonth,
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
