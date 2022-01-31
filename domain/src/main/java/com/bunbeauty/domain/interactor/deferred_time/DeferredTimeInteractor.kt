package com.bunbeauty.domain.interactor.deferred_time

import com.bunbeauty.common.Constants.MIN_DEFERRED_HOURS_ADDITION
import com.bunbeauty.common.Constants.MIN_DEFERRED_MINUTES_ADDITION
import com.bunbeauty.domain.util.IDateTimeUtil
import javax.inject.Inject

class DeferredTimeInteractor @Inject constructor(private val dateTimeUtil: IDateTimeUtil) :
    IDeferredTimeInteractor {

    override fun getMinTimeHours(): Int {
        return dateTimeUtil.getTimeIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION
        ).hourOfDay
    }

    override fun getMinTimeMinutes(): Int {
        return dateTimeUtil.getTimeIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION
        ).minuteOfHour
    }

    override fun getDeferredTimeMillis(hours: Int, minutes: Int): Long {
        return dateTimeUtil.getMillisByHourAndMinute(hours, minutes)
    }

    override fun getDeferredTimeHours(deferredTimeMillis: Long): Int {
        return dateTimeUtil.toTime(deferredTimeMillis).hourOfDay
    }

    override fun getDeferredTimeMinutes(deferredTimeMillis: Long): Int {
        return dateTimeUtil.toTime(deferredTimeMillis).minuteOfHour
    }

    override fun getDeferredTimeHHMM(deferredTimeMillis: Long): String {
        return dateTimeUtil.toHHMM(deferredTimeMillis)
    }
}