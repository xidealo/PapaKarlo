package com.bunbeauty.domain.worker

interface IUserWorkerUtil {

    fun refreshUser(userUuid: String, userPhone: String)
    suspend fun refreshUserBlocking(userUuid: String, userPhone: String)
}