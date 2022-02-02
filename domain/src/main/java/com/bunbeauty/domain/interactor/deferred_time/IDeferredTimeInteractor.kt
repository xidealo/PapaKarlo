package com.bunbeauty.domain.interactor.deferred_time

import com.bunbeauty.domain.model.datee_time.Time

interface IDeferredTimeInteractor {

    suspend fun getMinTime(): Time
    suspend fun getDeferredTimeMillis(hours: Int, minutes: Int): Long
    suspend fun getDeferredTimeHours(deferredTimeMillis: Long): Int
    suspend fun getDeferredTimeMinutes(deferredTimeMillis: Long): Int
    suspend fun getDeferredTime(deferredTimeMillis: Long): Time

}