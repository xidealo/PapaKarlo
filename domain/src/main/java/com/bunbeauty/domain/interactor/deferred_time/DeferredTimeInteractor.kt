package com.bunbeauty.domain.interactor.deferred_time

import com.bunbeauty.common.Constants.MIN_DEFERRED_HOURS_ADDITION
import com.bunbeauty.common.Constants.MIN_DEFERRED_MINUTES_ADDITION
import com.bunbeauty.domain.model.date_time.DateTime
import com.bunbeauty.domain.model.date_time.Time
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.IDateTimeUtil

class DeferredTimeInteractor(
    private val dateTimeUtil: IDateTimeUtil,
    private val dataStoreRepo: DataStoreRepo
) : IDeferredTimeInteractor {

    override suspend fun isDeferredTimeAvailable(): Boolean {
        return dataStoreRepo.getSelectedCityTimeZone().let { timeZone ->
            dateTimeUtil.getCurrentDateTime(timeZone).date == getMinDateTime().date
        }
    }

    override suspend fun getMinTime(): Time {
        return getMinDateTime().time
    }

    override suspend fun getDeferredTimeMillis(time: Time): Long {
        return dateTimeUtil.getMillisByTime(
            time,
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

    suspend fun getMinDateTime(): DateTime {
        return dateTimeUtil.getDateTimeIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION,
            dataStoreRepo.getSelectedCityTimeZone()
        )
    }
}