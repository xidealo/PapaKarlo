package com.bunbeauty.shared.domain.interactor.deferred_time

import com.bunbeauty.shared.Constants
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.util.IDateTimeUtil

class GetMinTimeUseCase(
    private val dateTimeUtil: IDateTimeUtil,
    private val dataStoreRepo: DataStoreRepo
) {

    suspend operator fun invoke(): Time {
        return dateTimeUtil.getDateTimeIn(
            Constants.MIN_DEFERRED_HOURS_ADDITION,
            Constants.MIN_DEFERRED_MINUTES_ADDITION,
            dataStoreRepo.getSelectedCityTimeZone()
        ).time
    }
}