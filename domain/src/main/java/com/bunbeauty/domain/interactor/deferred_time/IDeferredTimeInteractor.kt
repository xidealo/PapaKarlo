package com.bunbeauty.domain.interactor.deferred_time

import com.bunbeauty.domain.model.date_time.Time

interface IDeferredTimeInteractor {

    suspend fun isDeferredTimeAvailable(): Boolean
    suspend fun getMinTime(): Time
    suspend fun getDeferredTimeMillis(time: Time): Long
    suspend fun getDeferredTimeHours(deferredTimeMillis: Long): Int
    suspend fun getDeferredTimeMinutes(deferredTimeMillis: Long): Int
    suspend fun getDeferredTime(deferredTimeMillis: Long): Time

}