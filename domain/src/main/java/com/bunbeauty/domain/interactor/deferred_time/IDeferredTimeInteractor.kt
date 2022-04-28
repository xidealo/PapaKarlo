package com.bunbeauty.domain.interactor.deferred_time

import com.bunbeauty.domain.model.date_time.Time

interface IDeferredTimeInteractor {

    suspend fun getMinTime(): Time
    suspend fun getDeferredTimeMillis(time: Time): Long

}