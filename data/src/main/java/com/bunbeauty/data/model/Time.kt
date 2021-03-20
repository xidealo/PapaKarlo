package com.bunbeauty.data.model

import android.os.Parcelable
import android.os.SystemClock
import kotlinx.parcelize.Parcelize

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Days
import org.joda.time.Minutes
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Time(
    var millis: Long,
    var offset: Int = 0
) : Parcelable {

    constructor(date: String, _offset: Int) : this(0) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        format.timeZone.rawOffset = _offset * 3600000
        val parsedMillis = format.parse(date)?.time ?: 0
        millis = parsedMillis
        offset = _offset
    }

    fun toDateTime(): DateTime {
        return DateTime(millis).withZone(DateTimeZone.forOffsetHours(offset))
    }

    fun getCurrentTime(): Time {
        val newMillis = toDateTime().plus(SystemClock.elapsedRealtime()).millis
        return Time(newMillis, offset)
    }

    fun getTimeAtStartOfDay(): Time {
        return Time(toDateTime().withTimeAtStartOfDay().millis, offset)
    }

    fun isExactlySomeHours(time: Time): Boolean {
        val minutes = getMinutesBetween(time)
        return (minutes % MINUTES_IN_HOUR <= TOLERANCE) && (minutes > MINUTES_IN_HOUR)
    }

    fun isRecent(time: Time) : Boolean {
        val minutes = getMinutesBetween(time)
        return minutes <= TOLERANCE
    }

    private fun getMinutesBetween(time: Time): Int {
        return Minutes.minutesBetween(toDateTime(), time.toDateTime()).minutes
    }

    fun getStringTime(): String {
        val dateTime = toDateTime()
        var hours = dateTime.hourOfDay
        val meridiem = if (hours < 12) {
            "AM"
        } else {
            "PM"
        }
        hours = when (hours) {
            0, 12 -> 12
            in 1..11 -> hours
            else -> hours - 12
        }
        val minutesString = dateTime.minuteOfHour.addFirstZero()

        return "$hours:$minutesString $meridiem"
    }

    fun getHours(): Float {
        return toDateTime().hourOfDay + toDateTime().minuteOfHour / 60f
    }

    fun minusDays(daysCount: Int): Time {
        return Time(toDateTime().minusDays(daysCount).millis, offset)
    }

    fun plusDays(daysCount: Int): Time {
        return Time(toDateTime().plusDays(daysCount).millis, offset)
    }

    fun toStringTimeHHMMSSWithSeparator(): String {
        val stringHour = toDateTime().hourOfDay.addFirstZero()
        val stringMinute = toDateTime().minuteOfHour.addFirstZero()
        val stringSecond = toDateTime().secondOfMinute.addFirstZero()

        return "${stringHour}:${stringMinute}:${stringSecond}"
    }

    fun toStringTimeHHMM(): String {
        var roundedHours = toDateTime().hourOfDay
        val roundedMinutes = if (toDateTime().secondOfMinute < 30) {
            toDateTime().minuteOfHour
        } else {
            roundedHours += (toDateTime().minuteOfHour + 1) / 60
            (toDateTime().minuteOfHour + 1) % 60
        }

        val hours = if (roundedHours < 10) {
            "0$roundedHours"
        } else {
            roundedHours.toString()
        }
        val minutes = if (roundedMinutes < 10) {
            "0$roundedMinutes"
        } else {
            roundedMinutes.toString()
        }

        return "$hours:$minutes"
    }

    fun toStringDateYYYYMMDD(): String {
        val year = toDateTime().yearOfEra.addFirstZero()
        val month = toDateTime().monthOfYear.addFirstZero()
        val day = toDateTime().dayOfMonth.addFirstZero()

        return "$year-$month-$day"
    }

    fun toStringDateMMMDD(): String {
        val monthName = toDateTime().toString("MMM", Locale.US)
        return "$monthName ${toDateTime().dayOfMonth}"
    }

    fun toStringDateMMMMDDEEEE(): String {
        val monthName = toDateTime().toString("MMMM", Locale.US)
        val weekDayName = toDateTime().toString("EEEE", Locale.US)
        return "$monthName ${toDateTime().dayOfMonth}, $weekDayName"
    }

    private fun Int.addFirstZero(): String {
        return if (this < 10) {
            "0$this"
        } else {
            this.toString()
        }
    }

    fun getDaysBetween(time: Time): Int {
        return Days.daysBetween(
            this.toDateTime().withTimeAtStartOfDay(),
            time.toDateTime().withTimeAtStartOfDay()
        ).days
    }

    fun getNextDayStartTime(daysCount: Int): Time {
        return Time(getNextDayStartMillis(daysCount), offset)
    }

    fun getNextDayStartMillis(daysCount: Int): Long {
        return toDateTime().plusDays(daysCount).withTimeAtStartOfDay().millis
    }

    fun getNextDayEndMillis(daysCount: Int): Long {
        return getNextDayStartMillis(daysCount + 1) - 1
    }

    fun getEndMillis(): Long {
        return getNextDayStartMillis(1) - 1
    }

    fun getEndDayTime(): Time {
        return Time(getEndMillis(), offset)
    }

    companion object {
        const val TIME = "time"
        const val OFFSET = "offset"
        const val START_SYSTEM_MILLIS = "start system millis"

        const val MINUTES_IN_HOUR = 5
        const val TOLERANCE = 1
    }
}