package com.bunbeauty.domain.util

import com.bunbeauty.domain.model.Time
import kotlinx.datetime.*
import javax.inject.Inject

class DateTimeUtil @Inject constructor() : IDateTimeUtil {

    override fun toTime(millis: Long): Time {
        return getLocalDateTime(millis).time()
    }

    override fun toDDMMMMHHMM(millis: Long): String {
        return getLocalDateTime(millis).run {
            "$dayOfMonth ${monthName()} ${hour()}:${minute()}"
        }
    }

    override fun toHHMM(millis: Long): String {
        return getLocalDateTime(millis).run {
            "${hour()}:${minute()}"
        }
    }

    override fun toHHMM(millis: Long?): String? {
        millis ?: return null
        return toHHMM(millis)
    }

    override fun getCurrentTime(): Time {
        return getCurrentLocalDateTime().time()
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
        minuteOfDay = minute + hour * 60,
        minuteOfHour = minute,
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