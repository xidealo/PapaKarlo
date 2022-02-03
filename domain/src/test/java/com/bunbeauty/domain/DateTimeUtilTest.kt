package com.bunbeauty.domain

import com.bunbeauty.domain.model.datee_time.Date
import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.Time
import com.bunbeauty.domain.util.DateTimeUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DateTimeUtilTest {

    lateinit var dateTimeUtil: DateTimeUtil

    @Before
    fun setup() {
        dateTimeUtil = DateTimeUtil()
    }

    @Test
    fun convertingMillisToDateTime() {
        val millis = 1643673840000
        val timeZone = "UTC+3"
        val expectedDateTime = DateTime(
            date = Date(
                datOfMonth = 1,
                monthNumber = 2,
                year = 2022
            ),
            time = Time(
                hourOfDay = 3,
                minuteOfHour = 4
            )
        )

        val dateTime = dateTimeUtil.toDateTime(millis, timeZone)

        assertEquals(expectedDateTime, dateTime)
    }

    @Test
    fun convertingMillisToTime() {
        val millis = 1640988120000
        val timeZone = "UTC+3"
        val expectedTime = Time(
            hourOfDay = 1,
            minuteOfHour = 2
        )

        val time = dateTimeUtil.toTime(millis, timeZone)

        assertEquals(expectedTime, time)
    }

    @Test
    fun convertingZeroMillisToTime() {
        val millis = 0L
        val timeZone = "UTC+3"
        val expectedTime = Time(
            hourOfDay = 3,
            minuteOfHour = 0
        )

        val time = dateTimeUtil.toTime(millis, timeZone)

        assertEquals(expectedTime, time)
    }

    @Test
    fun gettingTimeIn() {
        val currentMillis = 1640988120000 // 01:02
        val hour = 1
        val minute = 2
        val timeZone = "UTC+3"
        val expectedTime = Time(
            hourOfDay = 2,
            minuteOfHour = 4
        )

        val time = dateTimeUtil.getDateTimeIn(currentMillis, hour, minute, timeZone)

        assertEquals(expectedTime, time)
    }

    @Test
    fun gettingMillisByHourAndMinute() {
        val currentMillis = 1640988120000 // 01:02
        val hour = 3
        val minute = 4
        val timeZone = "UTC+3"
        val expectedMillis = 1640995440000

        val millis = dateTimeUtil.getMillisByHourAndMinute(currentMillis, hour, minute, timeZone)

        assertEquals(expectedMillis, millis)
    }


}