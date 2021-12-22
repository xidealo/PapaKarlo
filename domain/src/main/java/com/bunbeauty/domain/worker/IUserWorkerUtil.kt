package com.bunbeauty.domain.worker

interface IUserWorkerUtil {

    fun refreshUser(token: String)
    suspend fun refreshUserBlocking(token: String)
    fun cancelRefreshUser()
}