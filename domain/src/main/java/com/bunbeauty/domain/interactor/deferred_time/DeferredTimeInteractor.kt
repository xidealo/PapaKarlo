package com.bunbeauty.domain.interactor.deferred_time

import com.bunbeauty.common.Constants.HH_MM_PATTERN
import com.bunbeauty.common.Constants.MIN_DEFERRED_HOURS_ADDITION
import com.bunbeauty.common.Constants.MIN_DEFERRED_MINUTES_ADDITION
import org.joda.time.DateTime
import javax.inject.Inject

class DeferredTimeInteractor @Inject constructor() : IDeferredTimeInteractor {

    override fun getMinTimeHours(): Int {
        return getTimeHourIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION
        ).hourOfDay
    }

    override fun getMinTimeMinutes(): Int {
        return getTimeHourIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION
        ).minuteOfHour
    }

    override fun getDeferredTimeMillis(hours: Int, minutes: Int): Long {
        return DateTime.now()
            .withHourOfDay(hours)
            .withMinuteOfHour(minutes)
            .millis
    }

    override fun getDeferredTimeHours(deferredTimeMillis: Long): Int {
        return DateTime(deferredTimeMillis).hourOfDay
    }

    override fun getDeferredTimeMinutes(deferredTimeMillis: Long): Int {
        return DateTime(deferredTimeMillis).minuteOfHour
    }

    override fun getDeferredTimeHHMM(deferredTimeMillis: Long): String {
        return DateTime(deferredTimeMillis).toString(HH_MM_PATTERN)
    }

    fun getTimeHourIn(hours: Int, minutes: Int): DateTime {
        return DateTime.now()
            .plusHours(hours)
            .plusMinutes(minutes)
    }
}