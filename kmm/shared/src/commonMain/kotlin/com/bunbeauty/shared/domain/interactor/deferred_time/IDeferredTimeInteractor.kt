package com.bunbeauty.shared.domain.interactor.deferred_time

import com.bunbeauty.shared.domain.model.date_time.Time

interface IDeferredTimeInteractor {

    suspend fun getMinTime(): Time
    suspend fun getDeferredTimeMillis(time: Time): Long

}