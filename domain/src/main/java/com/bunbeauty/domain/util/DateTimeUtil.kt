package com.bunbeauty.domain.util

import com.bunbeauty.domain.model.datee_time.Date
import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.MinuteSecond
import com.bunbeauty.domain.model.datee_time.Time
import kotlinx.datetime.*
import javax.inject.Inject

class DateTimeUtil @Inject constructor() : IDateTimeUtil {

    override fun toDateTime(millis: Long): DateTime {
        return getLocalDateTime(millis).dateTime()
    }

    override fun toTime(millis: Long): Time {
        return getLocalDateTime(millis).time()
    }

    override fun getCurrentMinuteSecond(): MinuteSecond {
        return getCurrentLocalDateTime().minuteSecond()
    }

    override fun getTimeIn(hour: Int, minute: Int): Time {
        return toTime(
            getCurrentInstant().plus(hour, DateTimeUnit.HOUR)
                .plus(minute, DateTimeUnit.MINUTE)
                .toEpochMilliseconds()
        )
    }

    override fun getMillisByHourAndMinute(hour: Int, minute: Int): Long {
        val currentLocalDateTime = getCurrentLocalDateTime()
        return LocalDateTime(
            year = currentLocalDateTime.year,
            monthNumber = currentLocalDateTime.monthNumber,
            dayOfMonth = currentLocalDateTime.dayOfMonth,
            hour = hour,
            minute = minute,
            second = 0,
            nanosecond = 0
        ).let { localDateTime ->
            getMillis(localDateTime)
        }
    }

    fun getMillis(localDateTime: LocalDateTime): Long {
        return localDateTime.toInstant(TimeZone.of("UTC+3")).toEpochMilliseconds()
    }

    fun getInstant(millis: Long): Instant {
        return Instant.fromEpochMilliseconds(millis)
    }

    fun getLocalDateTime(millis: Long): LocalDateTime {
        return getInstant(millis).toLocalDateTime(TimeZone.of("UTC+3"))
    }

    fun getCurrentInstant(): Instant {
        val currentEpoch = Clock.System.now().toEpochMilliseconds()
        return getInstant(currentEpoch)
    }

    fun getCurrentLocalDateTime(): LocalDateTime {
        val currentEpoch = Clock.System.now().toEpochMilliseconds()
        return getLocalDateTime(currentEpoch)
    }

    fun LocalDateTime.monthName() = month.name

    fun LocalDateTime.hour() = checkFirstZero(hour)

    fun LocalDateTime.minute() = checkFirstZero(minute)

    fun LocalDateTime.time() = Time(
        hourOfDay = hour,
        minuteOfHour = minute,
    )

    fun LocalDateTime.dateTime() = DateTime(
        time = time(),
        date = Date(
            datOfMonth = dayOfMonth,
            monthNumber = monthNumber,
            year = year
        )
    )

    fun LocalDateTime.minuteSecond() = MinuteSecond(
        minuteOfDay = minute + hour * 60,
        secondOfMinute = second
    )

    fun checkFirstZero(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else {
            number.toString()
        }
    }
}