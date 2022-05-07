package com.bunbeauty.shared.domain.interactor.deferred_time

import com.bunbeauty.shared.domain.model.date_time.DateTime
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.repo.DataStoreRepo
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import core_common.Constants.MIN_DEFERRED_HOURS_ADDITION
import core_common.Constants.MIN_DEFERRED_MINUTES_ADDITION

class DeferredTimeInteractor(
    private val dateTimeUtil: IDateTimeUtil,
    private val dataStoreRepo: DataStoreRepo
) : IDeferredTimeInteractor {

    override suspend fun getMinTime(): Time {
        return getMinDateTime().time
    }

    override suspend fun getDeferredTimeMillis(time: Time): Long {
        return dateTimeUtil.getMillisByTime(
            time,
            dataStoreRepo.getSelectedCityTimeZone()
        )
    }

    suspend fun getMinDateTime(): DateTime {
        return dateTimeUtil.getDateTimeIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION,
            dataStoreRepo.getSelectedCityTimeZone()
        )
    }
}