package com.bunbeauty.domain.interactor.deferred_time

import com.bunbeauty.common.Constants.MIN_DEFERRED_HOURS_ADDITION
import com.bunbeauty.common.Constants.MIN_DEFERRED_MINUTES_ADDITION
import com.bunbeauty.domain.model.datee_time.Time
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.IDateTimeUtil
import javax.inject.Inject

class DeferredTimeInteractor @Inject constructor(
    private val dateTimeUtil: IDateTimeUtil,
    private val dataStoreRepo: DataStoreRepo
) : IDeferredTimeInteractor {

    override suspend fun getMinTime(): Time {
        return dateTimeUtil.getTimeIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION,
            dataStoreRepo.getSelectedCityTimeZone()
        )
    }

    override suspend fun getDeferredTimeMillis(hours: Int, minutes: Int): Long {
        return dateTimeUtil.getMillisByHourAndMinute(
            hours,
            minutes,
            dataStoreRepo.getSelectedCityTimeZone()
        )
    }

    override suspend fun getDeferredTimeHours(deferredTimeMillis: Long): Int {
        return dataStoreRepo.getSelectedCityTimeZone().let { timeZone ->
            dateTimeUtil.toTime(deferredTimeMillis, timeZone).hourOfDay
        }
    }

    override suspend fun getDeferredTimeMinutes(deferredTimeMillis: Long): Int {
        return dataStoreRepo.getSelectedCityTimeZone().let { timeZone ->
            dateTimeUtil.toTime(deferredTimeMillis, timeZone).minuteOfHour
        }
    }

    override suspend fun getDeferredTime(deferredTimeMillis: Long): Time {
        return dataStoreRepo.getSelectedCityTimeZone().let { timeZone ->
            dateTimeUtil.toTime(deferredTimeMillis, timeZone)
        }
    }
}