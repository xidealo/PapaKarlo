package com.bunbeauty.domain.interactor.deferred_time

interface IDeferredTimeInteractor {

    fun getMinTimeHours(): Int
    fun getMinTimeMinutes(): Int
    fun getDeferredTimeMillis(hours: Int, minutes: Int): Long
    fun getDeferredTimeHours(deferredTimeMillis: Long): Int
    fun getDeferredTimeMinutes(deferredTimeMillis: Long): Int
    fun getDeferredTimeHHMM(deferredTimeMillis: Long): String

}