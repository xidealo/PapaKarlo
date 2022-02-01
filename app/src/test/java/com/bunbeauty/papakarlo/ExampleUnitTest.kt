package com.bunbeauty.papakarlo

import com.bunbeauty.domain.model.datee_time.Date
import com.bunbeauty.domain.model.datee_time.DateTime
import com.bunbeauty.domain.model.datee_time.Time
import com.bunbeauty.domain.util.DateTimeUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var dateTimeUtil: DateTimeUtil

    @Before
    fun setup() {
        dateTimeUtil = DateTimeUtil()
    }

    @Test
    fun convertingMillisToDateTime() {
        val millis = 1643673840000
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

        val dateTime = dateTimeUtil.toDateTime(millis)

        assertEquals(expectedDateTime, dateTime)
    }

    @Test
    fun convertingMillisToTime() {
        val millis = 1640988120000
        val expectedTime = Time(
            hourOfDay = 1,
            minuteOfHour = 2
        )

        val time = dateTimeUtil.toTime(millis)

        assertEquals(expectedTime, time)
    }

    @Test
    fun convertingZeroMillisToTime() {
        val millis = 0L
        val expectedTime = Time(
            hourOfDay = 3,
            minuteOfHour = 0
        )

        val time = dateTimeUtil.toTime(millis)

        assertEquals(expectedTime, time)
    }

    @Test
    fun gettingTimeIn() {
        val currentMillis = 1640988120000 // 01:02
        val hour = 1
        val minute = 2
        val expectedTime = Time(
            hourOfDay = 2,
            minuteOfHour = 4
        )

        val time = dateTimeUtil.getTimeIn(currentMillis, hour, minute)

        assertEquals(expectedTime, time)
    }

    @Test
    fun gettingMillisByHourAndMinute() {
        val currentMillis = 1640988120000 // 01:02
        val hour = 3
        val minute = 4
        val expectedMillis = 1640995440000

        val millis = dateTimeUtil.getMillisByHourAndMinute(currentMillis, hour, minute)

        assertEquals(expectedMillis, millis)
    }


}